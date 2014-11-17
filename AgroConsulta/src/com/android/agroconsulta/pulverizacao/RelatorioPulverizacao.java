package com.android.agroconsulta.pulverizacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.agroconsulta.atividade.Menu;
import com.android.agroconsulta.atividade.R;

public class RelatorioPulverizacao extends Activity{
	
	private Button btnExcluirPulv = null;
	private Button btnPercurso = null;
	private Button btnSistemaMenu = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_relatorio_pulverizacao);
        
        btnExcluirPulv = (Button) findViewById(R.id.btnExcluirPulv);
        btnExcluirPulv.setOnClickListener(new OnClickListener() {
		
			public void onClick(View view) {
				
			}});
        
        btnPercurso = (Button) findViewById(R.id.btnPercurso);
        btnPercurso.setOnClickListener(new OnClickListener() {
		
			public void onClick(View view) {
				
			}});
        
        btnSistemaMenu = (Button) findViewById(R.id.btnSistemaMenu);
        btnSistemaMenu.setOnClickListener(new OnClickListener() {
		
			public void onClick(View view) {
				chamaMenu();
			}});
    }
	
	public void chamaMenu(){
    	Intent intent = new Intent(this, Menu.class);
    	startActivity(intent);
    }
	
//	public void porValores (){
//		
//		TextView tvpropriedaderural = (TextView) view.findViewById(R.id.tvpropriedaderural);
//		tvpropriedaderural.setText(p.nome);
//		
//		TextView tvinsumo = (TextView) view.findViewById(R.id.tvinsumo);
//		tvinsumo.setText(String.valueOf(p.quantidade));
//		
//		TextView tvraio = (TextView) view.findViewById(R.id.tvraio);
//		tvraio.setText(p.nome);
//		
//		TextView tvpercurso = (TextView) view.findViewById(R.id.tvpercurso);
//		tvpercurso.setText(p.nome);
//		
//		TextView tvtempo = (TextView) view.findViewById(R.id.tvtempo);
//		tvtempo.setText(p.nome);
//		
//		TextView tvpraga = (TextView) view.findViewById(R.id.tvpraga);
//		nome.setText(p.nome);
//	}
	
	
	
	
}
