package com.android.agroconsulta.modelo;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;



public class PragaMap {
	
	public static String[] colunas = new String[] { PragaMaps._ID, PragaMaps.ID_PRAGA, PragaMaps.LATITUDE, PragaMaps.LONGITUDE }; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.pragamap"; 
	
	public long id;
	public Praga id_praga;
	public double latitude;
	public double longitude;
	
	public PragaMap()	{
		
	} 
	
	public PragaMap(Praga id_praga, double latitude, double longitude) { 
		super();
		this.id_praga = id_praga;
		this.latitude = latitude;
		this.longitude = longitude;
		} 
	
	public PragaMap(long id, Praga id_praga, double latitude, double longitude) { 
		super();
		this.id = id;
		this.id_praga = id_praga;
		this.latitude = latitude;
		this.longitude = longitude;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class PragaMaps implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private PragaMaps() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pragamaps");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.pragamaps"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.pragamaps"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String ID_PRAGA = "id_praga";
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
		
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /pragamaps 
			Uri uriPragaMaps = ContentUris.withAppendedId(PragaMaps.CONTENT_URI, id);
			return uriPragaMaps;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Id_Praga: " + id_praga + ", Latitude: " + latitude + ", Longitude: " + longitude; 
		 
		}
	}

