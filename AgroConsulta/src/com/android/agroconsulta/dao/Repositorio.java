package com.android.agroconsulta.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.agroconsulta.modelo.Insumo;
import com.android.agroconsulta.modelo.Percurso;
import com.android.agroconsulta.modelo.Percurso.Percursos;
import com.android.agroconsulta.modelo.Ponto;
import com.android.agroconsulta.modelo.Ponto.Pontos;
import com.android.agroconsulta.modelo.Praga;
import com.android.agroconsulta.modelo.Praga.Pragas;
import com.android.agroconsulta.modelo.PragaMap;
import com.android.agroconsulta.modelo.PragaMap.PragaMaps;
import com.android.agroconsulta.modelo.PropriedadeRural;
import com.android.agroconsulta.modelo.Insumo.Insumos;
import com.android.agroconsulta.modelo.PropriedadeRural.PropriedadeRurals;
import com.android.agroconsulta.modelo.Pulverizacao;
import com.android.agroconsulta.modelo.Pulverizacao.Pulverizacaos;
import com.android.agroconsulta.modelo.Usuario;
import com.android.agroconsulta.modelo.Usuario.Usuarios;

import android.content.ContentValues; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteQueryBuilder; 
import android.util.Log;

public class Repositorio {
	private static final String CATEGORIA = "dados";
	
	// Nome do banco 
	private static final String NOME_BANCO = "dados_android"; 
	
	// Nome da tabela 
	public static final String NOME_TABELAI = "insumo";
	public static final String NOME_TABELAPR = "propriedaderural";
	public static final String NOME_TABELAPRAGA = "praga";
	public static final String NOME_TABELAPULVERIZACAO = "pulverizacao";
	public static final String NOME_TABELAUSUARIO = "usuario";
	public static final String NOME_TABELAPRAGAMAP = "pragamap";
	public static final String NOME_TABELAPERCURSO = "percurso";
	public static final String NOME_TABELAPONTO = "ponto";
	 
	protected SQLiteDatabase db; 
	public Repositorio(Context ctx) {
		// Abre o banco de dados já existente 
		db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null); }
	
	
	protected Repositorio() {
		// Apenas para criar uma subclasse... 
		} 
	
	// Salva a insumo, insere um novo ou atualiza 
	public long salvarInsumo(Insumo insumo) {
		long id = insumo.id;
		if (id != 0) { atualizarInsumo(insumo);
		} else {
			// Insere novo 
			id = inserirInsumo(insumo);
			} return id; 
			} // Insere uma nova insumo 
	
	public long inserirInsumo(Insumo insumo) {
		ContentValues values = new ContentValues();
		values.put(Insumos.NOME, insumo.nome);
		values.put(Insumos.QUANTIDADE, insumo.quantidade);
		
		long id = inserirInsumo(values);
		return id; 
		} // Insere uma nova insumo 
	
	public long inserirInsumo(ContentValues valores) {
		long id = db.insert(NOME_TABELAI, "", valores);
		return id;
		} // Atualiza a insumo no banco. O id da insumo é utilizado. 
	
	public int atualizarInsumo(Insumo insumo) {
		ContentValues values = new ContentValues();
		values.put(Insumos.NOME, insumo.nome);
		values.put(Insumos.QUANTIDADE, insumo.quantidade);
		
		String _id = String.valueOf(insumo.id);
		String where = Insumos._ID + "=?"; String[] whereArgs = new String[] { _id };
		int count = atualizarInsumo(values, where, whereArgs);
		return count;
		} 
	// Atualiza a insumo com os valores abaixo 
	// A cláusula where é utilizada para identificar a insumo a ser atualizado 
	
	public int atualizarInsumo(ContentValues valores, String where, String[] whereArgs) { 
		int count = db.update(NOME_TABELAI, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
		} 
	// Deleta a insumo com o id fornecido 
	public int deletarInsumo(long id) {
		String where = Insumos._ID + "=?";
		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };
		int count = deletarInsumo(where, whereArgs);
		return count; } 
	
	// Deleta a insumo com os argumentos fornecidos 
	public int deletarInsumo(String where, String[] whereArgs) { 
		int count = db.delete(NOME_TABELAI, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros"); return count;
		} 
	// Busca a insumo pelo id 
	public Insumo buscarInsumo(long id) {
		// select * from insumo where _id=? 
		Cursor c = db.query(true, NOME_TABELAI, Insumo.colunas, Insumos._ID + "=" + id, null, null, null, null, null);
		if (c.getCount() > 0) { 
			// Posicinoa no primeiro elemento do cursor 
			c.moveToFirst();
			Insumo insumo = new Insumo();
			// Lê os dados 
			insumo.id = c.getLong(0);
			insumo.nome = c.getString(1);
			insumo.quantidade = c.getLong(2);
			
			return insumo;
			} 
		return null;
		} 
	// Retorna um cursor com todas as insumos 
	
	public Cursor getCursorInsumo() {
		try { 
			// select * from insumos 
			return db.query(NOME_TABELAI, Insumo.colunas, null, null, null, null, null, null);
			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar as insumos: " + e.toString());
			return null;
			} 
		} 
	// Retorna uma lista com todas as insumos 
	
	public List<Insumo> listarInsumos() {
		Cursor c = getCursorInsumo();
		List<Insumo> insumos = new ArrayList<Insumo>();
		try {
			 
			c.moveToFirst();
			for (int i=0; i < c.getCount(); i++)
			{
				// Recupera os índices das colunas 
				int idxId = c.getColumnIndex(Insumos._ID);
				int idxNome = c.getColumnIndex(Insumos.NOME); 
				int idxQuantidade = c.getColumnIndex(Insumos.QUANTIDADE); 
			
				// Loop até o final do 
		
					Insumo insumo = new Insumo(); 
					insumos.add(insumo); 
					// recupera os atributos da insumo 
					insumo.id = c.getLong(idxId);
					insumo.nome = c.getString(idxNome);
					insumo.quantidade = c.getLong(idxQuantidade);
				
					c.moveToNext();
			} 
		}catch (Exception e) {
			//Log.i("RepositorioInsumo - listaInsumos()", e.getMessage());
		}
		return insumos;
	} // Busca a insumo pelo nome "select * from insumo where nome=?"

	public Insumo buscarInsumoPorNome(String nome) {
		Insumo insumo = null; try { 
			// Idem a: SELECT _id,nome,cpf,idade from insumo where nome = ? 
			Cursor c = db.query(NOME_TABELAI, Insumo.colunas, Insumos.NOME + "='" + nome + "'", null, null, null, null);
			// Se encontrou... 
			if (c.moveToNext()) {
				insumo = new Insumo(); 
				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores 
				insumo.id = c.getLong(0);
				insumo.nome = c.getString(1);
				insumo.quantidade = c.getLong(2);
				
				}
			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar a insumo pelo nome: " + e.toString());
			return null;
			} return insumo;
			} // Busca uma insumo utilizando as configurações definidas no 
				// SQLiteQueryBuilder 
				// Utilizado pelo Content Provider de insumo 
	
	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
		return c;
		} 
