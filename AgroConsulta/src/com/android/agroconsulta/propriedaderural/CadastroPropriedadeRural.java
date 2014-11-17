package com.android.agroconsulta.propriedaderural;

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
import com.android.agroconsulta.modelo.PropriedadeRural;
import com.android.agroconsulta.modelo.PropriedadeRural.PropriedadeRurals;

public class CadastroPropriedadeRural extends ListActivity { 
	
	protected static final int INSERIR_EDITAR = 1;
	//protected static final int BUSCAR = 2;
	
	public static Repositorio repositorio;
	
	private List<PropriedadeRural> propriedaderurals;
	
	@Override public void onCreate(Bundle icicle) { 
		super.onCreate(icicle);
		repositorio = new RepositorioScript(this);
		atualizarLista();
		} 
	
	protected void atualizarLista() {
		// Pega a lista de propriedaderurals e exibe na tela 
		
		propriedaderurals = repositorio.listarPropriedadeRurals(); // Adaptador de lista customizado para cada linha de uma propriedaderural 
		setListAdapter(new PropriedadeRuralListAdapter(this, propriedaderurals)); 
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
			startActivityForResult(new Intent(this, EditarPropriedadeRural.class), INSERIR_EDITAR);
			break;
		/*	case BUSCAR: // Abre a tela para buscar a propriedaderural pelo nome 
				startActivity(new Intent(this, BuscarPropriedadeRural.class));
				break; */ 
				
		} 
		return true; 
		} 
	
	@Override 
	protected void onListItemClick(ListView l, View v, int posicao, long id) {
		super.onListItemClick(l, v, posicao, id);
		editarPropriedadeRural(posicao);
		
	} // Recupera o id da propriedaderural, e abre a tela de edição 
	
	protected void editarPropriedadeRural(int posicao) {
		// Usuário clicou em alguma propriedaderural da lista 
		// Recupera a propriedaderural selecionado 
		PropriedadeRural propriedaderural = propriedaderurals.get(posicao); 
		// Cria a intent para abrir a tela de editar 
		Intent it = new Intent(this, EditarPropriedadeRural.class); 
		// Passa o id da propriedaderural como parâmetro 
		it.putExtra(PropriedadeRurals._ID, propriedaderural.id); 
		// Abre a tela de edição 
		startActivityForResult(it, INSERIR_EDITAR); 
		
	} 
	@Override 
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
		super.onActivityResult(codigo, codigoRetorno, it); 
		// Quando a activity EditarPropriedadeRural retornar, seja se foi para adicionar vamos atualizar a lista 
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
		

