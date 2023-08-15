package com.rozer.walamoto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MotoDB extends SQLiteOpenHelper {

    public static final String NAME_DB = "walamotozerva.sqlite";
    public static final int VERSION_DB = 1;

    public MotoDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAME_DB, factory, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TablaMoto.CREAR_TABLA_MOTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TablaMoto.TABLA_MOTOS);
        onCreate(db);
    }


}