//============================================================================================
	
	public long salvarPR(PropriedadeRural propriedaderural) {
		long id = propriedaderural.id;
		if (id != 0) { atualizarPR(propriedaderural);
		} else {
			// Insere novo 
			id = inserirPR(propriedaderural);
			} return id; 
			} // Insere uma nova propriedaderural 
	
	public long inserirPR(PropriedadeRural propriedaderural) {
		ContentValues values = new ContentValues();
		values.put(PropriedadeRurals.NOME, propriedaderural.nome);
		values.put(PropriedadeRurals.TAMANHO, propriedaderural.tamanho);
		values.put(PropriedadeRurals.CULTURA, propriedaderural.cultura); 
		long id = inserirPR(values);
		return id; 
		} // Insere uma nova propriedaderural 
	
	public long inserirPR(ContentValues valores) {
		long id = db.insert(NOME_TABELAPR, "", valores);
		return id;
		} // Atualiza a propriedaderural no banco. O id da propriedaderural é utilizado. 
	
	public int atualizarPR(PropriedadeRural propriedaderural) {
		ContentValues values = new ContentValues();
		values.put(PropriedadeRurals.NOME, propriedaderural.nome);
		values.put(PropriedadeRurals.TAMANHO, propriedaderural.tamanho);
		values.put(PropriedadeRurals.CULTURA, propriedaderural.cultura);
		String _id = String.valueOf(propriedaderural.id);
		String where = PropriedadeRurals._ID + "=?"; String[] whereArgs = new String[] { _id };
		int count = atualizarPR(values, where, whereArgs);
		return count;
		} 
	// Atualiza a propriedaderural com os valores abaixo 
	// A cláusula where é utilizada para identificar a propriedaderural a ser atualizado 
	
	public int atualizarPR(ContentValues valores, String where, String[] whereArgs) { 
		int count = db.update(NOME_TABELAPR, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
		} 
	// Deleta a propriedaderural com o id fornecido 
	public int deletarPR(long id) {
		String where = PropriedadeRurals._ID + "=?";
		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };
		int count = deletarPR(where, whereArgs);
		return count; } 
	// Deleta a propriedaderural com os argumentos fornecidos 
	public int deletarPR(String where, String[] whereArgs) { 
		int count = db.delete(NOME_TABELAPR, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros"); return count;
		} 
	// Busca a propriedaderural pelo id 
	public PropriedadeRural buscarPropriedadeRural(long id) {
		// select * from propriedaderural where _id=? 
		Cursor c = db.query(true, NOME_TABELAPR, PropriedadeRural.colunas, PropriedadeRurals._ID + "=" + id, null, null, null, null, null);
		if (c.getCount() > 0) { 
			// Posicinoa no primeiro elemento do cursor 
			c.moveToFirst();
			PropriedadeRural propriedaderural = new PropriedadeRural();
			// Lê os dados 
			propriedaderural.id = c.getLong(0);
			propriedaderural.nome = c.getString(1);
			propriedaderural.tamanho = c.getLong(2);
			propriedaderural.cultura = c.getString(3);
			return propriedaderural;
			} 
		return null;
		} 
	
	// Retorna um cursor com todas as propriedaderurals 
	public Cursor getCursorPR() {
		try { 
			// select * from propriedaderurals 
			return db.query(NOME_TABELAPR, PropriedadeRural.colunas, null, null, null, null, null, null);
			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar as propriedaderurals: " + e.toString());
			return null;
			} 
		} 
	// Retorna uma lista com todas as propriedaderurals 
	
	public List<PropriedadeRural> listarPropriedadeRurals() {
		Cursor c = getCursorPR();
		List<PropriedadeRural> propriedaderurals = new ArrayList<PropriedadeRural>();
		try {
			 
			c.moveToFirst();
			for (int i=0; i < c.getCount(); i++)
			{
				// Recupera os índices das colunas 
				int idxId = c.getColumnIndex(PropriedadeRurals._ID);
				int idxNome = c.getColumnIndex(PropriedadeRurals.NOME); 
				int idxTamanho = c.getColumnIndex(PropriedadeRurals.TAMANHO); 
				int	 idxCultura = c.getColumnIndex(PropriedadeRurals.CULTURA);
				// Loop até o final do 
		
					PropriedadeRural propriedaderural = new PropriedadeRural(); 
					propriedaderurals.add(propriedaderural); 
					// recupera os atributos da propriedaderural 
					propriedaderural.id = c.getLong(idxId);
					propriedaderural.nome = c.getString(idxNome);
					propriedaderural.tamanho = c.getLong(idxTamanho);
					propriedaderural.cultura = c.getString(idxCultura); 
					c.moveToNext();
			} 
		}catch (Exception e) {
			Log.i("RepositorioPropriedadeRural - listaPropriedadeRurals()", e.getMessage());
		}
		return propriedaderurals;
	} // Busca a propriedaderural pelo nome "select * from propriedaderural where nome=?"

	public PropriedadeRural buscarPropriedadeRuralPorNome(String nome) {
		PropriedadeRural propriedaderural = null; try { 
			// Idem a: SELECT _id,nome,cpf,idade from propriedaderural where nome = ? 
			Cursor c = db.query(NOME_TABELAPR, PropriedadeRural.colunas, PropriedadeRurals.NOME + "='" + nome + "'", null, null, null, null);
			// Se encontrou... 
			if (c.moveToNext()) {
				propriedaderural = new PropriedadeRural(); 
				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores 
				propriedaderural.id = c.getLong(0);
				propriedaderural.nome = c.getString(1);
				propriedaderural.tamanho = c.getLong(2);
				propriedaderural.cultura = c.getString(3);
				}
			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar a propriedaderural pelo nome: " + e.toString());
			return null;
			} return propriedaderural;
			} // Busca uma propriedaderural utilizando as configurações definidas no 
				// SQLiteQueryBuilder 
				// Utilizado pelo Content Provider de propriedaderural 
	
//========================================================================================================================
	
	// Salva a praga, insere um novo ou atualiza 
	public long salvarPraga(Praga praga) {
		long id = praga.id;
		if (id != 0) { atualizarPraga(praga);
		} else {
			// Insere novo 
			id = inserirPraga(praga);
			} return id; 
			} // Insere uma nova praga 
	
	public long inserirPraga(Praga praga) {
		ContentValues values = new ContentValues();
		values.put(Pragas.NOME, praga.nome);
		values.put(Pragas.TIPO, praga.tipo);
		long id = inserirPraga(values);
		return id; 
		} // Insere uma nova praga 
	
	public long inserirPraga(ContentValues valores) {
		long id = db.insert(NOME_TABELAPRAGA, "", valores);
		return id;
		} // Atualiza a praga no banco. O id da praga é utilizado. 
	
	public int atualizarPraga(Praga praga) {
		ContentValues values = new ContentValues();
		values.put(Pragas.NOME, praga.nome);
		values.put(Pragas.TIPO, praga.tipo);
		String _id = String.valueOf(praga.id);
		String where = Pragas._ID + "=?"; String[] whereArgs = new String[] { _id };
		int count = atualizarPraga(values, where, whereArgs);
		return count;
		} 
	// Atualiza a praga com os valores abaixo 
	// A cláusula where é utilizada para identificar a praga a ser atualizado 
	
	public int atualizarPraga(ContentValues valores, String where, String[] whereArgs) { 
		int count = db.update(NOME_TABELAPRAGA, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		return count;
		} 
	// Deleta a praga com o id fornecido 
	public int deletarPraga(long id) {
		String where = Pragas._ID + "=?";
		String _id = String.valueOf(id);
		String[] whereArgs = new String[] { _id };
		int count = deletarPraga(where, whereArgs);
		return count; } 
	// Deleta a praga com os argumentos fornecidos 
	public int deletarPraga(String where, String[] whereArgs) { 
		int count = db.delete(NOME_TABELAI, where, whereArgs);
		Log.i(CATEGORIA, "Deletou [" + count + "] registros"); return count;
		} 
	// Busca a praga pelo id 
	public Praga buscarPraga(long id) {
		// select * from praga where _id=? 
		Cursor c = db.query(true, NOME_TABELAPRAGA, Praga.colunas, Pragas._ID + "=" + id, null, null, null, null, null);
		if (c.getCount() > 0) { 
			// Posicinoa no primeiro elemento do cursor 
			c.moveToFirst();
			Praga praga = new Praga();
			// Lê os dados 
			praga.id = c.getLong(0);
			praga.nome = c.getString(1);
			praga.tipo = c.getString(2);
			//praga.idade = c.getInt(3);
			return praga;
			} 
		return null;
		} 
	// Retorna um cursor com todas as pragas 
	
	public Cursor getCursorPraga() {
		try { 
			// select * from pragas 
			return db.query(NOME_TABELAPRAGA, Praga.colunas, null, null, null, null, null, null);
			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar as pragas: " + e.toString());
			return null;
			} 
		} 
	// Retorna uma lista com todas as pragas 
	
	public List<Praga> listarPragas() {
		Cursor c = getCursorPraga();
		List<Praga> pragas = new ArrayList<Praga>();
		try {
			 
			c.moveToFirst();
			for (int i=0; i < c.getCount(); i++)
			{
				// Recupera os índices das colunas 
				int idxId = c.getColumnIndex(Pragas._ID);
				int idxNome = c.getColumnIndex(Pragas.NOME); 
				int idxTipo = c.getColumnIndex(Pragas.TIPO); 
			//  	int idxidade = c.getColumnIndex(Pragas.IDADE);
				// Loop até o final do 
		
					Praga praga = new Praga(); 
					pragas.add(praga); 
					// recupera os atributos da praga 
					praga.id = c.getLong(idxId);
					praga.nome = c.getString(idxNome);
					praga.tipo = c.getString(idxTipo);
				//	praga.idade = c.getInt(idxidade); 
					c.moveToNext();
			} 
		}catch (Exception e) {
			Log.i("RepositorioPraga - listaPragas()", e.getMessage());
		}
		return pragas;
	} // Busca a praga pelo nome "select * from praga where nome=?"

	public Praga buscarPragaPorNome(String nome) {
		Praga praga = null; try { 
			// Idem a: SELECT _id,nome,cpf,idade from praga where nome = ? 
			Cursor c = db.query(NOME_TABELAPRAGA, Praga.colunas, Pragas.NOME + "='" + nome + "'", null, null, null, null);
			// Se encontrou... 
			if (c.moveToNext()) {
				praga = new Praga(); 
				// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores 
				praga.id = c.getLong(0);
				praga.nome = c.getString(1);
				praga.tipo = c.getString(2);
				// praga.idade = c.getInt(3);
				}
			} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar a praga pelo nome: " + e.toString());
			return null;
			} return praga;
			} // Busca uma praga utilizando as configurações definidas no 
				// SQLiteQueryBuilder 
				// Utilizado pelo Content Provider de praga 
	
//===========================================================================================================================
	
	public long salvarPulverizacao(Pulverizacao pulverizacao) {
		long id = pulverizacao.id;
		if (id != 0) { 
			Log.i("BANCO_PULVERIZACAO", "update");
			atualizarPulverizacao(pulverizacao);
		} else {
			// Insere novo 
			Log.i("BANCO_PULVERIZACAO", "novo");
			id = inserirPulverizacao(pulverizacao);
			} return id; 
			} 
	
	// Insere uma nova Pulverizacao 
	public long inserirPulverizacao(Pulverizacao pulverizacao) {
		ContentValues values = new ContentValues();
		values.put(Pulverizacaos.ID_PROPRIEDADERURAL, pulverizacao.id_propriedaderural.id);
		values.put(Pulverizacaos.ID_INSUMO, pulverizacao.id_insumo.id);
		values.put(Pulverizacaos.DATACRIACAO, pulverizacao.dataCriacao.getTime());
		values.put(Pulverizacaos.RAIO, pulverizacao.raio);
		Log.i("BANCO_PULVERIZACAO", "Inserindo" + " PROPRIEDADE: " + pulverizacao.id_propriedaderural.id + " , INSUMO: " + pulverizacao.id_insumo.id + " , DATA: " +pulverizacao.dataCriacao);                  
		
		long id = inserirPulverizacao(values);
		return id; 
		} 
	
	// Insere uma nova propriedaderural 
	public long inserirPulverizacao(ContentValues valores) {
		long id = db.insert(NOME_TABELAPULVERIZACAO, "", valores);
		Log.i("BANCO_PULVERIZACAO", "Inseriu");
		
		return id;
		} 
	
	// Atualiza a propriedaderural no banco. O id da propriedaderural é utilizado. 
	public int atualizarPulverizacao(Pulverizacao pulverizacao) {
		ContentValues values = new ContentValues();
		values.put(Pulverizacaos.ID_PROPRIEDADERURAL, pulverizacao.id_propriedaderural.id);
		values.put(Pulverizacaos.ID_INSUMO, pulverizacao.id_insumo.id);
		values.put(Pulverizacaos.DATACRIACAO, pulverizacao.dataCriacao.getTime());
		values.put(Pulverizacaos.RAIO, pulverizacao.raio);
		String _id = String.valueOf(pulverizacao.id);
		String where = Pulverizacaos._ID + "=?"; String[] whereArgs = new String[] { _id };
		int count = atualizarPulverizacao(values, where, whereArgs);
		return count;
		} 
	// Atualiza a propriedaderural com os valores abaixo 
	// A cláusula where é utilizada para identificar a propriedaderural a ser atualizado 
	
	public int atualizarPulverizacao(ContentValues valores, String where, String[] whereArgs) { 
		int count = db.update(NOME_TABELAPULVERIZACAO, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		Log.i("BANCO_PULVERIZACAO", "atualizo");
		return count;
		} 
	
	// Deleta a pulverizacao com o id fornecido 
		public int deletarPulverizacao(long id) {
			String where = Pulverizacaos._ID + "=?";
			String _id = String.valueOf(id);
			String[] whereArgs = new String[] { _id };
			int count = deletarPulverizacao(where, whereArgs);
			return count; } 
		// Deleta a pulverizacao com os argumentos fornecidos 
		public int deletarPulverizacao(String where, String[] whereArgs) { 
			int count = db.delete(NOME_TABELAPULVERIZACAO, where, whereArgs);
			Log.i(CATEGORIA, "Deletou [" + count + "] registros"); return count;
			} 
		// Busca a pulverizacao pelo id 
		public Pulverizacao buscarPulverizacao(long id) {
			// select * from pulverizacao where _id=? 
			Cursor c = db.query(true, NOME_TABELAPULVERIZACAO, Pulverizacao.colunas, Pulverizacaos._ID + "=" + id, null, null, null, null, null);
			if (c.getCount() > 0) { 
				// Posicinoa no primeiro elemento do cursor 
				c.moveToFirst();
				Pulverizacao pulverizacao = new Pulverizacao();
				// Lê os dados 
				pulverizacao.id = c.getLong(0);
				pulverizacao.id_insumo.id = c.getLong(1);
				pulverizacao.id_propriedaderural.id = c.getLong(2);
				//pulverizacao.id_percurso = c.getLong(3));
				pulverizacao.dataCriacao = new Date(c.getLong(4));
				pulverizacao.raio = c.getDouble(5);
				return pulverizacao;
				} 
			return null;
			} 
		// Retorna um cursor com todas as pulverizacaos 
		
		public Cursor getCursorPulverizacao() {
			try { 
				// select * from pulverizacaos  
				return db.rawQuery("SELECT * FROM pulverizacao a " +
								   "INNER JOIN insumo b ON a.id_insumo=b._id " +
								   "INNER JOIN propriedaderural c ON a.id_propriedaderural= c._id ", null);
						//db.query(NOME_TABELAPULVERIZACAO, Pulverizacao.olunas, null, null, null, null, null, null);
				} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar as pulverizacaos: " + e.toString());
				return null;
				} 
			} 
		// Retorna uma lista com todas as pulverizacaos 
		
		public List<Pulverizacao> listarPulverizacaos() {
			Cursor c = getCursorPulverizacao();
			List<Pulverizacao> pulverizacaos = new ArrayList<Pulverizacao>();
			try {
				 
				c.moveToFirst();
				for (int i=0; i < c.getCount(); i++)
				{
					// Recupera os índices das colunas 
					int idxId = c.getColumnIndex(Pulverizacaos._ID);
					int idxId_Insumo = c.getColumnIndex(Pulverizacaos.ID_INSUMO);
					int idxId_PropriedadeRural = c.getColumnIndex(Pulverizacaos.ID_PROPRIEDADERURAL); 
					int idxdataCriacao = c.getColumnIndex(Pulverizacaos.DATACRIACAO);
				 	int idxRaio = c.getColumnIndex(Pulverizacaos.RAIO);
				 	
					// Loop até o final do 
			
						Pulverizacao pulverizacao = new Pulverizacao(); 
						pulverizacaos.add(pulverizacao); 
						// recupera os atributos da pulverizacao 
						pulverizacao.id = c.getLong(idxId);
						pulverizacao.id_insumo.nome = c.getString(idxId_Insumo);
						pulverizacao.id_propriedaderural.nome = c.getString(idxId_PropriedadeRural);
						pulverizacao.dataCriacao = new Date(c.getLong(idxdataCriacao));
						pulverizacao.raio = c.getDouble(idxRaio);
						c.moveToNext();
				} 
			}catch (Exception e) {
				Log.i("RepositorioPulverizacao - listaPulverizacaos()", "Falha");
			}
			return pulverizacaos;
		} // Busca a pulverizacao pelo nome "select * from pulverizacao where nome=?"

//		public Pulverizacao buscarPulverizacaoPorNome(String nome) {
//			Pulverizacao pulverizacao = null; try { 
//				// Idem a: SELECT _id,nome,cpf,idade from pulverizacao where nome = ? 
//				Cursor c = db.query(NOME_TABELAPULVERIZACAO, Pulverizacao.colunas, Pulverizacaos.NOME + "='" + nome + "'", null, null, null, null);
//							db.query(NOME_TABELAPULVERIZACAO, Pulverizacao.colunas, Pulverizacaos.NOME + "='" + nome + "'", null, null, null, null);			
		
		// Se encontrou... 
//				if (c.moveToNext()) {
//					pulverizacao = new Pulverizacao(); 
//					// utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores 
//					pulverizacao.id = c.getLong(0);
//					pulverizacao.nome = c.getString(1);
//					pulverizacao.tipo = c.getString(2);
//					// pulverizacao.idade = c.getInt(3);
//					}
//				} catch (SQLException e) { Log.e(CATEGORIA, "Erro ao buscar a pulverizacao pelo nome: " + e.toString());
//				return null;
//				} return pulverizacao;
//				} // Busca uma pulverizacao utilizando as configurações definidas no 
//					// SQLiteQueryBuilder 
//					// Utilizado pelo Content Provider de pulverizacao 
//	

//===========================================================================================================================
		
		public long salvaPercurso(Percurso percurso) {
			long id = percurso.id;
			if (id != 0) { 
				Log.i("BANCO_PERCURSO", "update");
				atualizarPercurso(percurso);
			} else {
				// Insere novo 
				Log.i("BANCO_PERCURSO", "novo");
				id = inserirPercurso(percurso);
				} return id; 
				} 
		
		public long inserirPercurso(Percurso percurso) {
			ContentValues values = new ContentValues();
			values.put(Percursos.DATACRIACAO, percurso.dataCriacao);
			values.put(Percursos.DATAENCERRAMENTO, percurso.dataEncerramento);
			values.put(Percursos.ID_PULVERIZACAO, percurso.id_pulverizacao.id);
			values.put(Percursos.ID_PONTOS, percurso.id_pontos);
			
			//Log.i("BANCO_PERCURSO", "Inserindo" + " IDPERCURSO: " + percurso.id + " DATACRIACAO: " + percurso.dataCriacao + " , DATAENCERRAMENTO: " + percurso.dataEncerramento + " , ID_PULVERIZACAO: " + percurso.id_pulverizacao.id + " , ID_PONTOS: " + percurso.id_pontos);                  
			
			long id = inserirPercurso(values);
			return id; 
			} 
		
		// Insere uma nova Percurso 
		public long inserirPercurso(ContentValues valores) {
			long id = -1;
			try{
			    id = db.insertOrThrow(NOME_TABELAPERCURSO, "", valores);
			}catch(SQLException e){
				Log.i("BANCO_PERCURSO", "falha" + id);
			}
			Log.i("BANCO_PERCURSO", "Inseriu" + id);
			
			return id;
			} 

//Atualiza a Percurso no banco. O id da Percurso é utilizado. 
	public int atualizarPercurso(Percurso percurso) {
		ContentValues values = new ContentValues();
		values.put(Percursos.DATACRIACAO, percurso.dataCriacao);
		values.put(Percursos.DATAENCERRAMENTO, percurso.dataEncerramento);
		values.put(Percursos.ID_PULVERIZACAO, percurso.id_pulverizacao.id);
		values.put(Percursos.ID_PONTOS, percurso.id_pontos);
		
		String _id = String.valueOf(percurso.id);
		String where = Percursos._ID + "=?"; String[] whereArgs = new String[] { _id };
		int count = atualizarPercurso(values, where, whereArgs);
		return count;
		} 
	
	public int atualizarPercurso(ContentValues valores, String where, String[] whereArgs) { 
		int count = db.update(NOME_TABELAPERCURSO, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		Log.i("BANCO_PERCURSO", "atualizo");
		return count;
		} 
		
	
//=======================================================================================================
	//metodo de ponto
	
	public long salvaPonto(Ponto ponto) {
		long id = ponto.id;
		if (id != 0) { 
			Log.i("BANCO_PONTO", "update");
			atualizarPonto(ponto);
		} else {
			// Insere novo 
			Log.i("BANCO_PONTO", "novo");
			id = inserirPonto(ponto);
			} return id; 
			} 
	
	public long inserirPonto(Ponto ponto) {
		ContentValues values = new ContentValues();
		values.put(Pontos.DATA, ponto.data);
		values.put(Pontos.LATITUDE, ponto.latitude);
		values.put(Pontos.LONGITUDE, ponto.longitude);
		values.put(Pontos.ID_PERCURSO, ponto.percurso.id);
		
		Log.i("BANCO_PONTO", "Inserindo" + " IDPonto: " + ponto.id + " DATA: " + ponto.data + " , LATITUDE: " + ponto.latitude + " , LONGITUDE: " + ponto.longitude + " , ID_PERCURSO: " + ponto.percurso.id);                  
		
		long id = inserirPonto(values);
		return id; 
		} 
	
	// Insere uma nova Ponto 
	public long inserirPonto(ContentValues valores) {
		long id = -1; 
		try{
			id = db.insertOrThrow(NOME_TABELAPONTO, "", valores);
		}catch(SQLException e){
			Log.i("BANCO_PONTO", "Falha" + id);
		}
		Log.i("BANCO_PONTO", "Inseriu" + id);
		
		return id;
		} 

//Atualiza a Ponto no BANCO_PONTO. O id da Ponto é utilizado. 
	public int atualizarPonto(Ponto ponto) {
		ContentValues values = new ContentValues();
		values.put(Pontos.DATA, ponto.data);
		values.put(Pontos.LATITUDE, ponto.latitude);
		values.put(Pontos.LONGITUDE, ponto.longitude);
		values.put(Pontos.ID_PERCURSO, ponto.percurso.id);
	
		String _id = String.valueOf(ponto.id);
		String where = Pontos._ID + "=?"; String[] whereArgs = new String[] { _id };
		int count = atualizarPonto(values, where, whereArgs);
		return count;
		} 

	public int atualizarPonto(ContentValues valores, String where, String[] whereArgs) { 
		int count = db.update(NOME_TABELAPONTO, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
		Log.i("BANCO_PONTO", "atualizo");
		return count;
		} 
	
	
// =======================================================================================================
	
	public long salvarUsuarioLogin(Usuario usuario) {
		long id = usuario.id;
		if (id != 0) { 
			Log.i("BANCO_USUARIOLOGIN", "update");
			atualizarUsuarioLogin(usuario);
		} else {
			// Insere novo 
			Log.i("BANCO_USUARIOLOGIN", "novo");
			id = inserirUsuarioLogin(usuario);
		} return id; 
	}
			 
	public long inserirUsuarioLogin(Usuario usuario) {
		ContentValues values = new ContentValues();
		values.put(Usuarios.NOME, usuario.nome);
		values.put(Usuarios.USUARIO, usuario.usuario);
		values.put(Usuarios.SENHA, usuario.senha);
		
		Log.i("BANCO_USUARIOLOGIN", "Inserindo" + " NOME: " + usuario.nome + " , USUARIO: " + usuario.usuario + " , SENHA: " + usuario.senha);                  
		
		long id = inserirUsuarioLogin(values);
		return id; 
		} 
	
	// Insere uma nova propriedaderural 
	public long inserirUsuarioLogin(ContentValues valores) {
		long id = db.insert(NOME_TABELAUSUARIO, "", valores);
		Log.i("BANCO_USUARIOLOGIN", "Inseriu");
		
		return id;
		} 
	// Atualiza a propriedaderural no banco. O id da propriedaderural é utilizado. 
	public int atualizarUsuarioLogin(Usuario usuario) {
		ContentValues values = new ContentValues();
		values.put(Usuarios.NOME, usuario.nome);
		values.put(Usuarios.USUARIO, usuario.usuario);
		values.put(Usuarios.SENHA, usuario.senha);
		String _id = String.valueOf(usuario.id);
		String where = Usuarios._ID + "=?"; String[] whereArgs = new String[] { _id };
		
		int count = atualizarUsuarioLogin(values, where, whereArgs);
	
		return count;
			} 
		// Atualiza a propriedaderural com os valores abaixo 
		// A cláusula where é utilizada para identificar o usuario a ser atualizado 
		
		public int atualizarUsuarioLogin(ContentValues valores, String where, String[] whereArgs) { 
			int count = db.update(NOME_TABELAUSUARIO, valores, where, whereArgs);
			Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
			Log.i("BANCO_USUARIOLOGIN", "atualizo");
			return count;
			} 
	
//====================================================================================================
		
		public long salvarPosicaoPraga(PragaMap pragamap) {
			long id = pragamap.id;
			if (id != 0) { 
				Log.i("BANCO_POSICAOPRAGA", "update");
				atualizarPragaMap(pragamap);
			} else {
				// Insere novo 
				Log.i("BANCO_POSICAOPRAGA", "novo");
				id = inserirPragaMap(pragamap);
			} return id; 
		}
				 
		public long inserirPragaMap(PragaMap pragamap) {
			ContentValues values = new ContentValues();
			values.put(PragaMaps.ID_PRAGA, pragamap.id_praga.id);
			values.put(PragaMaps.LATITUDE, pragamap.latitude);
			values.put(PragaMaps.LONGITUDE, pragamap.longitude);
			
			Log.i("BANCO_POSICAOPRAGA", "Inserindo" + " ID_PRAGA: " + pragamap.id_praga.id + " , LATITUDE: " + pragamap.latitude + " , LONGITUDE: " + pragamap.longitude);                  
			
			long id = inserirPragaMap(values);
			return id; 
			} 
		
		// Insere uma nova propriedaderural 
		public long inserirPragaMap(ContentValues valores) {
			long id = db.insert(NOME_TABELAPRAGAMAP, "", valores);
			Log.i("BANCO_POSICAOPRAGA", "Inseriu");
			
			return id;
			} 
		// Atualiza a propriedaderural no banco. O id da propriedaderural é utilizado. 
		public int atualizarPragaMap(PragaMap pragamap) {
			ContentValues values = new ContentValues();
			values.put(PragaMaps.ID_PRAGA, pragamap.id_praga.id);
			values.put(PragaMaps.LATITUDE, pragamap.latitude);
			values.put(PragaMaps.LONGITUDE, pragamap.longitude);
			String _id = String.valueOf(pragamap.id);
			String where = PragaMaps._ID + "=?"; String[] whereArgs = new String[] { _id };
			
			int count = atualizarPragaMap(values, where, whereArgs);
		
			return count;
				} 
			// Atualiza a propriedaderural com os valores abaixo 
			// A cláusula where é utilizada para identificar o pragamap a ser atualizado 
			
			public int atualizarPragaMap(ContentValues valores, String where, String[] whereArgs) { 
				int count = db.update(NOME_TABELAPRAGAMAP, valores, where, whereArgs);
				Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
				Log.i("BANCO_POSICAOPRAGA", "atualizo");
				return count;
				} 

		
	// Fecha o banco 
	public void fechar() {
		// fecha o banco de dados 
		if (db != null) { db.close(); 
		}
		}
	}
			

