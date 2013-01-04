package com.semon;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.semon.recursos.Conexao;

public class AtividadeLogin extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.atividade_login, menu);
		return true;
	}

	public void logar(View componente) {
		EditText campoTexto = (EditText)findViewById(R.id.atividade_login_campo_texto_login);
		try {
			Conexao conexao = new Conexao();
			if (conexao.conectaServidor()) {
				conexao.getEnviaDados().write(new String("LOGAR").getBytes());
				conexao.getEnviaDados().flush();
				conexao.getEnviaDados().write(new String("charles").getBytes());
				conexao.getEnviaDados().flush();
				campoTexto.setText(conexao.byteParaString(conexao.getRecebeDados()));
			}
			else {
				campoTexto.setText("erro");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cancelar(View componente) {
		finish();
	}
}
