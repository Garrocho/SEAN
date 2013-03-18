package com.sean.atividade.listagem;

import android.os.Bundle;
import android.widget.Gallery;

import com.sean.R;
import com.sean.atividade.Atividade;

public class AtividadeListagem extends Atividade {
	
	private Gallery galeria;
	private ImagemAdapter imagemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_listagem);
		
		this.galeria = (Gallery) findViewById(R.id.atividade_listagem_galeria);
	}
	
	@Override
	protected void onStart() {

		this.imagemAdapter = new ImagemAdapter(this);
		galeria.setAdapter(imagemAdapter);
		
		super.onStart();
	}
}
