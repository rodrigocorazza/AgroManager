package com.android.agroconsulta.modelo;

import android.content.ContentUris;
import android.net.Uri; 
import android.provider.BaseColumns; 

public class Usuario { 
	public static String[] colunas = new String[] { Usuarios._ID, 
													Usuarios.NOME, 
													Usuarios.USUARIO, 
													Usuarios.SENHA }; 
	public static final String AUTHORITY = "com.android.agroconsulta.provider.usuario"; 
	
	public long id;
	public String nome;
	public String usuario;
	public String senha;
	
	
	public Usuario()	{
		
	} 
	
	public Usuario(String nome, String usuario, String senha) { 
		super();
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		} 
	
	public Usuario(long id, String nome, String usuario, String senha) { 
		super();
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		} /** * Classe interna para representar as colunas e ser utilizada por um Content * Provider * * Filha de BaseColumns que já define (_id e _count), para seguir o padrão * Android */ 
	
	public static final class Usuarios implements BaseColumns { 
		// Não pode instanciar esta Classe 
		
		private Usuarios() { 
			} 
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/usuarios");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.usuarios"; 
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.usuarios"; 
		public static final String DEFAULT_SORT_ORDER = "_id ASC"; 
		public static final String NOME = "nome"; 
		public static final String USUARIO = "usuario"; 
		public static final String SENHA = "senha";
		
		public static Uri getUriId(long id) {
			// Adiciona o id na URI default do /usuarios 
			Uri uriUsuarios = ContentUris.withAppendedId(Usuarios.CONTENT_URI, id);
			return uriUsuarios;
			} 
		} 
	
	@Override 
	
	public String toString() { 
		return "Nome: " + nome + ", Usuario: " + usuario + ", Senha: " + senha; 
		 
		}
	}


