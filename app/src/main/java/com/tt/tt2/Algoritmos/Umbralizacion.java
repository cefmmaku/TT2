package com.tt.tt2.Algoritmos;

public enum Umbralizacion {

    BINARIO (0), BINARIO_INVERTIDO (1), OTSU (8);

    private int tipo;
    Umbralizacion(int tipo)
        {
            this.tipo = tipo;
        }

    public int getTipo() {
        return tipo;
    }
}
