package com.sean.atividade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

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

}
