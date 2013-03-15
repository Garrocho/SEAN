package com.sean.net.tarefa;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.sean.atividade.monitoramento.AtividadeMonitoramento;
import com.sean.net.Conexao;


public class TarefaObterStatus extends AsyncTask<String, Integer, Integer> {

	private ProgressDialog progressDialog;
	private AtividadeMonitoramento atividadeMonitoramento;

	public TarefaObterStatus(AtividadeMonitoramento atividadeMonitoramento, ProgressDialog progressDialog) {
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
		progressDialog.setMessage("Obtendo Status do Monitoramento...");
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

				try {
					codigo = Integer.parseInt(resultado);
				}catch (Exception e) {
					codigo = 300;
				}
			}
		} catch (IOException e) {
			codigo = 500;
		}
		return codigo;
	}

	@Override
	protected void onPostExecute(Integer codigo) {
		progressDialog.dismiss();
		if(codigo == 200) {
			atividadeMonitoramento.mostrarAlerta("Monitoramento Iniciado");
			atividadeMonitoramento.getBotaoStatus().setText("Pausar");
		}
		else if (codigo == 401) {
			atividadeMonitoramento.mostrarAlerta("Monitoramento Pausado");
			atividadeMonitoramento.getBotaoStatus().setText("Iniciar");
		}
		else if (codigo == 400)
			atividadeMonitoramento.mostrarErros("Impossível Iniciar o Monitoramento");
		else if (codigo == 500)
			atividadeMonitoramento.mostrarErros("Erro ao Realizar Conexao");
	}
}