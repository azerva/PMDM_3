package com.rozer.walamoto.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.rozer.walamoto.MarcasMoto;
import com.rozer.walamoto.R;
import com.rozer.walamoto.database.MotoDB;
import com.rozer.walamoto.database.TablaMoto;
import com.rozer.walamoto.entidades.Motos;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Activity que se utilizará para la actualización o creación de una moto.
 */

public class FichaActivity extends AppCompatActivity {

    Motos motos;

    private Spinner spnMarcas;
    private EditText etmodelo,etkm,etyear,etcc,etcv,etprecio;
    private CheckBox vendido;
    Button btn_insert,btn_update;

    ArrayAdapter<String> adapterString;
    MotoDB motoDB= new MotoDB(FichaActivity.this,MotoDB.NAME_DB,null,MotoDB.VERSION_DB);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);
        //Recuperamos el objeto enviado desde ListadoActivity
        motos = (Motos) getIntent().getSerializableExtra("moto");

        //Relación entre los componentes gráficos y lógicos
        btn_insert = findViewById(R.id.btn_inserFicha);
        btn_update = findViewById(R.id.btn_updateFicha);

        spnMarcas = findViewById(R.id.spinner_marca_ficha);
        etmodelo = findViewById(R.id.modelo_ficha);
        etkm = findViewById(R.id.km_ficha);
        etyear = findViewById(R.id.anio_ficha);
        etcc = findViewById(R.id.cc_ficha);
        etcv = findViewById(R.id.cv_ficha);
        etprecio =findViewById(R.id.precio_ficha);
        vendido = findViewById(R.id.vendido_ficha);
        //Iniciamos el espiner
        initSpinner();

        recuperarDatos();

    }

    /**
     * Método que se utiliza para recibir el objeto Motos y colocar los datos del objeto recibidos en
     * sus repectivos EditText para poder modificarlos.
     */
    private void recuperarDatos(){
        if (motos != null){

            btn_insert.setVisibility(View.GONE);
            spnMarcas.setSelection(adapterString.getPosition(motos.getMarca()));
            etmodelo.setText(motos.getModelo());
            etkm.setText(String.valueOf(motos.getKm()));
            etyear.setText(String.valueOf(motos.getYear()));
            etcc.setText(String.valueOf(motos.getCc()));
            etcv.setText(String.valueOf(motos.getCv()));
            etprecio.setText(String.valueOf(motos.getPrecio())+" €");
            vendido.setChecked(motos.isVendido());
            if (motos.isVendido()){
                vendido.setChecked(true);
            }else vendido.setChecked(false);

        }else{
            btn_update.setVisibility(View.GONE);
            //vendido.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Iniciamos el Spinner agregando la lista de marcas de motos y creando un mensaje en el caso
     * de poder inicializarla.
     */
    private void initSpinner() {
        try {
            List<String> listaMarcas = MarcasMoto.getMarcas(getApplicationContext());
            // Inicializa el ArrayAdapter a partir de la lista de las marcas.
            adapterString = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMarcas);
            /*
             * Especifica el layout a usar cuando se muestra la lista de coches. En este caso se usa
             * android.R.layout.simple_spinner_dropdown_item, que es uno básico que incluye Android.
             */
            adapterString.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Establece el adaptador al Spinner.
            this.spnMarcas.setAdapter(adapterString);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), R.string.cargar_lista_motos, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * El boton nos cambia a la pantalla ListadoActivity.
     * @param view Nos devuelve el view pulsado
     */
    public void irListado(View view) {
        Intent i = new Intent(this,ListadoActivity.class);
        startActivity(i);
    }

    /**
     * Método con el cuál nos da las opciones de agregar o modificar motos en función del botón que
     * pulsemos.
     */
    public void insertUpdateFicha(View view) {

        switch (view.getId()){
            case R.id.btn_updateFicha:
                messageUpdate();
                break;
            case R.id.btn_inserFicha:
                if (comprobarCampos()){
                    addMoto();
                }else{
                    //Toast.makeText(this, "Rellene los campos vacios", Toast.LENGTH_SHORT).show();
                    Snackbar.make(findViewById(R.id.layout_ficha),"Rellene los campos vacios",Snackbar.LENGTH_LONG).show();
                }
        }

    }

    private boolean comprobarCampos() {
        boolean camposValidos = false;
        List<EditText> listaEtFicha = Arrays.asList(etmodelo,etkm,etyear,etcc,etcv,etprecio);

        if (!listaEtFicha.isEmpty()){
            camposValidos = true;
        }

        return camposValidos;
    }

    /**
     * Método con el cual accedemos a la base de datos en modo escritura, creamos un contenedor con
     * los datos que deseamos insertar y su sentencia sql con los parametros de la tabla seleccionada,
     * las condiciones que normalmente siempre son null y los valores del contentvalues.
     *
     * Finalmente cerramos la base de datos para evitar errores y generamos un AlertDialog para saber
     * se desea inertar otra moto o no.
     */
    private void addMoto() {

        SQLiteDatabase db = motoDB.getWritableDatabase();

        ContentValues moto = new ContentValues();
        moto.put(TablaMoto.CN_MARCA,spnMarcas.getSelectedItem().toString());
        moto.put(TablaMoto.CN_MODELO,etmodelo.getText().toString());
        moto.put(TablaMoto.CN_KILOMETROS,etkm.getText().toString());
        moto.put(TablaMoto.CN_YEAR,etyear.getText().toString());
        moto.put(TablaMoto.CN_CC,etcc.getText().toString());
        moto.put(TablaMoto.CN_CV,etcv.getText().toString());
        moto.put(TablaMoto.CN_PRECIO,etprecio.getText().toString());
        //Si la moto está vendida colocaremos un 1 en la base de datos en caso contrario un 0
        moto.put(TablaMoto.CN_VENDIDO, vendido.isChecked() ? 1 : 0);

        db.insert(TablaMoto.TABLA_MOTOS,null,moto);

        db.close();

        messageNewMoto();

    }

    /**
     * Método que utilizamos despues de pulsar el botón insertar para solicitar al usuario si desea
     * agregar otra moto mediante un AlertDialog con dos opciones.
     *
     * En caso afirmativo limpiamos los campos y permanecems en la misma pantalla, en caso negativo
     * volvemos a la pantalla ListadoActivity.
     */
    private void messageNewMoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(R.string.nuevaMoto)
                .setMessage(R.string.alertMessageAdd)
                .setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Envio de mensaje al usuario para informar que se ha insertado la moto.
                        Toast.makeText(FichaActivity.this, R.string.insert_mensaje, Toast.LENGTH_SHORT).show();
                        limparCampos();
                    }
                })
                .setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(FichaActivity.this, ListadoActivity.class);
                        Toast.makeText(FichaActivity.this, R.string.insert_mensaje, Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                });
        builder.create().show();
    }

    /**
     * Método utilizado para limpiar los campos EditText.
     */

    private void limparCampos() {
        spnMarcas.setSelection(0);
        etmodelo.setText("");
        etkm.setText("");
        etyear.setText("");
        etcc.setText("");
        etcv.setText("");
        etprecio.setText("");
        vendido.setChecked(false);
    }
    /**
     * Método que utilizamos al pulsar el botón actualizar para solicitar al usuario confirmación
     * para  modificar los datos del objeto Motos en la base de datos.
     *
     * En caso afirmativo nos dirigemos al método updateMoto en caso de no quere guardar los cambios
     * volveremos al ListadoActivity sin modificar los datos de la base de datos.
     */
    private void messageUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(R.string.alertTittleUpdate)
                .setMessage(R.string.alertMessageUpdate)
                .setPositiveButton(R.string.alert_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateMoto(motos);
                    }
                })
                .setNegativeButton(R.string.alert_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(FichaActivity.this,ListadoActivity.class);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }

    /**
     * Método para modificar el objeto Motos conectadno con la base de datos en modo escritura.
     * @param motos Objeto moto que se va a modificar.
     */
    private void updateMoto(Motos motos) {
        //conexion con la base de datos en modo escritura para poder modificar los datos.
        SQLiteDatabase db = motoDB.getWritableDatabase();

        String[] parametros ={String.valueOf(motos.getId())};
        // contenedor de datos que se desean modificar.
        ContentValues moto = new ContentValues();
        moto.put(TablaMoto.CN_MARCA,spnMarcas.getSelectedItem().toString());
        moto.put(TablaMoto.CN_MODELO,etmodelo.getText().toString());
        moto.put(TablaMoto.CN_KILOMETROS,etkm.getText().toString());
        moto.put(TablaMoto.CN_YEAR,etyear.getText().toString());
        moto.put(TablaMoto.CN_CC,etcc.getText().toString());
        moto.put(TablaMoto.CN_CV,etcv.getText().toString());
        moto.put(TablaMoto.CN_PRECIO,etprecio.getText().toString());
        moto.put(TablaMoto.CN_VENDIDO, vendido.isChecked() ? 1 : 0);
        /*
        Sentencia sql para agregar los datos que recibe cuatro parametros:
        El metodo update recibe el nombre de la tabla, el objeto contentvalues con sus valores
        la condición o clausula where, y los argumentos de la condición.
         */
        db.update(TablaMoto.TABLA_MOTOS,moto,TablaMoto.CN_ID+"=?" ,parametros);
        //Cierra la base de datos para evitar errores.
        db.close();
        /*
        Volvemos a la pantalla ListadoActivity y genereamos un mensaje informando al usuario que
        el estado del objeto a sido actualizado.
         */
        Intent intent = new Intent(FichaActivity.this,ListadoActivity.class);
        Toast.makeText(FichaActivity.this, R.string.update_mensaje, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}