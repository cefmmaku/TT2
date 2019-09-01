package com.tt.tt2.ModuloUI.Camara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tt.tt2.Algoritmos.FiltradoTexto;
import com.tt.tt2.Algoritmos.OpenCV;
import com.tt.tt2.Algoritmos.Segmentacion;
import com.tt.tt2.Algoritmos.Umbralizacion;
import com.tt.tt2.ModuloUI.PostProcesamiento.ResultadoActivity;
import com.tt.tt2.OCR.ModuloOCR;
import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

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

    private CameraView mCamaraView;

    private ModuloTTS tts;

    private static Fotoapparat mManejadorCamara;

    private ImageView mGuia;

    private ModuloOCR mTessOCR;

    private ProgressBar mLoader;

    public static final String RESULTADO_OCR_KEY = "ocrtexto";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    //Log.i("OpenCV", "OpenCV loaded successfully");
                    //imageMat=new Mat();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_guia);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playInstrucciones();
        configurarVistas();
        dibujarGuia();
        configuraCamara();
        iniciarPreview();
        cargarOpenCV();
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

    private void cargarOpenCV()
        {
            if (!OpenCVLoader.initDebug()) {
                Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
                OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
            } else {
                Log.d("OpenCV", "OpenCV library found inside package. Using it!");
                mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            }
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

            CircleImageView mInstruccionesCIV = findViewById(R.id.guia_escuchar_instrucciones_btn);
                mInstruccionesCIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playInstrucciones();
                   }
                });

            mLoader = findViewById(R.id.loader_camara_activity);

            mGuia = findViewById(R.id.camara_guia);
        }

    /**
     * Método que dibuja la guia sobre la pantalla respetando la proporción establecida de
     * 60% de alto y 40% de ancho.
     * */
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

    /**
     * Método que coloca y configura la cámara agregando los parámetros necesarios para su funcionamiento.
     * */
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


    /**
     * Método que inicia el preview en la pantalla.
     * */
    private void iniciarPreview()
        {
            mManejadorCamara.start();
        }

    /**
     * Método que hace al TTS rteproducir las instrucciones.
     * */
    private void playInstrucciones()
        {
            tts = new ModuloTTS(this);
            tts.escucharEnAudio(getResources().getString(R.string.instrucciones));
        }

    /**
     * Método que se ejecuta una vez que se presiona la pantalla, activa el método de tomar fotografía
     * y obtiene el resultado de la misma.
     * */
    private void tomarFoto() {

        final PhotoResult resultado = mManejadorCamara.autoFocus().takePicture();

        resultado.toBitmap().whenDone(new WhenDoneListener<BitmapPhoto>() {
            @Override
            public void whenDone(@Nullable BitmapPhoto bitmapPhoto) {
                if (bitmapPhoto == null) {
                     tts.escucharEnAudio(getResources().getString(R.string.fallo_tomar_foto));
                    return;
                }
                procesarFoto(bitmapPhoto.bitmap);
            }
        });
    }

    /**
     * Método en el que se trabajará la imagen antes de ser enviada al OCR, aquí se aplicarán
     * todos los métodos y algoritmos necesarios para tratar la imagen, el pre procesamiento.
     * @param foto la imagen tomada por la camara en formato de Bitmap.
     * */
    private void procesarFoto(Bitmap foto)
        {
            Bitmap imagenSegmentada, imagenProcesada;
            imagenSegmentada = Segmentacion.cortarImagen(foto);
            Log.d("Debuggeando",  "La imagen cortada mide: " + imagenSegmentada.getWidth() + "x" + imagenSegmentada.getHeight());
            imagenProcesada = OpenCV.umbralizar(imagenSegmentada, 150.00, Umbralizacion.BINARIO_INVERTIDO.getTipo());
            //imagenProcesada = OpenCV.colorAGrises(imagenSegmentada);
            //imagenProcesada = OpenCV.erosionar(imagenProcesada);
            //imagenProcesada = OpenCV.dilatar(imagenProcesada);
            mGuia.setImageBitmap(imagenProcesada);
            //mGuia.setImageBitmap(OpenCV.umbralizacionAdaptativa(imagenCortada));
            procesarConOCR(imagenProcesada);
        }

    /**
     * Método que inicia el proceso de extracción del texto con el OCR mediante la inicialización del mismo y el paso
     * de la fotografía ya segmentada y procesada.
     * @param procesada la fotografía a la cual ya se le aplicaron todos los métodos de procesamiento y segmentación.
     * */
    private void procesarConOCR(Bitmap procesada)
        {
            mTessOCR = new ModuloOCR(this, "spa");
            doOCR(procesada);
        }

    /**
     * Método que manda a llamar al OCR en un hilo principal para comenzar la extracción del texto en un hilo nuevo.
     * @param bitmap la imagen ya segmentada de donde tomará el texto.
     * */
    private void doOCR (final Bitmap bitmap)
    {
        mLoader.setVisibility(View.VISIBLE);
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
                            Intent irAResultado = new Intent(getApplicationContext(), ResultadoActivity.class);
                            irAResultado.putExtra(RESULTADO_OCR_KEY, FiltradoTexto.filtroPalabras(srcText));
                            startActivity(irAResultado);
                            mLoader.setVisibility(View.GONE);
                        }
                        mTessOCR.onDestroy();
                    }
                });
            }
        }).start();
    }
}
