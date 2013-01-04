package com.semon.principal;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.semon.R;

public class AtividadePrincipal extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atividade_principal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.atividade_principal, menu);
        return true;
    }
}
