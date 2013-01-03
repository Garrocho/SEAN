package com.semon;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class AtividadeLogin extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.atividade_login, menu);
        return true;
    }
    
    public void logar(View componente) {
    	
    }
    
    public void cancelar(View componente) {
    	finish();
    }
}
