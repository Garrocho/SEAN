package com.sean.net.tarefa;

import static com.sean.util.Constantes.OK_200;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.sean.atividade.monitoramento.AtividadeMonitoramento;
import com.sean.net.Conexao;


public class TarefaAlterarMonitoramento extends AsyncTask<String, Integer, Integer> {

	private ProgressDialog progressDialog;
	private AtividadeMonitoramento atividadeMonitoramento;

	public TarefaAlterarMonitoramento(AtividadeMonitoramento atividadeMonitoramento, ProgressDialog progressDialog) {
		this.atividadeMonitoramento = atividadeMonitoramento;
		this.progressDialog = progressDialog;
	}

	@Override
	protected void onPreExecute() {
		progressDialog.show();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		progressDialog.setMessage("Iniciando o Monitoramento do SEMON...");
	}

	@Override
	protected Integer doInBackground(String... args) {
		int codigo = 0;

		try {
			Conexao conexao = new Conexao();
			if (conexao.conectaServidor()) {
				conexao.getEnviaDados().write(args[0].getBytes());
				conexao.getEnviaDados().flush();
				
				String resultado = conexao.byteParaString(conexao.getRecebeDados());
				conexao.desconectaServidor();

				if (resultado.equalsIgnoreCase(OK_200)) {
					codigo = 200;
				}
				else
					codigo = 400;
			}
		} catch (IOException e) {
			codigo = 500;
		}
		return codigo;
	}

	@Override
	protected void onPostExecute(Integer codigo) {
		progressDialog.dismiss();
		if(codigo == 200)
			atividadeMonitoramento.mostrarAlerta("Monitoramento Iniciado");
		else if (codigo == 400)
			atividadeMonitoramento.mostrarErros("Impossível Iniciar o Monitoramento");
		else if (codigo == 500)
			atividadeMonitoramento.mostrarErros("Erro ao Realizar Conexao");
	}
}