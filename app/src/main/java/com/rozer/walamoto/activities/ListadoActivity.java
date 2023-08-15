package com.rozer.walamoto.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rozer.walamoto.R;
import com.rozer.walamoto.adapatador.MotosListaAdapter;
import com.rozer.walamoto.database.MotoDB;
import com.rozer.walamoto.database.TablaMoto;
import com.rozer.walamoto.entidades.Motos;
import com.rozer.walamoto.entidades.PreferenciasApp;
import com.rozer.walamoto.preferencias.PreferenciasActivity;

import java.util.ArrayList;

/**
 * Actividad en la cual podremos visualizar los datos introducidos en la base de datos en un RecyclerView,
 * el cuál al pulsar sobre un objeto Moto nos derivará a otra Activity en la cual podremos modificar los
 * datos de ese objeto.
 *
 * Dispondremos de un menú con las opciones de insertar o eliminar un objeto Motos. Para ello nos derivará
 * a otro Activity dondé podremos crear un objeto nuevo o nos derivará a otro Activity que recupera los datos
 * de la base de datos y así poder eliminar los objetos que se deseen.
 *
 * Finalmante dispondremos de una opción de preferecias en el menú con la cuál podremos mostrar todas las motos
 * o sólo que están vendidas
 *
 */

public class ListadoActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    /**
     * MotosListaAdapter,ArrayList<Motos>,RecyclerView, son variables que usaremos para crear la visualizazción
     * de la información en el ListadoActivity
     */
    MotosListaAdapter adapter;
    ArrayList<Motos> listamotos;
    RecyclerView recyclerView;
    /**
     * Barra de menú creada para visualizar el menú personalizado.
     */
    Toolbar toolbar;
    /**
     * Permite acceder a la base de datos.
     */
    MotoDB motoDB= new MotoDB(ListadoActivity.this,MotoDB.NAME_DB,null,MotoDB.VERSION_DB);
    /**
     * Permite acceder a las preferencias de la aplicación y así poder modificarlas al gusto del usuario.
     */
    SharedPreferences preferences;
    /**
     * Variable condicional con usaremos para idicar si se deben mostrar todas las motos o sólo
     * las que estén en venta.
     */
    boolean stockMotos;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        //Asociamos el toolbar y le damos el soporte para visualizarlo.
        //toolbar = findViewById(R.id.toolbar_listado);
        //setSupportActionBar(toolbar);

        //Iniciamos el recyclerview para mostrar las motos que hay en la base de datos.
            startRecyclerView();
        //Genera un mensaje la primera vez que abrimos la aplicación.
        smsFirstTiemOpenApp();
        preferencesMenu();
        //Establece los valores por defecto al iniciar la aplicación.
        PreferenceManager.setDefaultValues(this,R.xml.preferencias,false);
            /**
             * He inventado bastantes cosas y no soy capaz de dar con la tecla para que al cambiar las preferencia
             * cambie la lista del recyclerview.
             */
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        stockMotos = preferences.getBoolean("key",true);

        }


    @Override
    protected void onStart() {
        super.onStart();
        preferences.registerOnSharedPreferenceChangeListener(this);
        }

    @Override
    protected void onStop() {
        super.onStop();
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getlistaMotos();
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //PreferenciasApp.guardarPreferenciasMenu(sharedPreferences);
        stockMotos = sharedPreferences.getBoolean("key",true);
        getlistaMotos();
    }

    /**
     * Método con el cual se pretende que si el stockMotos está en true ()
     */
    public void getlistaMotos(){
        if (!stockMotos){//lo pongo el falso porque así visualizamos toda la lista, no consigo que me cambie la lista al cambiar las preferencias.
            allMotos();
        }else motosDisponibles();

    }

    /**
     * Método con el cual averiguamos si la aplicacion se abre por primera vez, en caso afirmativo se
     * mostrará un mensaje solicitando que se cambien las preferencias o que se agreguen motos a la base
     * de datos, ya que, el recyclerView está vacio al no disponer de información de la base de datos.
     */
    private void smsFirstTiemOpenApp() {
        //Ontiene el valor de la referencia. La primera vez es true por defecto.
        boolean mensaje = PreferenciasApp.getValuePreference(getApplicationContext());

    /*
    Valida si muestra o no el mensaje. En caso de no ser la primera vez que se abre la aplicación
    será false y no mostrará el mensaje, en caso contrario mostrará un mensaje inicial.
     */
        if (mensaje){
            PreferenciasApp.saveValuePreference(getApplicationContext(),false);
            Toast.makeText(getApplicationContext(), R.string.mensaje_inicial, Toast.LENGTH_SHORT).show();
        }
    }

    private void preferencesMenu(){
        boolean menu = PreferenciasApp.getPreferencesMenu(getApplicationContext());
        if (menu){
            PreferenciasApp.savePreferencesMenu(getApplicationContext(),false);
            getlistaMotos();
        }

    }

    /**
     * Inicia el RecyclerView que muestra los coches haciendo uso de un layout manager. Así mismo,
     * también le asocia el adaptador y establece el listener.
     */
    private void startRecyclerView() {
        listamotos = new ArrayList<>();
        //Relacionamos la parte lógica del recycler con la parte grafica que va a mostrar los datos.
        recyclerView = findViewById(R.id.rv_lista_motos);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

        getlistaMotos();
        //allMotos();
        //Crea el evento que debe de realizar la aplicación al pulsar sobre una moto.
        MotosListaAdapter.OnEventClickListener listener = motos -> verFicha(motos);

        //Inicializamos el adaptador y se lo pasamos al reyclerview.
        adapter = new MotosListaAdapter(listamotos,listener);
        recyclerView.setAdapter(adapter);

    }

    /**
     * Al pulsar nos redirije a la pantalla FichaActivity con los datos del objeto seleccionado, para
     * viualizar todos los datos sobre ese objeto y poder actualizarlo.
     * @param motos Pulsamos para actualizar o visualizar.
     */
    private void verFicha(Motos motos) {
        Intent i = new Intent(ListadoActivity.this,FichaActivity.class);
        i.putExtra("moto", motos);
        startActivity(i);
    }

    /**
     * Muestra un menú en la actividad para poder abrir las preferencias, la pantalla para insertar
     * motos o la pantalla para eliminarlos. Para ello obtiene una referencia al objeto Inflater
     * con el método getMenuInflater(), para después generar el menú con el método inflate() al que
     * se le pasa el identificador.
     * @param menu Opciones del menú
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listado_activity,menu);
        return true;
    }
    /**
     * Comprueba qué opción del menú se ha seleccionado, y lanza una actividad para que cambie las
     * preferencias del listado, para que inserte un coche o para ver una pantalla donde se muestran
     * todos las motos y desde la cual puede eliminar los que quiera.
     * @param item Elemento del menú seleccionado por el usuario.
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int items = item.getItemId();

        if (items == R.id.preferenciasMenu) {
            Intent pref = new Intent(this, PreferenciasActivity.class);
            startActivity(pref);
        }else if (items == R.id.insertMenu) {
            Intent insert = new Intent(this, FichaActivity.class);
            startActivity(insert);
        }else if (items == R.id.deleteMenu) {
            Intent del = new Intent(this, EliminarActivity.class);
            startActivity(del);
        }

        return super.onOptionsItemSelected(item);
    }
    public void allMotos(){
        listamotos = new ArrayList<>();
        SQLiteDatabase db = motoDB.getReadableDatabase();

        Motos motos = null;
        Cursor allmotos = db.rawQuery("select * from "+TablaMoto.TABLA_MOTOS+" where "+
                TablaMoto.CN_VENDIDO+ " = 1 or "+TablaMoto.CN_VENDIDO+ "= 0",null);
        while(allmotos.moveToNext()){
            motos = new Motos();
            motos.setId(allmotos.getInt(0));
            motos.setMarca(allmotos.getString(1));
            motos.setModelo(allmotos.getString(2));
            motos.setKm(allmotos.getInt(3));
            motos.setYear(allmotos.getInt(4));
            motos.setCc(allmotos.getInt(5));
            motos.setCv(allmotos.getInt(6));
            motos.setPrecio(allmotos.getInt(7));
            //Obtenemos el valor con el cursor y lo convertimos a booleano real.
            motos.setVendido(allmotos.getString(8).equals("1"));

            listamotos.add(motos);
        }

        db.close();

    }
    public  void motosDisponibles(){
        listamotos = new ArrayList<>();
        SQLiteDatabase db = motoDB.getReadableDatabase();

        Motos motos = null;
        Cursor vendidas = db.rawQuery("select * from "+TablaMoto.TABLA_MOTOS+ " where "+
                TablaMoto.CN_VENDIDO+ "= 0",null);
        while(vendidas.moveToNext()){
            motos = new Motos();
            motos.setId(vendidas.getInt(0));
            motos.setMarca(vendidas.getString(1));
            motos.setModelo(vendidas.getString(2));
            motos.setKm(vendidas.getInt(3));
            motos.setYear(vendidas.getInt(4));
            motos.setCc(vendidas.getInt(5));
            motos.setCv(vendidas.getInt(6));
            motos.setPrecio(vendidas.getInt(7));
            //Obtenemos el valor con el cursor y lo convertimos a booleano real.
            motos.setVendido(vendidas.getString(8).equals("1"));

            listamotos.add(motos);
        }

        db.close();

    }


}

