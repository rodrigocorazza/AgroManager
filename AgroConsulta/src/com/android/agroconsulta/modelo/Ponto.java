package com.android.agroconsulta.modelo;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Ponto {
	
	public static String[] colunas = new String[] { Pontos._ID, Pontos.DATA, Pontos.LATITUDE, Pontos.LONGITUDE, Pontos.ID_PERCURSO }; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.ponto"; 
	
	public long 				id;
	public long 				data;
	public double 				latitude;
	public double				longitude;
	public Percurso				percurso;
	
	public Ponto()	{
		
	} 
	
	public Ponto(long data, double latitude, double longitude, Percurso percurso) { 
		super();
		this.data = data;
		this.latitude = latitude;
		this.longitude = longitude;
		this.percurso = percurso;
		
		} 
	
	public Ponto(long id, long data, double latitude, double longitude, Percurso percurso) { 
		super();
		this.id = id;
		this.data = data;
		this.latitude = latitude;
		this.longitude = longitude;
		this.percurso = percurso;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class Pontos implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private Pontos() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pontos");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.pontos"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.pontos"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String DATA = "data"; 
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
		public static final String ID_PERCURSO = "id_percurso";
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /pontos 
			Uri uriPontos = ContentUris.withAppendedId(Pontos.CONTENT_URI, id);
			return uriPontos;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Data : " + data + ",Latitude: " + latitude + ", Longitude :" + longitude + "ID_Percurso: " + percurso; 
		 
		}
	}
