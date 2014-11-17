package com.android.agroconsulta.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log; 


class SQLiteHelper extends SQLiteOpenHelper {
	private static final String CATEGORIA = "livro";
	private String[] scriptSQLCreate;
	private String scriptSQLDelete;
	private String scriptSQLUpdate;
	
	/** * Cria uma inst�ncia de SQLiteHelper * *
	 *  @param context * 
	 *  @param nomeBanco nome do banco de dados *
	 *  @param versaoBanco vers�o do banco de dados (se for diferente � para atualizar) * 
	 *  @param scriptSQLCreate SQL com o create table.. * 
	 *  @param scriptSQLDelete SQL com o drop table... */ 
	SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String scriptSQLDelete, String scriptSQLUpdate) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
		this.scriptSQLUpdate = scriptSQLUpdate;
		} 
	@Override 
	
	// Criar novo banco... 
	public void onCreate(SQLiteDatabase db) { Log.i(CATEGORIA, "Criando banco com sql");
	int qtdeScripts = scriptSQLCreate.length;
	// Executa cada sql passado como par�metro 
	for (int i = 0; i < qtdeScripts; i++) { String sql = scriptSQLCreate[i];
	Log.i(CATEGORIA, sql);
	// Cria o banco de dados executando o script de cria��o 
	db.execSQL(sql); 
	}
	}
	@Override 
	// Mudou a vers�o... 
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
		Log.w(CATEGORIA, "Atualizando da vers�o " + versaoAntiga + " para " + novaVersao + ". Todos os registros ser�o deletados.");
		Log.i(CATEGORIA, scriptSQLDelete);
		// Deleta as tabelas... 
		db.execSQL(scriptSQLDelete); 
		// Cria novamente... 
		onCreate(db); 
		}
	
	public void OnUpgrade_table(SQLiteDatabase db){

	  //  ContentValues prod_values = new ContentValues();
	 //   final String columna_producto = product.get_prodName();
	    db.execSQL(scriptSQLUpdate);
	    onCreate(db);
	}
	
	}

