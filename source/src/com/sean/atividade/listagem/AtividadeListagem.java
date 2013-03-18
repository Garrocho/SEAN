package com.sean.atividade.listagem;

import android.os.Bundle;
import android.widget.Gallery;

import com.sean.R;
import com.sean.atividade.Atividade;
import com.sean.data.repositorio.RepositorioImagem;

public class AtividadeListagem extends Atividade {
	
	private Gallery galeria;
	private ImagemAdapter imagemAdapter;
	private RepositorioImagem repoImagem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_listagem);
		this.galeria = (Gallery) findViewById(R.id.atividade_listagem_galeria);
		
		this.repoImagem = new RepositorioImagem(this);
	}
	
	@Override
	protected void onStart() {
		
		this.imagemAdapter = new ImagemAdapter(this, repoImagem.listarImagens());
		galeria.setAdapter(imagemAdapter);
		super.onStart();
	}
}
