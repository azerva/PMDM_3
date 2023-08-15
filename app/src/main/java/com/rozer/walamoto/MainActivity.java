package com.rozer.walamoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rozer.walamoto.activities.ListadoActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Abre la actividad ListadoActivity que contiene una lista de motos y un menú para insertar y eliminar motos
     * o cambiar las preferencias de visualización de la lista y si pulsamos en un objeto de la lista nos permitirá
     * modificar sus datos en otra Activity.
     * @param view Vista que se genera al pulsar el ImageButton
     */

    public void goListMoto(View view) {
        startActivity(new Intent(this, ListadoActivity.class));
        finish();
    }
}