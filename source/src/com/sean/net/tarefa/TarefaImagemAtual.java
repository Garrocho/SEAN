package com.sean.net.tarefa;

import static com.sean.util.Constantes.IMAGEM;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;

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
				conexao.getEnviaDados().write(IMAGEM.getBytes());
				conexao.getEnviaDados().flush();
				
				int count;
				try {
					byte[] buffer = new byte[512];
					FileOutputStream fis = atividadeMonitoramento.openFileOutput("imagem.jpg", atividadeMonitoramento.MODE_WORLD_WRITEABLE);
					while ((count = conexao.getRecebeDados().read(buffer)) > 0) {
						fis.write(buffer, 0, count); 
						fis.flush();  
					}
					fis.close();
				}catch (Exception e) {
					Log.d("erro", e.toString());
				}
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
		if(codigo == 200) {
			try {
				atividadeMonitoramento.getImagemAtual().setImageDrawable(new BitmapDrawable(atividadeMonitoramento.openFileInput("imagem.jpg")));
			} catch (Exception e) {
				Log.d("erro", e.toString());
			}
		}
		else if (codigo == 500)
			atividadeMonitoramento.mostrarErros("Erro ao Realizar Conexao");
	}
}