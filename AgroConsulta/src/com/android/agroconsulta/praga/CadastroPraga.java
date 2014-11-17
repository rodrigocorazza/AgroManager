package com.android.agroconsulta.praga;
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
import com.android.agroconsulta.modelo.Praga;
import com.android.agroconsulta.modelo.Praga.Pragas;

public class CadastroPraga extends ListActivity { 
	
	protected static final int INSERIR_EDITAR = 1;
	//protected static final int BUSCAR = 2;
	
	public static Repositorio repositorio;
	
	private List<Praga> pragas;
	
	@Override public void onCreate(Bundle icicle) { 
		super.onCreate(icicle);
		repositorio = new RepositorioScript(this);
		atualizarLista();
		} 
	
	protected void atualizarLista() {
		// Pega a lista de pragas e exibe na tela 
		
		pragas = repositorio.listarPragas(); // Adaptador de lista customizado para cada linha de uma praga 
		setListAdapter(new PragaListAdapter(this, pragas)); 
		} 
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
	//	menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
		return true; 
		
	} 
	
	@Override 
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// Clicou no menu 
		switch (item.getItemId()) {
		case INSERIR_EDITAR: // Abre a tela com o formulário para adicionar 
			startActivityForResult(new Intent(this, EditarPraga.class), INSERIR_EDITAR);
			break;
		/*	case BUSCAR: // Abre a tela para buscar a praga pelo nome 
				startActivity(new Intent(this, BuscarPraga.class));
				break; */ 
				
		} 
		return true; 
		} 
	
	@Override 
	protected void onListItemClick(ListView l, View v, int posicao, long id) {
		super.onListItemClick(l, v, posicao, id);
		editarPraga(posicao);
		
	} // Recupera o id da praga, e abre a tela de edição 
	
	protected void editarPraga(int posicao) {
		// Usuário clicou em alguma praga da lista 
		// Recupera a praga selecionado 
		Praga praga = pragas.get(posicao); 
		// Cria a intent para abrir a tela de editar 
		Intent it = new Intent(this, EditarPraga.class); 
		// Passa o id da praga como parâmetro 
		it.putExtra(Pragas._ID, praga.id); 
		// Abre a tela de edição 
		startActivityForResult(it, INSERIR_EDITAR); 
		
	} 
	@Override 
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it); 
		// Quando a activity EditarPraga retornar, seja se foi para adicionar vamos atualizar a lista 
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
		

