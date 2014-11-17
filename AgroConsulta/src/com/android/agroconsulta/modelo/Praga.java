package com.android.agroconsulta.modelo;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Praga {
	
	public static String[] colunas = new String[] { Pragas._ID, Pragas.NOME, Pragas.TIPO}; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.praga"; 
	
	public long id;
	public String nome;
	public String tipo;
	
	public Praga()	{
		
	} 
	
	public Praga(String nome, String tipo) { 
		super();
		this.nome = nome;
		this.tipo = tipo;
		} 
	
	public Praga(long id, String nome, String tipo) { 
		super();
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class Pragas implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private Pragas() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pragas");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.pragas"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.pragas"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String NOME = "nome"; 
		public static final String TIPO = "tipo";
		
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /pragas 
			Uri uriPragas = ContentUris.withAppendedId(Pragas.CONTENT_URI, id);
			return uriPragas;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Nome: " + nome + ", tipo: " + tipo; 
		 
		}
	}




