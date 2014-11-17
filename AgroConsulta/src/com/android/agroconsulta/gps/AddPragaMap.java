package com.android.agroconsulta.gps;

import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.dao.Repositorio;
import com.android.agroconsulta.dao.RepositorioScript;
import com.android.agroconsulta.modelo.Praga;
import com.android.agroconsulta.modelo.PragaMap;


public class AddPragaMap extends Activity {

	public static Repositorio repositorio;
	
	private HashMap<String, Praga> hashMapPraga = new HashMap<String, Praga>();
	private Spinner spinnerPraga;
	private Praga pragaSelect;
	private Button btnSalvarPraga;
	private Button btnCancelarPraga = null;
	private double latitude;
	private double longitude;
	private Long id; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_addpragamap);
		repositorio = new RepositorioScript(this);
		
		latitude = getIntent().getDoubleExtra("latitude", 0.0);
		longitude = getIntent().getDoubleExtra("longitude", 0.0);
		
		Log.i("teste","latitude" + latitude + "longitude" + longitude);
		
		chamaSpinnerPraga();
		
		btnSalvarPraga = (Button) findViewById(R.id.btnSalvarPraga);
		btnSalvarPraga.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar(pragaSelect);
				setResult(RESULT_CANCELED); 
				// Fecha a tela 
				finish();
				} });
		
		btnCancelarPraga = (Button) findViewById(R.id.btnCancelarPraga);
		btnCancelarPraga.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
			setResult(RESULT_CANCELED); 
				// Fecha a tela 
				finish();
				} });
	}
	
	public void salvar(Praga praga){
		PragaMap pragamap = new PragaMap();
		
		if (id != null) {
			// É uma atualização 
			pragamap.id = id;
			} 
			pragamap.id_praga = praga;
			pragamap.latitude = latitude;
			pragamap.longitude = longitude;
			Log.i("LOCATION PRAGA", "Chamando metodo salvar! lat" +pragamap.latitude +"long" +pragamap.longitude );
		
			// Salvar 
			salvarPosicaoPraga(pragamap); 
	}
	
	protected void salvarPosicaoPraga(PragaMap pragamap){
		AddPragaMap.repositorio.salvarPosicaoPraga(pragamap);
		Log.i("LOCATION PRAGA", "Chamando metodo salvar repositorio");
	}
	
	public void chamaSpinnerPraga(){
		
		spinnerPraga = (Spinner) findViewById(R.id.spnAddPraga);

		Repositorio db = new RepositorioScript(getApplicationContext());
		List<Praga> rePragas = db.listarPragas();
    
		//criando um adapter 
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);
		for(Praga praga : rePragas){
			adapter.add(praga.nome);
			hashMapPraga.put(praga.nome, praga);
		}
		
		spinnerPraga.setAdapter(adapter);
		spinnerPraga.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				
				String key = adapter.getItem(position);
				pragaSelect = hashMapPraga.get(key);
				Toast.makeText(getApplicationContext(),"Praga Selecionada: "+pragaSelect.nome+ "\n" +"ID: "+pragaSelect.id, Toast.LENGTH_LONG).show();
			} 

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
