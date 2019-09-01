package com.tt.tt2.Algoritmos;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class ObtencionDeRuta {

    private static ArrayList<String> paradas = new ArrayList<>(Arrays.asList("TENO", "MOVI", "LOMAS", "SORIANA", "ALBORADAS",
            "ALAMOS", "CERRITO", "AUTOPISTA", "CEYLAN", "TEQUEX", "VISI"));

    public static String obtenRuta(String textoextraido)
        {
            Log.d("Debuggeando", textoextraido);
            for(int i = 0; i < paradas.size(); i++)
                {
                    if(textoextraido.contains(paradas.get(i)))
                        {
                            return "La ruta es Politécnico - Cuautitlán";
                        }
                }
            return "Ruta desconocida";
        }
}
