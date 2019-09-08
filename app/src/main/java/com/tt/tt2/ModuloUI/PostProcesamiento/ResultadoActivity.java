package com.tt.tt2.ModuloUI.PostProcesamiento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tt.tt2.Data.GestorData;
import com.tt.tt2.ModuloUI.Camara.CamaraActivity;
import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResultadoActivity extends AppCompatActivity {

    private TextView mTextoExtraidoTextview;

    private StringBuilder mTextoextraido = new StringBuilder();

    private ModuloTTS tts = new ModuloTTS(this);

    private GestorData mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.resultado_activity);
        configurarVistas();
        initDatos();
        recibirDatos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.detenerVoz();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.detenerVoz();
    }

    private void configurarVistas()
        {
            Button mRepetirAudioBoton = findViewById(R.id.resultado_repetir_audio_boton);
                mRepetirAudioBoton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playAudioTextoExtraido();
                    }
                });

            mTextoExtraidoTextview = findViewById(R.id.resultado_texto_obtenido_tv);
        }

    private void initDatos()
        {
            mData = new GestorData();
            mData.initData();
        }

    /**
     * Método que recibe los datos del OCR, siendo el listado de palabras filtradas y los coloca en su respectivo elemento.
     * */
    private void recibirDatos()
        {
            ArrayList<String> mListadoPalabras = getIntent().getStringArrayListExtra(CamaraActivity.RESULTADO_OCR_KEY);

            for(String palabra : mListadoPalabras)
                {
                    mTextoextraido.append(palabra);
                    mTextoextraido.append(" ");
                }
            playAudioTextoExtraido();
        }

    /**
     * Método que reproduce el texto extraído mediante el TTS.
     * */
    private void playAudioTextoExtraido()
        {
            ArrayList<String> rutas;
            rutas = mData.getRutasAsociadas(mTextoextraido.toString().toLowerCase());
            String ruta = obtieneMasFrecuente(rutas);
            mTextoExtraidoTextview.setText(mTextoextraido.toString() + "\n" + ruta);
            tts.escucharEnAudio(ruta);
        }

    /**
     * Método que retorna la ruta que tuvo más ocurrencia de paradas dentro de las paradas encontradas con el OCR.
     * @param list lista de rutas por cada parada donde las rutas se pueden repetir dependiendo de cuantas paradas de la misma
     *             fueron encontradas con el OCR.
     * */
    public String obtieneMasFrecuente(ArrayList<String> list)
    {
        Map<String, Integer> hm = new HashMap<>();
        int maxValue = -1;
        String indice = "";

        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        // displaying the occurrence of elements in the arraylist
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            if((val.getValue() > maxValue))
                {
                    maxValue =  val.getValue();
                    indice = val.getKey();
                }
        }
        return "La ruta es " + indice;
    }

}
