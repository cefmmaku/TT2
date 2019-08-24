package com.tt.tt2.ModuloUI.PostProcesamiento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tt.tt2.ModuloUI.Camara.CamaraActivity;
import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import java.util.ArrayList;
import java.util.Objects;

public class ResultadoActivity extends AppCompatActivity {

    private TextView mTextoExtraidoTextview;

    private ArrayList<String> mListadoPalabras;

    private StringBuilder mTextoextraido = new StringBuilder();

    private ModuloTTS tts = new ModuloTTS(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.resultado_activity);
        configurarVistas();
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


    /**
     * Método que recibe los datos del OCR, siendo el listado de palabras filtradas y los coloca en su respectivo elemento.
     * */
    private void recibirDatos()
        {
            mListadoPalabras = getIntent().getStringArrayListExtra(CamaraActivity.RESULTADO_OCR_KEY);

            for(String palabra : mListadoPalabras)
                {
                    mTextoextraido.append(palabra);
                    mTextoextraido.append(" ");
                }
            mTextoExtraidoTextview.setText(mTextoextraido);
            playAudioTextoExtraido();
        }

    /**
     * Método que reproduce el texto extraído mediante el TTS.
     * */
    private void playAudioTextoExtraido()
        {
            tts.escucharEnAudio(mTextoextraido.toString());
        }


}
