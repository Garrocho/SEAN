package com.semon.principal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.semon.R;
import com.semon.recursos.Conexao;

public class AtividadePrincipal extends Activity {

	private String resultado;
	private ImageView imagemAtual;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atividade_principal);

		this.imagemAtual = (ImageView)findViewById(R.id.atividade_principal_imagem_atual);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.atividade_principal, menu);
		return true;
	}

	public void obterImagemAtual(View componente) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					Conexao conexao = new Conexao();
					if (conexao.conectaServidor()) {
						conexao.getEnviaDados().write(new String("IMAGEM").getBytes());
						conexao.getEnviaDados().flush();
						int count;
						try {
							byte[] buffer = new byte[512];
							FileOutputStream fis = openFileOutput("imagem.jpg", MODE_WORLD_WRITEABLE);
							while ((count = conexao.getRecebeDados().read(buffer)) > 0) {
								fis.write(buffer, 0, count); 
								fis.flush();  
							}
							fis.close();
							runOnUiThread(new Runnable() {
								public void run() {
									try {
										getImagemAtual().setImageDrawable(new BitmapDrawable(openFileInput("imagem.jpg")));
									} catch (FileNotFoundException e) {
									}
								}
							});
						} catch (Exception e) {
							Log.d("erro", e.toString());
						}
					}
				} catch (IOException e) {
					resultado = "Não Foi Possível Estabelecer Conexão Com o Servidor.";
				}
			}
		});
		t.start();
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
