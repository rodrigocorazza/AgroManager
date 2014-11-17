package com.android.agroconsulta.usuariologin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.agroconsulta.atividade.Menu;
import com.android.agroconsulta.atividade.R;

public class LoginUsuario extends Activity{
	
	private EditText  edtUsuario 	 = null;
	private EditText  edtSenha  	 = null;
	private Button	  btLogin		 = null;
	private Button	  btSair		 = null;
	private Button    btCadastrar 	 = null;
	private ImageView imageView1	 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_usuariologin);
		
		edtUsuario = (EditText)findViewById(R.id.edtUsuario);
		edtSenha = (EditText)findViewById(R.id.edtSenha);
	
		imageView1 =  ( ImageView ) findViewById ( R.id.imageView1);
		
		btCadastrar = (Button) findViewById(R.id.btCadastrar);
		btCadastrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				chamaCadastroUsuario();
				
			}
		});
		btLogin = (Button) findViewById(R.id.btLogin);
		btLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				chamaMenu();
				
			}
		});

	}

	public void chamaCadastroUsuario(){
		Intent intent = new Intent(LoginUsuario.this, CadastroUsuario.class);
		startActivity(intent);
	}
	public void chamaMenu(){
		Intent intent = new Intent(this, Menu.class);
		startActivity(intent);
	}
	
	
}
