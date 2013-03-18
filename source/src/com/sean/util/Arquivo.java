package com.sean.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Arquivo {

	public static void gravaImagem(Context contexto, InputStream inPut, String nomeArquivo) {
		try {
			byte[] buffer = new byte[512];
			FileOutputStream fis = contexto.openFileOutput(nomeArquivo, Activity.MODE_PRIVATE);
			int count;
			while ((count = inPut.read(buffer)) > 0) {
				fis.write(buffer, 0, count);
				fis.flush();  
			}
			fis.close();
		}catch (Exception e) {
			Log.d("erro", e.toString());
		}
	}
	
	public static void gravaImagemAtual(Context contexto, FileInputStream imagemAtual, String nomeArquivo) {
		
		try {
			byte[] buffer = new byte[512];
			FileOutputStream fis = contexto.openFileOutput(nomeArquivo, Activity.MODE_PRIVATE);
			int count;
			while ((count = imagemAtual.read(buffer)) > 0) {
				fis.write(buffer, 0, count);
				fis.flush();  
			}
			fis.close();
		}catch (Exception e) {
			Log.d("erro", e.toString());
		}
	}
	
	public static Drawable carregaDrawable(Context contexto, String nomeArquivo) {
		try {
			return new BitmapDrawable(contexto.openFileInput(nomeArquivo));
		} catch (FileNotFoundException e) {
			Log.d("erro", e.toString());
		}
		return null;
	}
	
	public static FileInputStream carregaInputStream(Context contexto, String nomeArquivo) {
		try {
			return contexto.openFileInput(nomeArquivo);
		} catch (FileNotFoundException e) {
			Log.d("erro", e.toString());
		}
		return null;
	}
}
