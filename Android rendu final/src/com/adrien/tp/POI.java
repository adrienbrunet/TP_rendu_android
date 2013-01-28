package com.adrien.tp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.adrien.tp.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class POI extends Activity implements OnClickListener{
	Button favoris = null;
	Button yaller = null;
	Button map = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.batiment);
		((TextView) findViewById(R.id.textView1)).setText(getIntent()
				.getStringExtra("nom"));
		((TextView) findViewById(R.id.textView2)).setText(getIntent()
				.getStringExtra("quartier"));
		((TextView) findViewById(R.id.textView3)).setText(getIntent()
				.getStringExtra("secteur"));
		((TextView) findViewById(R.id.textView4)).setText(getIntent()
				.getStringExtra("categorie"));
		((TextView) findViewById(R.id.textView5)).setText(getIntent()
				.getStringExtra("description"));
		((ImageView) findViewById(R.id.imageView1))
				.setImageBitmap(downloadImage(getIntent().getStringExtra(
						"urlimage")));
		favoris = (Button) findViewById(R.id.button1);
		favoris.setOnClickListener(this);
		yaller = (Button) findViewById(R.id.button2);
		yaller.setOnClickListener(this);
		map = (Button) findViewById(R.id.button3);
		map.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == favoris) {
			if(!Database.getBatiment(getIntent().getStringExtra("nom")).getFavoris()){
				Database.setFavoris(getIntent()
						.getStringExtra("nom"), true);
				Toast.makeText(getApplicationContext(),
						"POI ajouté à vos favoris", Toast.LENGTH_SHORT)
						.show();
			}
			else{
				Database.setFavoris(getIntent()
						.getStringExtra("nom"), false);
				Toast.makeText(getApplicationContext(),
						"POI supprimé de vos favoris", Toast.LENGTH_SHORT)
						.show();	
			}
			
		}
		if (v == yaller) {
			Toast.makeText(getApplicationContext(),
					"pas encore implémenter", Toast.LENGTH_SHORT).show();
		}
		
		if (v == map) {
			Toast.makeText(getApplicationContext(),
					"Zoom sur le batiment", Toast.LENGTH_SHORT).show();
			Intent intent_map = new Intent(POI.this, GoogleMapActivity.class);
			intent_map.putExtra("longitude", Database.getBatiment(getIntent().getStringExtra("nom")).getLongitude());
			intent_map.putExtra("latitude", Database.getBatiment(getIntent().getStringExtra("nom")).getLatitude());
			startActivity(intent_map);
		}
	}

	public static Bitmap downloadImage(String url) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(url);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			Log.e("Erreur", "Erreur lors de la recuperation de l'image : "
					+ e.getMessage().toString());
		}
		return bm;
	}
}