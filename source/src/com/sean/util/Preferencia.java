package com.sean.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencia {
	public static final String NOME_PREFERENCIAS = "SEMON_PREF";
	
	private SharedPreferences configuracoes;
	private Context contexto;

	public Preferencia(Context contexto) {
		
		// Restaura as preferencias gravadas
		this.contexto = contexto;
		configuracoes = this.contexto.getSharedPreferences(NOME_PREFERENCIAS, 0);
	}

	public Map<String, ?> getPreferencias(){
		if(configuracoes != null){
			return configuracoes.getAll();
		}
		return null;
	}

	public void addStringPreferencia(String nome, String valor){
		SharedPreferences.Editor editor = configuracoes.edit();
		editor.putString(nome, valor);

		// Confirma a gravacao dos dados
		editor.commit();
	}
	
	public void addIntPreferencia(String nome, int valor){
		SharedPreferences.Editor editor = configuracoes.edit();
		editor.putInt(nome, valor);

		// Confirma a gravacao dos dados
		editor.commit();
	}
}