package com.sean.atividade.monitoramento;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.sean.R;
import com.sean.atividade.Atividade;
import com.sean.net.tarefa.TarefaImagemAtual;

public class AtividadeMonitoramento extends Atividade {

	private ImageView imagemAtual;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_monitoramento);

		this.imagemAtual = (ImageView)findViewById(R.id.atividade_principal_imagem_atual);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.atividade_principal, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		try {
			getImagemAtual().setImageDrawable(new BitmapDrawable(openFileInput("imagem.jpg")));
		} catch (Exception e) {
			Log.d("erro", e.toString());
		}
		super.onResume();
	}
	
	public void obterImagemAtual(View componente) {

		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		TarefaImagemAtual tarefaImagemAtual = new TarefaImagemAtual(this, progressDialog);
		tarefaImagemAtual.execute("nada");
	}

	public Activity getThis() {
		return this;
	}

	public void sair(View componente) {
		finish();
	}

	public ImageView getImagemAtual() {
		return imagemAtual;
	}
}
