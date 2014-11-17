package com.android.agroconsulta.usuariologin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.agroconsulta.atividade.R;
import com.android.agroconsulta.dao.Repositorio;
import com.android.agroconsulta.dao.RepositorioScript;
import com.android.agroconsulta.modelo.Usuario;




public class CadastroUsuario extends Activity {

	private EditText edtNome			= null;
	private EditText edtUsuario 		= null;
	private EditText edtSenha 			= null;
	private EditText edtNovaSenha		= null;
	private Button   btnSalvar			= null;
	private Button   btnCancelar        = null;
	private Long id; 
	public static Repositorio repositorio;
	
	private void validaUsuario() {
		
		edtNome 	 = (EditText) findViewById(R.id.edtNome);
		edtUsuario 	 = (EditText) findViewById(R.id.edtUsuario);
		edtSenha 	 = (EditText) findViewById(R.id.edtSenha);
		edtNovaSenha = (EditText) findViewById(R.id.edtNovaSenha);
		
		String senha = edtSenha.getText().toString();
    	String senhaConfirmacao = edtNovaSenha.getText().toString();
    	if(senha.equals(senhaConfirmacao)){
    		
    		salvarUsuarioLogin();
    	}
    	else
    		//mensagem de erro, caso nao valide
			Toast.makeText(CadastroUsuario.this, "Erro ao logar!\n", Toast.LENGTH_LONG).show();
	}
	
	public void salvarUsuarioLogin(){
		String senha = null;
		try {
			senha = edtSenha.getText().toString();
			} catch (NumberFormatException e) {
				
			}Usuario usuario = new Usuario();
		if (id != null) {
			// É uma atualização 
			usuario.id = id;
			} usuario.nome = edtNome.getText().toString();
			usuario.usuario = edtUsuario.getText().toString();
			usuario.senha = senha;
			Log.i("teste", "Id: " + usuario.id + "\n nome: " + usuario.nome + "\n usuario: " + usuario.usuario + "\n senha: " + usuario.senha);
			
			//
			// Salvar 
			salvarCadastroUsuarioLogin(usuario); 
			// OK 
	} 

	protected void salvarCadastroUsuarioLogin(Usuario usuario) {
			CadastroUsuario.repositorio.salvarUsuarioLogin(usuario);
			Toast.makeText(CadastroUsuario.this, "Cadastrado com Sucesso!\n", Toast.LENGTH_LONG).show();
			chamaLoginUsuario();
	}
	
	public void chamaLoginUsuario(){
		Intent intent = new Intent(this, LoginUsuario.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_usuariocadastro);
		
		repositorio = new RepositorioScript(this);
		
		btnCancelar = (Button) findViewById(R.id.btnCancelar);
		btnCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED); 
				
				finish();
				} });
		
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		btnSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				validaUsuario();
			}
		});
		
		
		
		
		
	}
}