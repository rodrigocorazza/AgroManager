package com.android.agroconsulta.propriedaderural;


import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.modelo.PropriedadeRural;
import com.android.agroconsulta.modelo.PropriedadeRural.PropriedadeRurals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;


public class EditarPropriedadeRural extends Activity {
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	// Campos texto 
	
	private EditText campoNome;
	private EditText campoTamanho;
	private EditText campoCultura;
	private Long id; 
	
	@Override 
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.form_editar_propriedaderural);
		campoNome = (EditText) findViewById(R.id.campoNome);
		campoTamanho = (EditText) findViewById(R.id.campoTamanho);
		campoCultura = (EditText) findViewById(R.id.campoCultura);
		id = null;
		
		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ... 
		
		if (extras != null) {
			id = extras.getLong(PropriedadeRurals._ID);
			if (id != null) {
				// é uma edição, busca o propriedaderural... 
				PropriedadeRural p = buscarPropriedadeRural(id);
				campoNome.setText(p.nome);
				campoTamanho.setText(String.valueOf(p.tamanho));
				campoCultura.setText(p.cultura);
				
			} 
			}
		ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED); 
				// Fecha a tela 
				finish();
				} });
		// Listener para salvar a propriedaderural 
		ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar); 
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) { 
				salvar();
				} });
		ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluir); 
		
		if (id == null) { 
			// Se id está nulo, não pode excluir 
			btExcluir.setVisibility(View.INVISIBLE); 
			} else { 
				// Listener para excluir a propriedaderural 
				btExcluir.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						excluir();
						
					} });
				} 
		} 
	@Override 
	protected void onPause() {
		super.onPause();
		// Cancela para não ficar nada na tela pendente 
		setResult(RESULT_CANCELED); 
		// Fecha a tela 
		finish();
		
	} 
	public void salvar() {
		int tamanho = 0;
		try {
			tamanho = Integer.parseInt(campoTamanho.getText().toString());
			} catch (NumberFormatException e) {
				
			} PropriedadeRural propriedaderural = new PropriedadeRural();
			if (id != null) {
				// É uma atualização 
				propriedaderural.id = id;
				} propriedaderural.nome = campoNome.getText().toString();
				propriedaderural.tamanho = Long.parseLong(campoTamanho.getText().toString());
				propriedaderural.tamanho = tamanho;
				propriedaderural.cultura = campoCultura.getText().toString();
				//
				// Salvar 
				salvarPropriedadeRural(propriedaderural); 
				
				// OK 
				setResult(RESULT_OK, new Intent());
				// Fecha a tela 
				finish(); 
				} 
	public void excluir() {
		if (id != null) {
			excluirPropriedadeRural(id); 
			} 
		// OK 
		setResult(RESULT_OK, new Intent()); 
		// Fecha a tela 
		finish(); 
		} // Buscar a propriedaderural pelo id 
	protected PropriedadeRural buscarPropriedadeRural(long id) {
		return CadastroPropriedadeRural.repositorio.buscarPropriedadeRural(id); 
		} // Salvar a propriedaderural 
	protected void salvarPropriedadeRural(PropriedadeRural propriedaderural) {
		CadastroPropriedadeRural.repositorio.salvarPR(propriedaderural); 
		} // Excluir a propriedaderural 
	protected void excluirPropriedadeRural(long id) {
		CadastroPropriedadeRural.repositorio.deletarPR(id); 
		} 
	
			}
		
	
