package com.sean.atividade.monitoramento;

import static com.sean.util.Constantes.IMAGEM;
import static com.sean.util.Constantes.INICIAR;
import static com.sean.util.Constantes.PAUSAR;
import static com.sean.util.Constantes.STATUS;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sean.R;
import com.sean.atividade.Atividade;
import com.sean.net.tarefa.TarefaAlterarMonitoramento;
import com.sean.net.tarefa.TarefaImagemAtual;
import com.sean.net.tarefa.TarefaObterStatus;

public class AtividadeMonitoramento extends Atividade {

	private ImageView imagemAtual;
	private EditText botaoStatus;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_monitoramento);

		this.imagemAtual = (ImageView)findViewById(R.id.atividade_principal_imagem_atual);
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
	
	public void obterStatusMonitoramento(View componente) {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		String tipo = botaoStatus.getText().toString();
		if (tipo.equalsIgnoreCase("Nenhum")) {
			TarefaObterStatus tarefaObterStatus = new TarefaObterStatus(this, progressDialog);
			tarefaObterStatus.execute(STATUS);
		}
		else if (tipo.equalsIgnoreCase("Iniciar")) {
			TarefaAlterarMonitoramento TarefaIniciar = new TarefaAlterarMonitoramento(this, progressDialog);
			TarefaIniciar.execute(INICIAR);
		}
		else {
			TarefaAlterarMonitoramento TarefaPausar = new TarefaAlterarMonitoramento(this, progressDialog);
			TarefaPausar.execute(PAUSAR);
		}
	}
	
	public void obterImagemAtual(View componente) {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		TarefaImagemAtual tarefaImagemAtual = new TarefaImagemAtual(this, progressDialog);
		tarefaImagemAtual.execute(IMAGEM);
	}

	public void sair(View componente) {
		finish();
	}

	public ImageView getImagemAtual() {
		return imagemAtual;
	}

	public EditText getBotaoStatus() {
		return botaoStatus;
	}
}
