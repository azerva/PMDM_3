package com.rozer.walamoto.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.rozer.walamoto.R;
import com.rozer.walamoto.adapatador.EliminarMotoAdapter;
import com.rozer.walamoto.database.MotoDB;
import com.rozer.walamoto.database.TablaMoto;
import com.rozer.walamoto.entidades.Motos;

import java.util.ArrayList;

/**
 * Activity que intoduce los objetos de la base de datos en el recyclerview, en el cual disponemos
 * de un botón para eliminar los objetos que se deseen.
 */

public class EliminarActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Motos>listamotos;
    EliminarMotoAdapter adapter;

    MotoDB motoDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        //Conectamos con la base de datos.
        motoDB = new MotoDB(this,MotoDB.NAME_DB,null,MotoDB.VERSION_DB);
        //Inicamos el recyclerView.
        startRecyclerView();

    }

    /**
     * Inicia el RecyclerView que muestra los coches haciendo uso de un layout manager. Así mismo,
     * también le asocia el adaptador y establece el listener.
     */
    private void startRecyclerView() {
        //Relacionamos la parte lógica del recycler con la parte grafica que va a mostrar los datos.
        recyclerView = findViewById(R.id.rv_delete);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

        //Método para adjuntar los datos de la base de datos en el recyclerview.
        addMotos();

        //Crea el evento que debe de realizar la aplicación al pulsar sobre una moto.
        EliminarMotoAdapter.OnEventClickListener listener = moto -> deletemoto(moto);

        //Inicializamos el adaptador y se lo pasamos al reyclerview.
        adapter = new EliminarMotoAdapter(listamotos,listener);
        recyclerView.setAdapter(adapter);
    }
    /**
     * Método al que llamamos para rellenar el recyclervew accediendo en modo lectura a la base de datos
     * y creando un cursor con una rawquery nos servirá para recorrer la base de datos y mostrar por
     * pantalla la lista de los objetos que coinciden con la sentencia rawquery creada.
     * Finalmente cerramos la base de datos para evitar errores posteriores.
     */
    private void addMotos() {

        listamotos = new ArrayList<>();

        SQLiteDatabase db = motoDB.getReadableDatabase();
        Motos motos =null;
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TablaMoto.TABLA_MOTOS,null);

        while(cursor.moveToNext()){
            motos = new Motos();
            motos.setId(cursor.getInt(0));
            motos.setMarca(cursor.getString(1));
            motos.setModelo(cursor.getString(2));
            motos.setKm(cursor.getInt(3));
            motos.setYear(cursor.getInt(4));

            listamotos.add(motos);
        }
    }

    /**
     * Método del objeto Motos que nos permite eliminar los datos recibidos como parámetros.
     * @param motos Moto a eliminar de la base de datos
     */

    private void deletemoto(Motos motos) {

        SQLiteDatabase db = motoDB.getWritableDatabase();

        String [] parametros = {String.valueOf(motos.getId())};
        //Sentencia delete para eliminar la moto de la base de datos.
        db.delete(TablaMoto.TABLA_MOTOS, TablaMoto.CN_ID + " = ? ",parametros);
        //Cerramos la base de datos.
        db.close();

        Intent refresh = new Intent (EliminarActivity.this,EliminarActivity.class);
        Toast.makeText(this, R.string.delete_mensaje, Toast.LENGTH_SHORT).show();
        startActivity(refresh);
    }

    public void irListado(View view) {
        startActivity(new Intent(EliminarActivity.this,ListadoActivity.class));
    }
}