package com.sean.net.tarefa;

import static com.sean.util.Constantes.LOGAR;
import static com.sean.util.Constantes.OK_200;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.sean.atividade.login.SEAN;
import com.sean.net.Conexao;


public class TarefaLogin extends AsyncTask<String, Integer, Integer> {

	private ProgressDialog progressDialog;
	private SEAN atividadeLogin;

	public TarefaLogin(SEAN atividadeLogin, ProgressDialog progressDialog) {
		this.atividadeLogin = atividadeLogin;
		this.progressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute() {
		progressDialog.show();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressDialog.setMessage("Autenticando com SEMON...");
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		int codigo = 0;

		Conexao conexao = new Conexao();
		if (conexao.conectaServidor()) {
			try {
				conexao.getEnviaDados().write(LOGAR.getBytes());
				conexao.getEnviaDados().flush();

				String mensagem = atividadeLogin.getCampoTextoLogin().getText().toString() + "==0#_6_#0==" + atividadeLogin.getCampoTextoSenha().getText().toString();
				Log.d("mensagem", mensagem);
				conexao.getEnviaDados().write(mensagem.getBytes());
				conexao.getEnviaDados().flush();
				String resultado = conexao.byteParaString(conexao.getRecebeDados());
				conexao.desconectaServidor();

				if (resultado.equalsIgnoreCase(OK_200)) {
					codigo = 200;
				}
				else
					codigo = 400;
			} catch (IOException e) {
				e.printStackTrace();
				codigo = 500;
			}
		}
		else {
			codigo = 300;
		}
		Log.d("resulado 2", String.valueOf(codigo));
		return codigo;
	}

	@Override
	protected void onPostExecute(Integer codigo) {
		progressDialog.dismiss();
		if(codigo == 200)
			atividadeLogin.chamaAtividadeMenuInicial();
		else if (codigo == 400)
			atividadeLogin.mostrarErros("Login Incorreto");
		else if (codigo == 300)
			atividadeLogin.mostrarErros("Erro de Acesso");
		else if (codigo == 500)
			atividadeLogin.mostrarErros("Erro ao Realizar Conexao");
	}
}