package com.tt.tt2.Data;

import java.util.ArrayList;

public class Paradas {
    String nombreRuta;
    ArrayList<String> rutaAsociada;

    public Paradas(String nombreRuta, ArrayList<String> rutaAsociada) {
        this.nombreRuta = nombreRuta;
        this.rutaAsociada = rutaAsociada;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public ArrayList<String> getRutaAsociada() {
        return rutaAsociada;
    }
}
