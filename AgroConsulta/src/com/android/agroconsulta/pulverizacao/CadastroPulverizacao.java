package com.android.agroconsulta.pulverizacao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.dao.Repositorio;
import com.android.agroconsulta.dao.RepositorioScript;
import com.android.agroconsulta.gps.GPSLocalizacao;
import com.android.agroconsulta.modelo.Insumo;
import com.android.agroconsulta.modelo.PropriedadeRural;
import com.android.agroconsulta.modelo.Pulverizacao;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class CadastroPulverizacao extends Activity{
	 
	private HashMap<String, Insumo> hashMapInsumo = new HashMap<String, Insumo>();
	private HashMap<String, PropriedadeRural> hashMapPropriedadeRural = new HashMap<String, PropriedadeRural>();
	
	public static Repositorio repositorio;
	private Button btnIniciar = null;
	private Button btnCancelar = null;
	private EditText edtRaio = null;
	private Long id;
	private Spinner spinnerInsumo, spinnerPropriedadeRural;
	private Insumo insumoSelect;
	private PropriedadeRural propriedaderuralSelect;
	private Pulverizacao     pulverizacao;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_pulverizacao);
		repositorio = new RepositorioScript(this);
		
		ChamaSpinnerInsumo();
		ChamaPropriedadeRural();
		
		edtRaio = (EditText) findViewById(R.id.edtRaio);
		id = null;
		
		btnCancelar = (Button) findViewById(R.id.btnCancelar);
		btnCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED); 
				// Fecha a tela 
				finish();
				} });
		
		btnIniciar = (Button) findViewById(R.id.btnIniciar);
		btnIniciar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chamaGPSLocalizacao();
				
			}
		});
		
	}
	
	protected void salvarPulverizacao(Pulverizacao pulverizacao) {
		CadastroPulverizacao.repositorio.salvarPulverizacao(pulverizacao);
	}
	
	public void ChamaSpinnerInsumo(){
		
		spinnerInsumo = (Spinner) findViewById(R.id.spnInsumo);
	
		Repositorio db = new RepositorioScript(getApplicationContext());
		List<Insumo> reInsumos = db.listarInsumos();
    
		//criando um adapter 
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);
		for(Insumo insumo : reInsumos){
			adapter.add(insumo.nome);
			hashMapInsumo.put(insumo.nome, insumo);
		}
		
		spinnerInsumo.setAdapter(adapter);
		spinnerInsumo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
				String key = adapter.getItem(position);
				insumoSelect = hashMapInsumo.get(key);
				Toast.makeText(getApplicationContext(),"Insumo Selecionado: "+insumoSelect.nome+ "\n" +"ID: "+insumoSelect.id, Toast.LENGTH_LONG).show();
			} 

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void ChamaPropriedadeRural(){
		
		spinnerPropriedadeRural = (Spinner) findViewById(R.id.spnPropriedadeRural);
	
		Repositorio db = new RepositorioScript(getApplicationContext());
		List<PropriedadeRural> rePropriedadeRurals = db.listarPropriedadeRurals();
    
		//criando um adapter 
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);
		for(PropriedadeRural propriedaderural : rePropriedadeRurals){
			adapter.add(propriedaderural.nome);
			hashMapPropriedadeRural.put(propriedaderural.nome, propriedaderural);
		}
		
		spinnerPropriedadeRural.setAdapter(adapter);
		spinnerPropriedadeRural.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
				String key = adapter.getItem(position);
				propriedaderuralSelect = hashMapPropriedadeRural.get(key);
				Toast.makeText(getApplicationContext(),"Propriedade Rural Selecionado: "+propriedaderuralSelect.nome+ "\n" +"ID: "+propriedaderuralSelect.id, Toast.LENGTH_LONG).show();
			} 

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void chamaGPSLocalizacao(){
		salvar(insumoSelect, propriedaderuralSelect);
		
		Intent intent = new Intent(CadastroPulverizacao.this, GPSLocalizacao.class);
		intent.putExtra("pulverizacao", pulverizacao.id);
		intent.putExtra("raio", pulverizacao.raio);
		startActivity(intent);
	}
	
	
	public void salvar(Insumo insumo, PropriedadeRural propriedaderural) {
		double raio = 0.00001;
		try {
			raio = Double.parseDouble(edtRaio.getText().toString());
			} catch (NumberFormatException e) {
				
			}
				pulverizacao = new Pulverizacao();
			if (id != null) {
				// É uma atualização 
				pulverizacao.id = id;
				}
				pulverizacao.raio =  Double.parseDouble(edtRaio.getText().toString());
				pulverizacao.id_propriedaderural = propriedaderural;
				pulverizacao.raio = raio;
				pulverizacao.id_insumo = insumo;
				pulverizacao.dataCriacao = new Date();
				
				Log.i("teste", "PROPRIEDADE: " + propriedaderural.id + ", INSUMO: " + insumo.id + ", RAIO: " + pulverizacao.raio);
				
				
				//
				// Salvar 
				salvarPulverizacao(pulverizacao); 
				// OK 
				
	} 
	
	
	
	
}
