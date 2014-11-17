package com.android.agroconsulta.modelo;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class PropriedadeRural {

	public static String[] colunas = new String[] { PropriedadeRurals._ID, PropriedadeRurals.NOME, PropriedadeRurals.TAMANHO, PropriedadeRurals.CULTURA }; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.propriedaderural"; 
	
	public long id;
	public String nome;
	public long tamanho;
	public String cultura;
	
	public PropriedadeRural()	{
		
	} 
	
	public PropriedadeRural(String nome, long tamanho, String cultura) { 
		super();
		this.nome = nome;
		this.tamanho = tamanho;
		this.cultura = cultura;
		} 
	
	public PropriedadeRural(long id, String nome, long tamanho, String cultura) { 
		super();
		this.id = id;
		this.nome = nome;
		this.tamanho = tamanho;
		this.cultura = cultura;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class PropriedadeRurals implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private PropriedadeRurals() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/propriedaderurals");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.propriedaderurals"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.propriedaderurals"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String NOME = "nome"; 
		public static final String TAMANHO = "tamanho"; 
		public static final String CULTURA = "cultura"; 
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /propriedaderurals 
			Uri uriPropriedadeRurals = ContentUris.withAppendedId(PropriedadeRurals.CONTENT_URI, id);
			return uriPropriedadeRurals;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Nome: " + nome + ", Tamanho: " + tamanho + ", Cultura: " + cultura; 
		 
		}
	}




