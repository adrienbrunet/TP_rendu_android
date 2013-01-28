package com.adrien.tp;

import java.util.ArrayList;
import java.util.List;

import com.adrien.tp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FavoriActivity extends Activity implements OnClickListener, TextWatcher{
	List<Batiment> listbatimentsaffiche = new ArrayList<Batiment>();
	Button liste = null;
	Button carte = null;
	Button favoris = null;
	Button categorie = null;
	ListView listview = null;
	BatimentListAdapter batimentlistadapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listebatiments);
		listbatimentsaffiche.clear();
		for (Batiment batiment : Database.getBatiments()) {
			if (batiment.getFavoris()){
				listbatimentsaffiche.add(batiment);
			}
		}
		final ListView listview = (ListView) findViewById(R.id.listView1);
		batimentlistadapter = new BatimentListAdapter(
				this, listbatimentsaffiche);
		listview.setAdapter(batimentlistadapter);
		
		liste = (Button) findViewById(R.id.button1);
		liste.setOnClickListener(this);
		carte = (Button) findViewById(R.id.button2);
		carte.setOnClickListener(this);
		favoris = (Button) findViewById(R.id.button3);
		favoris.setOnClickListener(this);
		categorie = (Button) findViewById(R.id.button4);
		categorie.setOnClickListener(this);
		EditText editText = (EditText) findViewById(R.id.editText1);
		editText.addTextChangedListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent_POI = new Intent(FavoriActivity.this, POI.class);
				Batiment batiment = batimentlistadapter.getItem(position);
				intent_POI.putExtra("nom", batiment.getNom());
				intent_POI.putExtra("quartier", batiment.getQuartier());
				intent_POI.putExtra("secteur", batiment.getSecteur());
				intent_POI.putExtra("categorie", batiment.getCategories());
				intent_POI.putExtra("urlimage", batiment.getUrlimage());
				intent_POI.putExtra("description", batiment.getDescription());
				intent_POI.putExtra("longitude", batiment.getLongitude());
				intent_POI.putExtra("latitude", batiment.getLatitude());
				startActivity(intent_POI);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.listebatiments);
		listbatimentsaffiche.clear();
		for (Batiment batiment : Database.getBatiments()) {
			if (batiment.getFavoris()){
				listbatimentsaffiche.add(batiment);
			}
		}
		final ListView listview = (ListView) findViewById(R.id.listView1);
		batimentlistadapter = new BatimentListAdapter(
				this, listbatimentsaffiche);
		listview.setAdapter(batimentlistadapter);
		
		liste = (Button) findViewById(R.id.button1);
		liste.setOnClickListener(this);
		carte = (Button) findViewById(R.id.button2);
		carte.setOnClickListener(this);
		favoris = (Button) findViewById(R.id.button3);
		favoris.setOnClickListener(this);
		categorie = (Button) findViewById(R.id.button4);
		categorie.setOnClickListener(this);
		EditText editText = (EditText) findViewById(R.id.editText1);
		editText.addTextChangedListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent_POI = new Intent(FavoriActivity.this, POI.class);
				Batiment batiment = batimentlistadapter.getItem(position);
				intent_POI.putExtra("nom", batiment.getNom());
				intent_POI.putExtra("quartier", batiment.getQuartier());
				intent_POI.putExtra("secteur", batiment.getSecteur());
				intent_POI.putExtra("categorie", batiment.getCategories());
				intent_POI.putExtra("urlimage", batiment.getUrlimage());
				intent_POI.putExtra("description", batiment.getDescription());
				intent_POI.putExtra("longitude", batiment.getLongitude());
				intent_POI.putExtra("latitude", batiment.getLatitude());
				startActivity(intent_POI);
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == liste) {
			Toast.makeText(getApplicationContext(), "Liste des batiments",
					Toast.LENGTH_SHORT).show();
			Intent intent_batiment = new Intent(FavoriActivity.this, Main.class);
			startActivity(intent_batiment);
		}
		if (v == carte) {
			Toast.makeText(getApplicationContext(), "Non implémenté",
					Toast.LENGTH_SHORT).show();
		}
		if (v == favoris) {
			Toast.makeText(getApplicationContext(), "Vous etes déjà sur la liste des favoris",
					Toast.LENGTH_SHORT).show();
		}
		if (v == categorie) {
			Toast.makeText(getApplicationContext(), "menu des catégories",
					Toast.LENGTH_SHORT).show();
			this.openOptionsMenu();
		}
	}
	
	@Override
	public void afterTextChanged(Editable recherche) {
		Log.w("TAG", "aftertextchanged");
		listbatimentsaffiche.clear();
		for (Batiment batiment : Database.getBatiments()) {
			if (batiment.getFavoris() && batiment.getNom().toLowerCase().contains(recherche.toString())){
				listbatimentsaffiche.add(batiment);
			}
		}
		batimentlistadapter.notifyDataSetChanged();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.categorie1:
	        Intent intent_categorie1 = new Intent(FavoriActivity.this, CategorieActivity.class);
	        intent_categorie1.putExtra("categorie", "1");
	        startActivity(intent_categorie1);
			return true;
		case R.id.categorie2:
			Intent intent_categorie2 = new Intent(FavoriActivity.this, CategorieActivity.class);
			intent_categorie2.putExtra("categorie", "2");
			startActivity(intent_categorie2);
			return true;
		case R.id.categorie3:
			Intent intent_categorie3 = new Intent(FavoriActivity.this, CategorieActivity.class);
			intent_categorie3.putExtra("categorie", "3");
			startActivity(intent_categorie3);
			return true;
		case R.id.categorie4:
			Intent intent_categorie4 = new Intent(FavoriActivity.this, CategorieActivity.class);
			intent_categorie4.putExtra("categorie", "4");
			startActivity(intent_categorie4);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
