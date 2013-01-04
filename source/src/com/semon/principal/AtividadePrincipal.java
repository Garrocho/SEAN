package com.semon.principal;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
						resultado = conexao.byteParaString(conexao.getRecebeDados());
						if (resultado.equalsIgnoreCase("OK")) {
							resultado = "Requisição Correta";
						}
						else {
							resultado = "Requisição Incorreta";
						}
					}
					else {
						resultado = "Não Foi Possível Estabelecer Conexão Com o Servidor.";
					}
				} catch (IOException e) {
					resultado = "Não Foi Possível Estabelecer Conexão Com o Servidor.";
				}
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getThis(), resultado, Toast.LENGTH_SHORT).show();
					}
				});
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
}
