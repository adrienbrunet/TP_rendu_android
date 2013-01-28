package com.adrien.tp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adrien.tp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Splash extends Activity{
	List<Batiment> listebatiments = new ArrayList<Batiment>();
	ProgressBar progressbar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		progressbar = (ProgressBar) findViewById(R.id.progressBar1);
		this.loadDataInList();
		Intent intent_listbatimentactivity = new Intent(Splash.this, Main.class);
		startActivity(intent_listbatimentactivity);
	}

	private void loadDataInList() {
		listebatiments.clear();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://cci.corellis.eu/pois.php");
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				String line = "";
				InputStream inputStream = response.getEntity().getContent();
				line = convertStreamToString(inputStream);
				try {
					JSONObject jsonObject = new JSONObject(line);
					JSONArray jsonArray = jsonObject.getJSONArray("results");
					Toast.makeText(this,
							"Données chargées : " + jsonArray.length() + "",
							Toast.LENGTH_SHORT).show();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject objet = jsonArray.getJSONObject(i);
						Batiment batiment = new Batiment(
								objet.getString("nom"),
								objet.getString("quartier"),
								objet.getString("secteur"),
								objet.getString("categorie_id"),
								objet.getString("small_image"),
								objet.getString("informations"),
								objet.getString("lon"),
								objet.getString("lat"));
												listebatiments.add(batiment);
						Log.w("TAG", objet.toString());
						progressbar.setProgress((int)((float)i/(float)jsonArray.length()*100));
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(this, "Unable to complete the request",
						Toast.LENGTH_LONG).show();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Database.setBatiments(listebatiments);
		Database.initializeFavoris(this);
	}

	private String convertStreamToString(InputStream inputStream) {
		String ligne = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				inputStream));
		try {
			while ((ligne = rd.readLine()) != null) {
				total.append(ligne);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
}
