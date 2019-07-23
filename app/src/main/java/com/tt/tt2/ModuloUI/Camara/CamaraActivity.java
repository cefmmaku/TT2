package com.tt.tt2.ModuloUI.Camara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.tt.tt2.Algoritmos.Segmentacion;
import com.tt.tt2.ModuloUI.PostProcesamiento.ResultadoActivity;
import com.tt.tt2.OCR.ModuloOCR;
import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.log.LoggersKt;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.selector.FocusModeSelectorsKt;
import io.fotoapparat.selector.LensPositionSelectorsKt;
import io.fotoapparat.selector.ResolutionSelectorsKt;
import io.fotoapparat.selector.SelectorsKt;
import io.fotoapparat.view.CameraView;

public class CamaraActivity extends AppCompatActivity{

    private CircleImageView mInstruccionesCIV;

    private CameraView mCamaraView;

    private ModuloTTS tts;

    private static Fotoapparat mManejadorCamara;

    private Bitmap mFotografía;

    private ImageView mGuia;

    private ModuloOCR mTessOCR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_guia);
        playInstrucciones();
        configurarVistas();
        dibujarGuia();
        configuraCamara();
        iniciarPreview();
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
            mCamaraView = findViewById(R.id.camera_view);
                mCamaraView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tomarFoto();
                    }
                });

            mInstruccionesCIV = findViewById(R.id.guia_escuchar_instrucciones_btn);
                mInstruccionesCIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playInstrucciones();
                   }
                });

            mGuia = findViewById(R.id.camara_guia);
        }

    private void dibujarGuia(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.6);
        int width = (int) (displayMetrics.widthPixels * 0.4);
        ViewGroup.LayoutParams params = mGuia.getLayoutParams();
            params.height = height;
            params.width = width;
        mGuia.setLayoutParams(params);
            mGuia.setVisibility(View.VISIBLE);
    }

    private void configuraCamara()
        {
            mManejadorCamara =
            Fotoapparat.with(this)
            .into(mCamaraView)           // view which will draw the camera preview
            .previewScaleType(ScaleType.CenterCrop)  // we want the preview to fill the view
            .photoResolution(ResolutionSelectorsKt.highestResolution())   // we want to have the biggest photo possible
            .lensPosition(LensPositionSelectorsKt.back())       // we want back camera
            .focusMode(SelectorsKt.firstAvailable(  // (optional) use the first focus mode which is supported by device
                    FocusModeSelectorsKt. continuousFocusPicture(),
                    FocusModeSelectorsKt.autoFocus(),        // in case if continuous focus is not available on device, auto focus will be used
                    FocusModeSelectorsKt.fixed()             // if even auto focus is not available - fixed focus mode will be used
            ))
            /*.flash(SelectorsKt.firstAvailable(      // (optional) similar to how it is done for focus mode, this time for flash
                    FlashSelectorsKt.autoRedEye(),
                    FlashSelectorsKt.autoFlash(),
                    FlashSelectorsKt.torch()
            ))*/
            .logger(LoggersKt.loggers(            // (optional) we want to log camera events in 2 places at once
                    LoggersKt.logcat(),           // ... in logcat
                    LoggersKt.fileLogger(this)    // ... and to file
            ))
            .build();
        }


    private void iniciarPreview()
        {
            mManejadorCamara.start();
        }

    private void playInstrucciones()
        {
            tts = new ModuloTTS(this);
            tts.escucharEnAudio(getResources().getString(R.string.instrucciones));
        }

    private void tomarFoto() {

        PhotoResult resultado = mManejadorCamara.takePicture();

        resultado.toBitmap().whenDone(new WhenDoneListener<BitmapPhoto>() {
            @Override
            public void whenDone(@Nullable BitmapPhoto bitmapPhoto) {
                if (bitmapPhoto == null) {
                     tts.escucharEnAudio(getResources().getString(R.string.fallo_tomar_foto));
                    return;
                }
                procesarFoto(Segmentacion.cortarImagen(bitmapPhoto.bitmap));
            }
        });
    }

    private void procesarFoto(Bitmap segmentada)
        {
            mTessOCR = new ModuloOCR(this, "spa");
            doOCR(segmentada);
        }

    private void doOCR (final Bitmap bitmap)
    {
        new Thread(new Runnable()
        {
            public void run() {
                final String srcText = mTessOCR.extraerTexto(bitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (srcText != null && !srcText.equals(""))
                        {
                            //srcText contiene el texto reconocido
                            Intent irAResultado;
                           tts.escucharEnAudio(srcText);
                        }
                        mTessOCR.onDestroy();
                    }
                });
            }
        }).start();
    }
}
