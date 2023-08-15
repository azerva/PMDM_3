package com.rozer.walamoto.entidades;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Entidad que nos ayudará a verificar si es la primera vez que abrimos la Aplicación o no. En caso
 * afirmativo mostraremos un mensaje al acceder a la pantalla ListadoActivity.
 */
public class PreferenciasApp {

    public static final String SMS_ACCESS_FIRST_TIME = "sms";
    public static final String KEY_PREFERENCIAS = "key";
    public static final String STOCK_MOTOS = "stock";

    public static boolean stockMotos;


    public static void saveValuePreference(Context context, Boolean acceso_inicial) {
        SharedPreferences settings = context.getSharedPreferences(SMS_ACCESS_FIRST_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("acceso_inicial", acceso_inicial);
        editor.commit();
    }

    public static boolean getValuePreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SMS_ACCESS_FIRST_TIME, Context.MODE_PRIVATE);
        return  preferences.getBoolean("acceso_inicial", true);
    }


    public static void savePreferencesMenu(Context context,Boolean falso) {

        SharedPreferences menuPref = context.getSharedPreferences(STOCK_MOTOS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editorMenu;
        editorMenu = menuPref.edit();
        editorMenu.putBoolean(KEY_PREFERENCIAS,falso);
        editorMenu.commit();
    }

    public static boolean getPreferencesMenu(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(STOCK_MOTOS, Context.MODE_PRIVATE);
        return  preferences.getBoolean(KEY_PREFERENCIAS, true);
    }



}
