package com.rozer.walamoto.preferencias;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rozer.walamoto.R;


/**
    * Actividad que implementa el Fragment con las preferencias.
    */
public class PreferenciasActivity extends AppCompatActivity {
    /**
     * Fragment que guarda las preferencias del usuario.
     */
    PreferenciasFragment preferenciasFragment;
    /**
     * Ejecuta la lógica de arranque básica de la actividad cuando el sistema crea la actividad
     * por primera vez.
     * @param savedInstanceState Objeto Bundle que contiene el estado ya guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        if (preferenciasFragment == null){
            preferenciasFragment = new PreferenciasFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_preferencias,preferenciasFragment).commit();
    }

}