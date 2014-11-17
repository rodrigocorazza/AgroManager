package com.android.agroconsulta.atividade;

import java.util.List;

import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.dao.Repositorio;
import com.android.agroconsulta.dao.RepositorioScript;
import com.android.agroconsulta.modelo.Insumo;
import com.android.agroconsulta.modelo.Insumo.Insumos;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class CadastroInsumo extends ListActivity {

	protected static final int INSERIR_EDITAR = 1;
	// protected static final int BUSCAR = 2;

	public static Repositorio repositorio;

	private List<Insumo> insumos;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		repositorio = new RepositorioScript(this);
		atualizarLista();
	}

	public void atualizarLista() {
		// Pega a lista de insumos e exibe na tela

		insumos = repositorio.listarInsumos(); // Adaptador de lista customizado
												// para cada linha de uma insumo
		setListAdapter(new InsumoListAdapter(this, insumos));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
		// menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true;

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu
		switch (item.getItemId()) {
		case INSERIR_EDITAR: // Abre a tela com o formulário para adicionar
			startActivityForResult(new Intent(this, EditarInsumo.class),
					INSERIR_EDITAR);
			break;
		/*
		 * case BUSCAR: // Abre a tela para buscar a insumo pelo nome
		 * startActivity(new Intent(this, BuscarInsumo.class)); break;
		 */

		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int posicao, long id) {
		super.onListItemClick(l, v, posicao, id);
		editarInsumo(posicao);

	} // Recupera o id da insumo, e abre a tela de edição

	protected void editarInsumo(int posicao) {
		// Usuário clicou em alguma insumo da lista
		// Recupera a insumo selecionado
		Insumo insumo = insumos.get(posicao);
		// Cria a intent para abrir a tela de editar
		Intent it = new Intent(this, EditarInsumo.class);
		// Passa o id da insumo como parâmetro
		it.putExtra(Insumos._ID, insumo.id);
		// Abre a tela de edição
		startActivityForResult(it, INSERIR_EDITAR);

	}

	@Override
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it);
		// Quando a activity EditarInsumo retornar, seja se foi para adicionar
		// vamos atualizar a lista
		if (codigoRetorno == RESULT_OK) {
			// atualiza a lista na tela
			atualizarLista();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Fecha o banco
		repositorio.fechar();
	}
}
