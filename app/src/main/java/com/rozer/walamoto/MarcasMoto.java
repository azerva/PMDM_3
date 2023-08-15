package com.rozer.walamoto;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que permite cargar una lista de marcas de coches a través de su método getMarcas(), el cual
 * obtiene las marcas a partir de un fichero de texto plano almacenado como recurso de la
 * aplicación.
 */

public class MarcasMoto {

    /**
     * Lista de marcas de coches.
     */
    private static List<String> listaMarcas;

    /**
     * Devuelve una lista de marcas de coches obtenidas a partir del fichero "marcas.txt" almacenado
     * en res/raw/.
     * La primera vez que se llama a este método, abre el fichero con las marcas, lo recorre línea a
     * línea (cada marca está en una línea) y añade la marca a una lista. Por último devuelve esta
     * lista. Por el contrario, el resto de veces en las que el método es llamado, al estar ya
     * cargada la lista, el método simplemente la devuelve.
     * @param context Contexto de la aplicación.
     * @return Una lista de marcas de coches obtenidas a partir del fichero "marcas.txt" almacenado
     * en res/raw/.
     * @throws IOException Al leer del fichero de texto "marcas.txt" guardado en res/raw.
     */
    public static List<String> getMarcas(Context context) throws IOException {
        if (listaMarcas == null) {
            listaMarcas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources()
                    .openRawResource(R.raw.lista_motos)))) {
                String marca;
                while ((marca = br.readLine()) != null) {
                    listaMarcas.add(marca);
                }
            }
        }
        return listaMarcas;
    }
}
