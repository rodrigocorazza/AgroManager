package com.android.agroconsulta.atividade;


import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.praga.CadastroPraga;
import com.android.agroconsulta.propriedaderural.CadastroPropriedadeRural;
import com.android.agroconsulta.pulverizacao.ListDaPulverizacao;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Menu extends Activity {
	private Button bt1 = null;
	private Button bt2 = null;
	private Button bt3 = null;
	private Button bt4 = null;
	private ImageView imageView1 = null;
	

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_menu);
        
        imageView1 =  ( ImageView ) findViewById ( R.id.imageView1); 
        
        bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaPropriedadeRural();
			}
		});

        bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaInsumo();
			}
		});

        bt3 = (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaPraga();
			}
		});
        
        bt4 = (Button) findViewById(R.id.bt4);
        bt4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaPulverizacao();
			}
		});

    }

    public void chamaInsumo(){
    	Intent intent = new Intent(this, CadastroInsumo.class);
    	startActivity(intent);
    }
    
    public void chamaPropriedadeRural(){
    	Intent intent = new Intent(this, CadastroPropriedadeRural.class);
    	startActivity(intent);
    }
    public void chamaPraga(){
    	Intent intent = new Intent(this, CadastroPraga.class);
    	startActivity(intent);
    }
    public void chamaPulverizacao(){
    	Intent intent = new Intent(this, ListDaPulverizacao.class);
    	startActivity(intent);
    }
 }   