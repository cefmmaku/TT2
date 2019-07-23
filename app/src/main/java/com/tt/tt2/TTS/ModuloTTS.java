package com.tt.tt2.TTS;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class ModuloTTS {

    private TextToSpeech tts;

    private Context mContext;

    public ModuloTTS(Context context)
    {
        mContext = context;
    }


    /**
     * Método para comenzr a escuchar un texto.
     * @param texto el Texto que se desea escuchar
     **/
    public void escucharEnAudio(final String texto)
    {
        tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int ttsInitResult)
            {
                if (TextToSpeech.SUCCESS == ttsInitResult)
                {
                    tts.setLanguage(new Locale("es", "mx"));
                    //tts.speak(textToSay, TextToSpeech.QUEUE_ADD, null);
                    tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            }
        });
    }

    /**
     * Método que detiene al TTS de continuar hablando.
     * */
    public void detenerVoz()
    {
        if(tts != null && tts.isSpeaking()) {
            tts.stop();
            tts.shutdown();
        }
    }
}
