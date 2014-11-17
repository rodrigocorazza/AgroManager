//package com.android.agroconsulta.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteQueryBuilder;
//import android.util.Log;
//
//import com.android.agroconsulta.modelo.PropriedadeRural;
//import com.android.agroconsulta.modelo.PropriedadeRural.PropriedadeRurals;
//
//public class PropriedadeRuralDAO {
//
//private static final String CATEGORIA = "dados";
//	
//	// Nome do banco 
//	private static final String NOME_BANCO = "dados_android"; 
//	
//	public static final String NOME_TABELAPR = "propriedaderural";
//	
//	protected SQLiteDatabase db; 
//	public PropriedadeRuralDAO(Context ctx) {
//		// Abre o banco de dados já existente 
//		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null); }
//	
//	
//	protected PropriedadeRuralDAO() {
//		// Apenas para criar uma subclasse... 
//		} 
//	
//	public long salvarPR(PropriedadeRural propriedaderural) {
//		long id = propriedaderural.id;
//		if (id != 0) { atualizarPR(propriedaderural);
//		} else {
//			// Insere novo 
//			id = inserirPR(propriedaderural);
//			} return id; 
//			} // Insere uma nova propriedaderural 
//	
//	public long inserirPR(PropriedadeRural propriedaderural) {
//		ContentValues values = new ContentValues();
//		values.put(PropriedadeRurals.NOME, propriedaderural.nome);
//		values.put(PropriedadeRurals.TAMANHO, propriedaderural.tamanho);
//		values.put(PropriedadeRurals.CULTURA, propriedaderural.cultura); 
//		long id = inserirPR(values);
//		return id; 
//		} // Insere uma nova propriedaderural 
//	
//	public long inserirPR(ContentValues valores) {
//		long id = db.insert(NOME_TABELAPR, "", valores);
//		return id;
//		} // Atualiza a propriedaderural no banco. O id da propriedaderural é utilizado. 
//	
//	public int atualizarPR(PropriedadeRural propriedaderural) {
//		ContentValues values = new ContentValues();
//		values.put(PropriedadeRurals.NOME, propriedaderural.nome);
//		values.put(PropriedadeRurals.TAMANHO, propriedaderural.tamanho);
//		values.put(PropriedadeRurals.CULTURA, propriedaderural.cultura);
//		String _id = String.valueOf(propriedaderural.id);
//		String where = PropriedadeRurals._ID + "=?"; String[] whereArgs = new String[] { _id };
//		int count = atualizarPR(values, where, whereArgs);
//		return count;
//		} 
//	// Atualiza a propriedaderural com os valores abaixo 
//	// A cláusula where é utilizada para identificar a propriedaderural a ser atualizado 
//	
//	public int atualizarPR(ContentValues valores, String where, String[] whereArgs) { 
//		int count = db.update(NOME_TABELAPR, valores, where, whereArgs);
//		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
//		return count;
//		} 
//	// Deleta a propriedaderural com o id fornecido 
//	public int deletarPR(long id) {
//		String where = PropriedadeRurals._ID + "=?";
//		String _id = String.valueOf(id);
//		String[] whereArgs = new String[] { _id };
//		int count = deletarPR(where, whereArgs);
//		return count; } 
//	// Deleta a propriedaderural com os argumentos fornecidos 
//	public int deletarPR(String where, String[] whereArgs) { 
//		int count = db.delete(NOME_TABELAPR, where, whereArgs);
//		Log.i(CATEGORIA, "Deletou [" + count + "] registros"); return count;
//		} 
//	// Busca a propriedaderural pelo id 
//	public PropriedadeRural buscarPropriedadeRural(long id) {
//		// select * from propriedaderural where _id=? 
//		Cursor c = db.query(true, NOME_TABELAPR, PropriedadeRural.colunas, PropriedadeRurals._ID + "=" + id, null, null, null, null, null);
//		if (c.getCount() > 0) { 
//			// Posicinoa no primeiro elemento do cursor 
//			c.moveToFirst();
//			PropriedadeRural propriedaderural = new PropriedadeRural();
//			// Lê os dados 
//			propriedaderural.id = c.getLong(0);
//			propriedaderural.nome = c.getString(1);
//			propriedaderural.tamanho = c.getLong(2);
//			propriedaderural.cultura = c.getString(3);
//			return propriedaderural;
//			} 
//		return null;
//		} 
//	
//	// Retorna um cursor com todas as propriedaderurals 
//	public Cursor getCursorPR() {
//		try { 
//			// select * from propriedaderurals 
//			return db.query(NOME_TABELAPR, PropriedadeRural.colunas, null, null, null, null, null, null);
//			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar as propriedaderurals: " + e.toString());
//			return null;
//			} 
//		} 
//	// Retorna uma lista com todas as propriedaderurals 
//	
//	public List<PropriedadeRural> listarPropriedadeRurals() {
//		Cursor c = getCursorPR();
//		List<PropriedadeRural> propriedaderurals = new ArrayList<PropriedadeRural>();
//		try {
//			 
//			c.moveToFirst();
//			for (int i=0; i < c.getCount(); i++)
//			{
//				// Recupera os índices das colunas 
//				int idxId = c.getColumnIndex(PropriedadeRurals._ID);
//				int idxNome = c.getColumnIndex(PropriedadeRurals.NOME); 
//				int idxTamanho = c.getColumnIndex(PropriedadeRurals.TAMANHO); 
//				int	 idxCultura = c.getColumnIndex(PropriedadeRurals.CULTURA);
//				// Loop até o final do 
//		
//					PropriedadeRural propriedaderural = new PropriedadeRural(); 
//					propriedaderurals.add(propriedaderural); 
//					// recupera os atributos da propriedaderural 
//					propriedaderural.id = c.getLong(idxId);
//					propriedaderural.nome = c.getString(idxNome);
//					propriedaderural.tamanho = c.getLong(idxTamanho);
//					propriedaderural.cultura = c.getString(idxCultura); 
//					c.moveToNext();
//			} 
//		}catch (Exception e) {
//			Log.i("RepositorioPropriedadeRural - listaPropriedadeRurals()", e.getMessage());
//		}
//		return propriedaderurals;
//	} // Busca a propriedaderural pelo nome "select * from propriedaderural where nome=?"
//
//	public PropriedadeRural buscarPropriedadeRuralPorNome(String nome) {
//		PropriedadeRural propriedaderural = null; try { 
//			// Idem a: SELECT _id,nome,cpf,idade from propriedaderural where nome = ? 
//			Cursor c = db.query(NOME_TABELAPR, PropriedadeRural.colunas, PropriedadeRurals.NOME + "='" + nome + "'", null, null, null, null);
//			// Se encontrou... 
//			if (c.moveToNext()) {
//				propriedaderural = new PropriedadeRural(); 
//				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores 
//				propriedaderural.id = c.getLong(0);
//				propriedaderural.nome = c.getString(1);
//				propriedaderural.tamanho = c.getLong(2);
//				propriedaderural.cultura = c.getString(3);
//				}
//			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar a propriedaderural pelo nome: " + e.toString());
//			return null;
//			} return propriedaderural;
//			} // Busca uma propriedaderural utilizando as configurações definidas no 
//				// SQLiteQueryBuilder 
//				// Utilizado pelo Content Provider de propriedaderural 
//	
//	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
//		Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
//		return c;
//		} 
//	
//	// Fecha o banco 
//		public void fechar() {
//			// fecha o banco de dados 
//			if (db != null) { db.close(); 
//			}
//			}
//}
//
