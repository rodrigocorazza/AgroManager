package com.android.agroconsulta.atividade;

import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.modelo.Insumo;
import com.android.agroconsulta.modelo.Insumo.Insumos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.EditText; 
import android.widget.ImageButton;

public class EditarInsumo extends Activity {
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	// Campos texto 
	
	private EditText campoNome;
	private EditText campoQuantidade;
	//private EditText campoIdade;
	private Long id; 
	
	@Override 
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.form_editar_insumo);
		campoNome = (EditText) findViewById(R.id.campoNome);
		campoQuantidade = (EditText) findViewById(R.id.campoQuantidade);
		// campoIdade = (EditText) findViewById(R.id.campoIdade);
		id = null;
		
		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ... 
		
		if (extras != null) {
			id = extras.getLong(Insumos._ID);
			if (id != null) {
				// é uma edição, busca o insumo... 
				Insumo p = buscarInsumo(id);
				campoNome.setText(p.nome);
				campoQuantidade.setText(String.valueOf(p.quantidade));
				//campoIdade.setText(String.valueOf(p.idade));
				
			} 
			}
		ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED); 
				// Fecha a tela 
				finish();
				} });
		// Listener para salvar a insumo 
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
				// Listener para excluir a insumo 
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
		int quantidade = 0;
		try {
			quantidade = Integer.parseInt(campoQuantidade.getText().toString());
			} catch (NumberFormatException e) {
				
			} Insumo insumo = new Insumo();
			if (id != null) {
				// É uma atualização 
				insumo.id = id;
				} insumo.nome = campoNome.getText().toString();
				insumo.quantidade = Long.parseLong(campoQuantidade.getText().toString());
				insumo.quantidade = quantidade;
				//insumo.idade = idade; 
				// Salvar 
				salvarInsumo(insumo); 
				// OK 
				setResult(RESULT_OK, new Intent());
				// Fecha a tela 
				finish(); 
				} 
	public void excluir() {
		if (id != null) {
			excluirInsumo(id); 
			} 
		// OK 
		setResult(RESULT_OK, new Intent()); 
		// Fecha a tela 
		finish(); 
		} // Buscar a insumo pelo id 
	protected Insumo buscarInsumo(long id) {
		return CadastroInsumo.repositorio.buscarInsumo(id); 
		} // Salvar a insumo 
	protected void salvarInsumo(Insumo insumo) {
		CadastroInsumo.repositorio.salvarInsumo(insumo); 
		} // Excluir a insumo 
	protected void excluirInsumo(long id) {
		CadastroInsumo.repositorio.deletarInsumo(id); 
		} 
	
			}
		
	
