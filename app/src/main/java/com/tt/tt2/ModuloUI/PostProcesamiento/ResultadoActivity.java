package com.tt.tt2.ModuloUI.PostProcesamiento;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import java.util.Objects;

public class ResultadoActivity extends AppCompatActivity {

    private Button mRepetirAudioBoton;

    private ImageView mImagenSegmentadaImageview;

    private TextView mTextoExtraidoTextview;

    private String mTextoextraido;

    private ProgressBar mLoader;

    private ModuloTTS tts = new ModuloTTS(this);

    private Bitmap mImagenSegmentada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.resultado_activity);
        configurarVistas();
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
            mRepetirAudioBoton = findViewById(R.id.resultado_repetir_audio_boton);
                mRepetirAudioBoton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playAudioTextoExtraido();
                    }
                });

            mTextoExtraidoTextview = findViewById(R.id.resultado_texto_obtenido_tv);

            mLoader = findViewById(R.id.resultado_loader);
        }


    private void playAudioTextoExtraido()
        {
            tts.escucharEnAudio(mTextoextraido);
        }


}
