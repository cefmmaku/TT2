package com.tt.tt2.Algoritmos;

import java.util.ArrayList;

public class FiltradoTexto {

    public static ArrayList<String> filtroPalabras(String cadenota)
    {
        ArrayList<String> palabras = new ArrayList<>();
        StringBuilder cadenaTemporal = new StringBuilder();
        for(int i = 0; i < cadenota.length(); i++)
        {
            char caracter = cadenota.charAt(i);
            if(caracter >= 65 && caracter<= 90 || caracter >= 97 && caracter <= 122 || esLatina(caracter)) //Es una letra
            {
                cadenaTemporal.append(caracter);
            }
            else //Es un separador
            {
                if(cadenaTemporal.length() > 3)
                {
                    palabras.add(cadenaTemporal.toString());
                }
                cadenaTemporal = new StringBuilder();
            }

        }
        if(cadenaTemporal.length() > 3)
        {
            palabras.add(cadenaTemporal.toString());
        }
        return palabras;
    }

    private static boolean esLatina(char c)
    {
        char[] especiales = {'á','é','í','ó','ú','ü', 'ñ','Ñ','Á','É','Í','Ó','Ú','Ü'};
        for (char especiale : especiales) {
            if (c == especiale) {
                return true;
            }
        }
        return false;
    }
}
