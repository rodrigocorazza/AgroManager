package com.android.agroconsulta.modelo;

import java.util.ArrayList;
import java.util.Date;

import android.R.integer;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Percurso {

	public static String[] colunas = new String[] { Percursos._ID, Percursos.DATACRIACAO, Percursos.DATAENCERRAMENTO, Percursos.ID_PULVERIZACAO, Percursos.ID_PONTOS }; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.percurso"; 
	
	public long 				id;
	public long 				dataCriacao;
	public long 				dataEncerramento;
	public Pulverizacao			id_pulverizacao;
	public long					id_pontos;
//	public ArrayList<Ponto>		pontos;
	
	public Percurso()	{
		
	} 
	
	public Percurso(long dataCriacao, long dataEncerramento, Pulverizacao id_pulverizacao, long id_pontos) { 
		super();
		this.dataCriacao = dataCriacao;
		this.dataEncerramento = dataEncerramento;
		this.id_pulverizacao = id_pulverizacao;
		this.id_pontos = id_pontos;
		} 
	
	public Percurso(long id, long dataCriacao, long dataEncerramento, Pulverizacao id_pulverizacao, long id_pontos) { 
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.dataEncerramento = dataEncerramento;
		this.id_pulverizacao = id_pulverizacao;
		this.id_pontos = id_pontos;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class Percursos implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private Percursos() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/percursos");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.percursos"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.percursos"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String DATACRIACAO = "dataCriacao"; 
		public static final String DATAENCERRAMENTO = "dataEncerramento";
		public static final String ID_PULVERIZACAO = "id_pulverizacao";
		public static final String ID_PONTOS = "id_pontos";
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /percursos 
			Uri uriPercursos = ContentUris.withAppendedId(Percursos.CONTENT_URI, id);
			return uriPercursos;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Data Criacao: " + dataCriacao + ",Data Encerramento: " + dataEncerramento + ",ID_pulverizacao: " + id_pulverizacao + "ID_PONTOS: " + id_pontos; 
		 
		}
	}

