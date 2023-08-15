package com.rozer.walamoto.database;

public class TablaMoto {

    public static final String TABLA_MOTOS ="motos";
    public static final String CN_ID ="id";
    public static final String CN_MARCA ="marca";
    public static final String CN_MODELO ="modelo";
    public static final String CN_KILOMETROS ="kilometros";
    public static final String CN_YEAR ="año";
    public static final String CN_CC ="cc";
    public static final String CN_CV ="cv";
    public static final String CN_PRECIO ="precio";
    public static final String CN_VENDIDO ="venta";
    public static final String CREAR_TABLA_MOTOS = "CREATE TABLE " + TABLA_MOTOS + " ("
            + CN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CN_MARCA + " TEXT,"
            + CN_MODELO + " TEXT," + CN_KILOMETROS + " INTEGER,"
            + CN_YEAR + " INTEGER," + CN_CC + " INTEGER," + CN_CV + " INTEGER,"
            + CN_PRECIO + " INTEGER," + CN_VENDIDO + " INTEGER DEFAULT 0);";

    //CHECK ("+CN_VENDIDO+ " IN (0,1)) ----- DEFAULT 0
}
