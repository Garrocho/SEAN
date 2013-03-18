package com.sean.net.tarefa;

import static com.sean.util.Arquivo.carregaImagem;
import static com.sean.util.Arquivo.gravaImagem;

import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.sean.atividade.monitoramento.AtividadeMonitoramento;
import com.sean.net.Conexao;


public class TarefaImagemAtual extends AsyncTask<String, Integer, Integer> {

	private ProgressDialog progressDialog;
	private AtividadeMonitoramento atividadeMonitoramento;

	public TarefaImagemAtual(AtividadeMonitoramento atividadeMonitoramento, ProgressDialog progressDialog) {
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
		progressDialog.setMessage("Solicitando Imagem Atual do SEMON...");
	}

	@Override
	protected Integer doInBackground(String... args) {
		int codigo = 0;

		try {
			Conexao conexao = new Conexao();
			if (conexao.conectaServidor()) {
				conexao.getEnviaDados().write(args[0].getBytes());
				conexao.getEnviaDados().flush();
				gravaImagem(atividadeMonitoramento, conexao.getRecebeDados(), "teste.png");
				codigo = 200;
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
			atividadeMonitoramento.getImagemAtual().setImageDrawable(carregaImagem(atividadeMonitoramento, "teste.png"));
		else if (codigo == 500)
			atividadeMonitoramento.mostrarErros("Erro ao Realizar Conexao");
	}
}