package com.sean.atividade.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sean.R;
import com.sean.atividade.Atividade;
import com.sean.net.tarefa.TarefaLogin;

public class SEAN extends Atividade {

	private EditText campoTextoLogin;
	private EditText campoTextoSenha;

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

		String campoTextoLogin = getCampoTextoLogin().getText().toString();
		String campoTextoSenha = getCampoTextoSenha().getText().toString();
		
		if (!campoTextoLogin.equals("") && !campoTextoSenha.equals("")) {
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Conectando ao servidor SEMON...");
			progressDialog.setCancelable(false);
			progressDialog.show();
			TarefaLogin tarefaLogin = new TarefaLogin(this, progressDialog);
			tarefaLogin.execute("nada");
		}
		else {
			Toast msgErro = Toast.makeText(this, "Dados Incompletos", Toast.LENGTH_SHORT);
			msgErro.show();
		}
	}

	public void chamaAtividadeMenuInicial() {
		Intent intent = new Intent("monitoramento");
		startActivity(intent);
		finish();
	}

	public void cancelar(View componente) {
		finish();
	}

	public EditText getCampoTextoLogin() {
		return campoTextoLogin;
	}

	public EditText getCampoTextoSenha() {
		return campoTextoSenha;
	}
}