package com.rozer.walamoto.entidades;

import java.io.Serializable;
import java.util.ArrayList;

public class Motos implements Serializable {

    int id;
    String marca;
    String modelo;
    int km;
    int year;
    int cc;
    int cv;
    int precio;
    boolean vendido;

    ArrayList<Motos> moto;

    public Motos() {
    }

    public Motos(String marca, String modelo, int km, int year, int cc, int cv, int precio, boolean vendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.km = km;
        this.year = year;
        this.cc = cc;
        this.cv = cv;
        this.precio = precio;
        this.vendido = vendido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public int getCv() {
        return cv;
    }

    public void setCv(int cv) {
        this.cv = cv;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isVendido() {
        return vendido;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }


}
