package com.tt.tt2.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GestorData {
    private ArrayList<Paradas> mParadas;

    public void initData(){
        mParadas = new ArrayList<>();
        mParadas.add(new Paradas("tenayucan", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria", "Politécnico - Temoaya"))));
        mParadas.add(new Paradas("ceylan", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria", "Politécnico - Temoaya"))));
        mParadas.add(new Paradas("tabla", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("honda", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("recuerdo", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria", "Politécnico - Temoaya"))));
        mParadas.add(new Paradas("jardines", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria", "Politécnico - Temoaya"))));
        mParadas.add(new Paradas("tequex", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("perinorte", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("lecheria", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("bacardi", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("dhl", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("ford", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("koblens", new ArrayList<String>(Arrays.asList("Politécnico - Cuautitlan Izcalli Infonavit Norte", "Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("infonavit", new ArrayList<String>(Collections.singletonList("Politécnico - Cuautitlan Izcalli Infonavit Norte"))));
        mParadas.add(new Paradas("norte", new ArrayList<String>(Collections.singletonList("Politécnico - Cuautitlan Izcalli Infonavit Norte"))));
        mParadas.add(new Paradas("cuautitlan", new ArrayList<String>(Collections.singletonList("Politécnico - Cuautitlan Izcalli Infonavit Norte"))));
        mParadas.add(new Paradas("soriana", new ArrayList<String>(Collections.singletonList("Politécnico - Cuautitlan Izcalli Infonavit Norte"))));
        mParadas.add(new Paradas("mega", new ArrayList<String>(Collections.singletonList("Politécnico - Cuautitlan Izcalli Infonavit Norte"))));
        mParadas.add(new Paradas("alamos", new ArrayList<String>(Collections.singletonList("Politécnico - Cuautitlan Izcalli Infonavit Norte"))));
        mParadas.add(new Paradas("monte", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("maria", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("lago", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("autopista", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("recuerdo", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("nicolas", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("romero", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("isidro", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("maria", new ArrayList<String>(Collections.singletonList("Politécnico - Monte Maria"))));
        mParadas.add(new Paradas("patera", new ArrayList<String>(Collections.singletonList("Politécnico - San Juan Ixtacala"))));
        mParadas.add(new Paradas("torres", new ArrayList<String>(Collections.singletonList("Politécnico - San Juan Ixtacala"))));
        mParadas.add(new Paradas("perlillar", new ArrayList<String>(Collections.singletonList("Politécnico - San Juan Ixtacala"))));
        mParadas.add(new Paradas("san", new ArrayList<String>(Collections.singletonList("Politécnico - San Juan Ixtacala"))));
        mParadas.add(new Paradas("juan", new ArrayList<String>(Collections.singletonList("Politécnico - San Juan Ixtacala"))));
        mParadas.add(new Paradas("ixtacala", new ArrayList<String>(Collections.singletonList("Politécnico - San Juan Ixtacala"))));
        mParadas.add(new Paradas("comandancia", new ArrayList<String>(Collections.singletonList("Politécnico - San Juan Ixtacala"))));
        mParadas.add(new Paradas("santa", new ArrayList<String>(Arrays.asList("Politécnico - San Juan Ixtacala","Politécnico - Temoaya"))));
        mParadas.add(new Paradas("rosa", new ArrayList<String>(Arrays.asList("Politécnico - San Juan Ixtacala","Politécnico - Temoaya"))));
        mParadas.add(new Paradas("vallejo", new ArrayList<String>(Collections.singletonList("Politécnico - Temoaya"))));
        mParadas.add(new Paradas("covadonga", new ArrayList<String>(Collections.singletonList("Politécnico - Temoaya"))));

    }

    public ArrayList<String> getRutasAsociadas(String paradasOCR)
        {
            ArrayList<String> mRutas = new ArrayList<>();
            for(Paradas parada: mParadas)
                {
                    if(paradasOCR.contains(parada.getNombreRuta()))
                        {
                            mRutas.addAll(parada.getRutaAsociada());
                        }
                }
            return  mRutas;
        }
}
