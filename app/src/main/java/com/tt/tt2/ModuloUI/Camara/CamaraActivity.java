package com.tt.tt2.ModuloUI.Camara;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.tt.tt2.R;
import com.tt.tt2.TTS.ModuloTTS;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.log.LoggersKt;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.selector.FlashSelectorsKt;
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
    //private TextureView mTextureView;

/*    //Check state orientation of output image
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static{
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }

    private String cameraId;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSessions;
    private CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;

    //Save to FILE
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;*/

   /* CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreview();

        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {
            cameraDevice.close();
            cameraDevice=null;
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_guia);
        configurarVistas();
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
            mInstruccionesCIV = findViewById(R.id.guia_escuchar_instrucciones_btn);
                mInstruccionesCIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playInstrucciones();
                   };
                });
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
            .flash(SelectorsKt.firstAvailable(      // (optional) similar to how it is done for focus mode, this time for flash
                    FlashSelectorsKt.autoRedEye(),
                    FlashSelectorsKt.autoFlash(),
                    FlashSelectorsKt.torch()
            ))
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

    private void tomarFoto()
        {
            mManejadorCamara.takePicture();
        }
}
