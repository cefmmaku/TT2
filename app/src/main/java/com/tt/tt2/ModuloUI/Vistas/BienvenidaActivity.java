package com.tt.tt2.ModuloUI.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.tt.tt2.ModuloUI.Camara.CamaraActivity;
import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import java.util.Objects;

public class BienvenidaActivity extends AppCompatActivity {

    private ConstraintLayout mRootView;

    private ModuloTTS tts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Escondemos la barra superior y su titulo para mostrar el contenido en pantalla completa.
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_inicio);
        playBienvenidaAudio();
        bindViews();
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

    /**
     * Método para cargar los elementos visuales
     * */
    private void bindViews()
    {
        mRootView = findViewById(R.id.bienvenida_activity_root_view);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.detenerVoz();
                onSalirBienvenida();
            }
        });
    }

    /**
     * Método para reproducir un audio de bienvenida al usuario.
     * */
    private void playBienvenidaAudio()
    {
        tts = new ModuloTTS(this);
        tts.escucharEnAudio(getResources().getString(R.string.bienvenido));
    }

    /**
     * Método que se ejecuta cuando se sale de la pantalla de bienvenida, haciendo click sobre la pantalla.
     * */
    private void onSalirBienvenida(){
        Intent irA = new Intent(this, CamaraActivity.class);
        startActivity(irA);
    }
}
