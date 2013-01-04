package com.semon.login;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.semon.R;
import com.semon.recursos.Conexao;

public class AtividadeLogin extends Activity {

	private EditText campoTextoLogin;
	private EditText campoTextoSenha;
	private String resultado;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_login);

		this.campoTextoLogin = (EditText)findViewById(R.id.atividade_login_campo_texto_login);
		this.campoTextoSenha = (EditText)findViewById(R.id.atividade_login_campo_texto_senha);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.atividade_login, menu);
		return true;
	}

	public void logar(View componente) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Conexao conexao = new Conexao();
					if (conexao.conectaServidor()) {
						conexao.getEnviaDados().write(new String("LOGAR").getBytes());
						conexao.getEnviaDados().flush();
						conexao.getEnviaDados().write(campoTextoLogin.getText().toString().getBytes());
						conexao.getEnviaDados().flush();
						conexao.getEnviaDados().write(campoTextoSenha.getText().toString().getBytes());
						conexao.getEnviaDados().flush();
						resultado = conexao.byteParaString(conexao.getRecebeDados());
						if (resultado.equalsIgnoreCase("LOGIN_CORRETO")) {
							resultado = "Login Correto\nIniciando SemonAndroid";
						}
						else {
							resultado = "Login Incorreto";
						}
					}
					else {
						resultado = "Não Foi Possível Estabelecer Conexão Com o Servidor.";
					}
				} catch (IOException e) {
					resultado = "Não Foi Possível Estabelecer Conexão Com o Servidor.";
				}
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getThis(), resultado, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		t.start();
		Intent intent = new Intent("principal");
		startActivity(intent);
	}

	public Activity getThis() {
		return this;
	}

	public void cancelar(View componente) {
		finish();
	}
}