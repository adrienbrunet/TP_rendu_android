package com.adrien.tp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public abstract class Database {
	private static List<Batiment> batiments;
	private static SharedPreferences preferences;
	private static SharedPreferences.Editor preferencesEditor;

	public static List<Batiment> getBatiments() {
		return batiments;
	}

	public static void setBatiments(List<Batiment> batiments) {
		Database.batiments = batiments;
	}

	public static Batiment getBatiment(String nom){
		for (Batiment batiment : batiments) {
			if (batiment.getNom().equals(nom)){
				return batiment;
			}
		}
		return null;
	}
	
	public static void setFavoris(String nom, boolean etatfavori) {
		Batiment batiment = getBatiment(nom);
		batiment.setFavoris(etatfavori);
		preferencesEditor.putBoolean(batiment.getNom(), batiment.getFavoris());
		preferencesEditor.apply();
	}

	public static void initializeFavoris(Activity activite) {
		preferences = activite.getPreferences(Context.MODE_PRIVATE);
		preferencesEditor = preferences.edit();
		for (Batiment batiment : batiments) {
			String cle = batiment.getNom();
			boolean etatfavori = preferences.getBoolean(cle, false);
			setFavoris(batiment.getNom(), etatfavori);
		}
	}

}
