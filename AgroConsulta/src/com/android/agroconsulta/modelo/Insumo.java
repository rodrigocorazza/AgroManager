package com.android.agroconsulta.modelo;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Insumo {

	public static String[] colunas = new String[] { Insumos._ID, Insumos.NOME, Insumos.QUANTIDADE }; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.insumo"; 
	
	public long id;
	public String nome;
	public long quantidade;
	
	public Insumo()	{
		
	} 
	
	public Insumo(String nome, long quantidade) { 
		super();
		this.nome = nome;
		this.quantidade = quantidade;
		} 
	
	public Insumo(long id, String nome, long quantidade) { 
		super();
		this.id = id;
		this.nome = nome;
		this.quantidade = quantidade;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class Insumos implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private Insumos() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/insumos");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.insumos"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.insumos"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String NOME = "nome"; 
		public static final String QUANTIDADE = "quantidade";
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /insumos 
			Uri uriInsumos = ContentUris.withAppendedId(Insumos.CONTENT_URI, id);
			return uriInsumos;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Nome: " + nome + ", Quantidade: " + quantidade; 
		 
		}
	}




