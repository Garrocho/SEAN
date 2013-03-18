package com.sean.atividade.monitoramento;

import static com.sean.util.Arquivo.carregaDrawable;
import static com.sean.util.Arquivo.carregaInputStream;
import static com.sean.util.Arquivo.gravaImagemAtual;
import static com.sean.util.Constantes.IMAGEM;
import static com.sean.util.Constantes.IMAGEM_ATUAL;
import static com.sean.util.Constantes.INICIAR;
import static com.sean.util.Constantes.PAUSAR;
import static com.sean.util.Constantes.STATUS;
import static com.sean.util.Data.DateToString;
import static com.sean.util.Data.getDataAtual;
import static com.sean.util.Data.getHoraAtual;

import java.io.FileInputStream;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sean.R;
import com.sean.atividade.Atividade;
import com.sean.classe.Imagem;
import com.sean.data.repositorio.RepositorioImagem;
import com.sean.net.tarefa.TarefaAlterarMonitoramento;
import com.sean.net.tarefa.TarefaImagemAtual;
import com.sean.net.tarefa.TarefaObterStatus;

public class AtividadeMonitoramento extends Atividade {

	private ImageView imagemAtual;
	private Button botaoStatus;
	private RepositorioImagem repoImagem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_monitoramento);

		this.imagemAtual = (ImageView)findViewById(R.id.atividade_principal_imagem_atual);
		this.botaoStatus = (Button)findViewById(R.id.atividade_principal_botao_status);
		this.repoImagem = new RepositorioImagem(this);
		obterStatusMonitoramento();
	}

	public void chamaAtividadeListagem(View componente) {
		Intent intent = new Intent("listagem");
		startActivity(intent);
	}

	public void salvarImagem(View componente) {
		String dataAtual = DateToString(getDataAtual()) + getHoraAtual("-");
		FileInputStream imagemAtual = carregaInputStream(this, IMAGEM_ATUAL);
		gravaImagemAtual(this, imagemAtual, dataAtual);
		repoImagem.insert(new Imagem(dataAtual));
	}

	@Override
	protected void onResume() {
		obterStatusMonitoramento();
		getImagemAtual().setImageDrawable(carregaDrawable(this, IMAGEM_ATUAL));
		super.onResume();
	}

	public void obterStatusMonitoramento() {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();
		TarefaObterStatus tarefaObterStatus = new TarefaObterStatus(this, progressDialog);
		tarefaObterStatus.execute(STATUS);
	}

	public void alterarStatusMonitoramento(View componente) {

		String tipoInicial = botaoStatus.getText().toString();

		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Conectando ao servidor SEMON...");
		progressDialog.setCancelable(false);
		progressDialog.show();

		if (tipoInicial.equalsIgnoreCase("Iniciar")) {
			TarefaAlterarMonitoramento TarefaIniciar;
			TarefaIniciar = new TarefaAlterarMonitoramento(this, progressDialog, "Iniciando o Monitoramento do SEMON...");
			TarefaIniciar.execute(INICIAR);
			mostrarAlerta("Monitoramento Iniciado");
			botaoStatus.setText("Pausar");
		}
		else {
			TarefaAlterarMonitoramento TarefaPausar;
			TarefaPausar = new TarefaAlterarMonitoramento(this, progressDialog, "Pausando o Monitoramento do SEMON...");
			TarefaPausar.execute(PAUSAR);
			mostrarAlerta("Monitoramento Pausado");
			botaoStatus.setText("Iniciar");
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

	@Override
	public void onBackPressed() {
		verificaSaida("Deseja Sair do SEAN?");
	}

	public void sair(View componente) {
		verificaSaida("Deseja Sair do SEAN?");
	}

	public ImageView getImagemAtual() {
		return imagemAtual;
	}

	public Button getBotaoStatus() {
		return botaoStatus;
	}
}
