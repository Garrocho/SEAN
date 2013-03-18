package com.sean.atividade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class Atividade extends Activity {
	
	public void mostrarErros(String mensagem) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setMessage(mensagem);
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}
	
	public void verificaSaida(String mensagem) {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Confirmacao")
		.setMessage(mensagem)
		.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				getThis().finish();
			}
		})
		.setNegativeButton("Nao", null)
		.show();
	}
	
	public void mostrarAlerta(String mensagem) {
		Toast msg = Toast.makeText(this, mensagem, Toast.LENGTH_SHORT);
		msg.show();
	}
	
	public Activity getThis() {
		return this;
	}
}
