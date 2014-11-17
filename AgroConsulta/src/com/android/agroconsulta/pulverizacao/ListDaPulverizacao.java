package com.android.agroconsulta.pulverizacao;

import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.dao.Repositorio;
import com.android.agroconsulta.dao.RepositorioScript;
import com.android.agroconsulta.modelo.Pulverizacao;
import com.android.agroconsulta.modelo.Pulverizacao.Pulverizacaos;


public class ListDaPulverizacao extends ListActivity {

	protected static final int INSERIR = 1;
	// protected static final int BUSCAR = 2;

	public static Repositorio repositorio;

	private List<Pulverizacao> pulverizacaos;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		repositorio = new RepositorioScript(this);
		atualizarLista();
	}

	public void atualizarLista() {
		// Pega a lista de pulverizacaos e exibe na tela

		pulverizacaos = repositorio.listarPulverizacaos(); // Adaptador de lista customizado para cada linha de uma pulverizacao
		setListAdapter(new PulverizacaoListAdapter(this, pulverizacaos));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERIR, 0, "Inserir Novo").setIcon(R.drawable.novo);
		// menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true;

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu
		switch (item.getItemId()) {
		case INSERIR: // Abre a tela com o formulário para adicionar
			startActivityForResult(new Intent(this, CadastroPulverizacao.class),
					INSERIR);
			break;
		/*
		 * case BUSCAR: // Abre a tela para buscar a pulverizacao pelo nome
		 * startActivity(new Intent(this, BuscarPulverizacao.class)); break;
		 */

		}
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int posicao, long id) {
		super.onListItemClick(l, v, posicao, id);
		editarPulverizacao(posicao);

	} // Recupera o id da pulverizacao, e abre a tela de edição

	protected void editarPulverizacao(int posicao) {
		// Usuário clicou em alguma pulverizacao da lista
		// Recupera a pulverizacao selecionado
		Pulverizacao pulverizacao = pulverizacaos.get(posicao);
		// Cria a intent para abrir a tela de editar
		Intent it = new Intent(this, RelatorioPulverizacao.class);
		// Passa o id da pulverizacao como parâmetro
		it.putExtra(Pulverizacaos._ID, pulverizacao.id);
		// Abre a tela de edição
		startActivityForResult(it, INSERIR);

	}

	@Override
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it);
		// Quando a activity EditarPulverizacao retornar, seja se foi para adicionar
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
