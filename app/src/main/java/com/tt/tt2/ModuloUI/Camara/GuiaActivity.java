package com.tt.tt2.ModuloUI.Camara;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import de.hdodenhof.circleimageview.CircleImageView;

public class GuiaActivity extends AppCompatActivity {

    private ImageView mGuiaFoto;

    private CircleImageView mRepetirInstruccionesButton;

    private ModuloTTS tts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia);
        bindViews();
        playInstrucciones();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.detenerVoz();
    }

    private void bindViews()
    {
        mGuiaFoto = findViewById(R.id.camara_guia);
        mRepetirInstruccionesButton = findViewById(R.id.guia_escuchar_instrucciones_btn);
            mRepetirInstruccionesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playInstrucciones();
                }
            });
    }

    /**
     * Método para reproducir un audio de bienvenida al usuario.
     * */
    private void playInstrucciones()
    {
        tts = new ModuloTTS(this);
        tts.escucharEnAudio(getResources().getString(R.string.instrucciones));
    }
}
