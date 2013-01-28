package com.adrien.tp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.adrien.tp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BatimentListAdapter extends BaseAdapter {
	List<Batiment> listbatiment;
	LayoutInflater inflater;
	
	public BatimentListAdapter(Context context, List<Batiment> objects){
		inflater = LayoutInflater.from(context);
		this.listbatiment = objects;
	}

	@Override
	public int getCount() {
		return listbatiment.size();
	}

	@Override
	public Batiment getItem(int position) {
		return listbatiment.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null){
			Log.v("test","convertView is null");
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listbatiment, null);
			holder.tvNom = (TextView) convertView.findViewById(R.id.textView1);
			holder.tvAdresse = (TextView) convertView.findViewById(R.id.textView2);
			holder.tvCategorie = (TextView) convertView.findViewById(R.id.textView3);
			holder.ivsmallimage = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.ivfavoriimage = (ImageView) convertView.findViewById(R.id.imageView2);
			convertView.setTag(holder);
		}
		else{
			Log.v("test", "convertView is not null");
			holder = (ViewHolder) convertView.getTag();
		}

		Batiment batiment = listbatiment.get(position);
		holder.tvNom.setText(batiment.getNom());
		holder.tvAdresse.setText(batiment.getQuartier() + " - " + batiment.getSecteur());
		holder.ivsmallimage.setImageBitmap(downloadImage(batiment.getUrlimage()));
		holder.tvCategorie.setText(batiment.getCategories());
		if(batiment.getFavoris()){
			holder.ivfavoriimage.setImageResource(R.drawable.defacto_tab_favoris_w);
		}
		else{
			holder.ivfavoriimage.setImageResource(R.drawable.defacto_tab_favoris);
		}
		return convertView;
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
    		Log.e("Erreur","Erreur lors de la recuperation de l'image : " + e.getMessage().toString());
    	}
    	return bm;
    }
	
	private class ViewHolder{
		TextView tvNom;
		TextView tvAdresse;
		ImageView ivsmallimage;
		TextView tvCategorie;
		ImageView ivfavoriimage;
	}
}