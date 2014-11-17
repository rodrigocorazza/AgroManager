package com.android.agroconsulta.praga;
import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.modelo.Praga;
import com.android.agroconsulta.modelo.Praga.Pragas;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.EditText; 
import android.widget.ImageButton;

public class EditarPraga extends Activity {
	
	static final int RESULT_SALVAR = 1;
	static final int RESULT_EXCLUIR = 2;
	// Campos texto 
	
	private EditText campoNomePraga;
	private EditText campoPragaTipo;

	private Long id; 
	
	@Override 
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.form_editar_praga);
		campoNomePraga = (EditText) findViewById(R.id.campoNomePraga);
		campoPragaTipo = (EditText) findViewById(R.id.campoPragaTipo);
		
		id = null;
		
		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ... 
		
		if (extras != null) {
			id = extras.getLong(Pragas._ID);
			if (id != null) {
				// é uma edição, busca o praga... 
				Praga p = buscarPraga(id);
				campoNomePraga.setText(p.nome);
				campoPragaTipo.setText(p.tipo);
				
				
			} 
			}
		ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED); 
				// Fecha a tela 
				finish();
				} });
		// Listener para salvar a praga 
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
				// Listener para excluir a praga 
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
		 Praga praga = new Praga();
			if (id != null) {
				// É uma atualização 
				praga.id = id;
				} praga.nome = campoNomePraga.getText().toString();
				praga.tipo = campoPragaTipo.getText().toString();
				
				 
				// Salvar 
				salvarPraga(praga); 
				// OK 
				setResult(RESULT_OK, new Intent());
				// Fecha a tela 
				finish(); 
				} 
	public void excluir() {
		if (id != null) {
			excluirPraga(id); 
			} 
		// OK 
		setResult(RESULT_OK, new Intent()); 
		// Fecha a tela 
		finish(); 
		} // Buscar a praga pelo id 
	protected Praga buscarPraga(long id) {
		return CadastroPraga.repositorio.buscarPraga(id); 
		} // Salvar a praga 
	protected void salvarPraga(Praga praga) {
		CadastroPraga.repositorio.salvarPraga(praga); 
		} // Excluir a praga 
	protected void excluirPraga(long id) {
		CadastroPraga.repositorio.deletarPraga(id); 
		} 
	
			}
		
	
