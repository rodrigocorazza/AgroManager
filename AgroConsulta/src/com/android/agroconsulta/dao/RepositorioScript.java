package com.android.agroconsulta.dao;


import android.content.Context;


public class RepositorioScript extends Repositorio {

	//public static final String FOREIGN_KEYS_ON = "PRAGMA foreign_keys = ON";
	/* Habilita chave estrangeira */
	
	// Script para fazer drop na tabela 
	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS usuario" +
														 "DROP TABLE IF EXISTS insumo" + 
														 "DROP TABLE IF EXISTS propriedaderural" +
														 "DROP TABLE IF EXISTS praga" +
														 "DROP TABLE IF EXISTS pragamap" +
														 "DROP TABLE IF EXISTS percurso" +
														 "DROP TABLE IF EXISTS ponto" +
														 "DROP TABLE IF EXISTS pulverizacao" ;
	
									
	// Cria a tabela com o "_id" sequencial 
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] { 
		"create table usuario ( _id integer primary key autoincrement," +
								"nome text not null," +
								"usuario text not null," +
								"senha text not null);",
		
		"create table insumo ( _id integer primary key autoincrement," +
							 "nome text not null," +
							 "quantidade integer not null);",
							 
		"create table propriedaderural ( _id integer primary key autoincrement," +
										"nome text not null," +
										"tamanho integer not null," +
										"cultura text not null);",
										
		"create table praga ( _id integer primary key autoincrement," +
							"nome text not null," +
							"tipo text not null );",
							
		"create table pulverizacao ( _id integer primary key autoincrement," +
									"raio double not null," +
									"dataCriacao integer ," + 
									"dataEncerramento integer ," +
									"id_propriedaderural integer not null," +
									"id_insumo integer not null," +											
									"CONSTRAINT fk_pulverizacao_propriedaderural FOREIGN KEY(id_propriedaderural) REFERENCES propriedaderural(_id)," +
									"CONSTRAINT fk_pulverizacao_propriedaderural FOREIGN KEY(id_insumo) REFERENCES insumo(_id));",
		
		"create table percurso( _id integer primary key autoincrement," +
											"dataCriacao integer not null," +
											"dataEncerramento integer not null," +
											"id_pulverizacao integer not null," +
											"id_pontos integer not null," +
											"CONSTRAINT fk_percurso_pulverizacao FOREIGN KEY(id_pulverizacao) REFERENCES pulverizacao(_id));",
									
		"create table ponto( _id integer primary key autoincrement," +
											"data integer not null," +
											"latitude double not null," +
											"longitude double not null," +
											"id_percurso integer not null," +
											"CONSTRAINT fk_ponto_percurso FOREIGN KEY(id_percurso) REFERENCES percurso(_id));",
											
		"create table pragamap( _id integer primary key autoincrement," +
											"id_praga integer not null," +
											"latitude double not null," +
											"longitude double not null," +
											"CONSTRAINT fk_pragamap_praga FOREIGN KEY(id_praga) REFERENCES praga(_id));",
											
											
		"insert into insumo(nome,quantidade) values('insumo A','3123');",
		"insert into insumo(nome,quantidade) values('Insumo B','42321');",
		"insert into insumo(nome,quantidade) values('Insumo C','2131');",
		"insert into propriedaderural(nome,tamanho,cultura) values('propriedaderural A','3123','milho');",
		"insert into praga(nome,tipo) values('Praga 1','teste');",
		"insert into pulverizacao(raio,id_propriedaderural,id_insumo) values('2','1','1');",
		};
	
	private static final String SCRIPT_DATABASE_UPDATE = "ALTER TABLE usuario ALTER COLUMN _id int," +
														 "ALTER TABLE insumo ALTER COLUMN quantidade int," +
														 "ALTER TABLE propriedaderural ALTER COLUMN tamanho int" +
														 "ALTER TABLE praga ALTER COLUMN tipo char" +
														 "ALTER TABLE pragamap ALTER COLUMN _id int" +
														 "ALTER TABLE pulverizacao ALTER COLUMN _id int" +
														 "ALTER TABLE percurso ALTER COLUMN _id int" +
														 "ALTER TABLE ponto ALTER COLUMN _id int" ;
	
			
	// Nome do banco 
	private static final String NOME_BANCO = "baco_dados";
	// Controle de versão
	private static final int VERSAO_BANCO = 1; 
	// Nome da tabela 
	public static final String TABELA_SCRIPT = "usuario" +
											   "insumo" + 
											   "propriedaderural" +
											   "praga" +
											   "pragamap" +
											   "pulverizacao" +
											   "percurso"+
											   "ponto";
	
	// Classe utilitária para abrir, criar, e atualizar o banco de dados 
	private SQLiteHelper dbHelper; 
	
	// Cria o banco de dados com um script SQL
	
	public RepositorioScript(Context ctx) { 
		// Criar utilizando um script SQL 
		dbHelper = new SQLiteHelper(ctx, RepositorioScript.NOME_BANCO, 
										 RepositorioScript.VERSAO_BANCO, 
										 RepositorioScript.SCRIPT_DATABASE_CREATE, 
										 RepositorioScript.SCRIPT_DATABASE_DELETE, 
										 RepositorioScript.SCRIPT_DATABASE_UPDATE); 
		
		// abre o banco no modo escrita para poder alterar também 
		db = dbHelper.getWritableDatabase(); 
		}
	
	// Fecha o banco 
	@Override 
	public void fechar()
	{
		super.fechar(); if (dbHelper != null) { dbHelper.close(); 
		}
		}
	}
	

