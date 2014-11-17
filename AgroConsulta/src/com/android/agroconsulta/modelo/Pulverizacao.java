package com.android.agroconsulta.modelo;

import java.util.ArrayList;
import java.util.Date;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Pulverizacao {

	public static String[] colunas = new String[] { Pulverizacaos._ID, 
													Pulverizacaos.RAIO, 
													Pulverizacaos.DATACRIACAO, 
													Pulverizacaos.DATAENCERRAMENTO,
													Pulverizacaos.ID_INSUMO, 
													Pulverizacaos.ID_PROPRIEDADERURAL };
													//Pulverizacaos.ID_PERCURSO}; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.puverizacao"; 
	
	public long 				id;
	public double 				raio;
	public Date 				dataCriacao;
	public Date 				dataEncerramento;
	public Insumo 				id_insumo;
	public PropriedadeRural		id_propriedaderural;
	//public ArrayList<Percurso>	id_percurso;
	
	public Pulverizacao()	{
		
	} 
	
	public Pulverizacao(double raio, long quantidade, Date dataCriacao, Date dataEncerramento, Insumo id_insumo, PropriedadeRural id_propriedaderural) { 
		super();
		this.raio = raio;
		this.dataCriacao = dataCriacao;
		this.dataEncerramento = dataEncerramento;
		this.id_insumo = id_insumo;
		this.id_propriedaderural = id_propriedaderural;
		//this.id_percurso = id_percurso;
		} 
	
	public Pulverizacao(long id, double raio, long quantidade, Date dataCriacao, Date dataEncerramento, Insumo id_insumo, PropriedadeRural id_propriedaderural, ArrayList<Percurso> id_percurso) { 
		super();
		this.id = id;
		this.raio = raio;
		this.dataCriacao = dataCriacao;
		this.dataEncerramento = dataEncerramento;
		this.id_insumo = id_insumo;
		this.id_propriedaderural = id_propriedaderural;
	//	this.id_percurso = id_percurso;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class Pulverizacaos implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private Pulverizacaos() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/pulverizacaos");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.pulverizacaos"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.pulverizacaos"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String RAIO = "raio"; 
		public static final String DATACRIACAO = "dataCriacao"; 
		public static final String DATAENCERRAMENTO = "dataEncerramento";
		public static final String ID_INSUMO = "id_insumo"; 
		public static final String ID_PROPRIEDADERURAL = "id_propriedaderural"; 
		//public static final String ID_PERCURSO = "id_percurso";
		
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /pulverizacaos 
			Uri uriPulverizacaos = ContentUris.withAppendedId(Pulverizacaos.CONTENT_URI, id);
			return uriPulverizacaos;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Raio: " + raio +
			   ", Data Criacao: " + dataCriacao + 
			   ", Data Encerramento: " + dataEncerramento + 
			   ", Insumo: " + id_insumo + 
			   ", PropriedadeRural: " + id_propriedaderural ;
			   //", Percurso: " + id_percurso;
		 
		}
	}



