package com.tt.tt2.OCR;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public class ModuloOCR {

    /**
     * Instancia del OCR.
     * */
    private TessBaseAPI mTess;

    /**
     * Lista de caracteres permitidos para ser utilziados por el OCR durante el reconocimiento del texto.
     * */
    private static final String whiteList = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";

    /**
     * Lista de caracteres no permitidos como salida tras la extracción del texto mediante el OCR.
     * */
    private static final String blackList = "~;'°-_.!?¿'¡!#$%&//()={}><~`^ﬂ,[]0123456789";

    /**
     * Constructor para inicializar el módulo OCR.
     * @param context contexto sobre el que va a trabajar.
     * @param language idioma en el que buscará el texto.
     * */
    public ModuloOCR(Context context, String language) {

        mTess = new TessBaseAPI();
        String DATA_PATH = context.getFilesDir() + "/tesseract/";
        Log.d("DataPath", "datapath " + DATA_PATH);
        File dataIdiomaLocal = new File(DATA_PATH + "tessdata/");
        if (dataIdiomaLocal.exists()) {
            try
                {
                    AssetManager assetManager = context.getAssets();
                    InputStream in = assetManager.open(language + ".traineddata");

                    //GZIPInputStream gin = new GZIPInputStream(in);

                    File outFile = new File(DATA_PATH + "tessdata/", language + ".traineddata");
                    outFile.getParentFile().mkdir();
                    OutputStream out = new FileOutputStream(outFile);
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    //while ((lenf = gin.read(buff)) > 0) {
                    while ((len = in.read(buf)) > 0)
                        {
                            out.write(buf, 0, len);
                        }
                    in.close();
                    //gin.close();
                    out.close();
                }
            catch (Exception e)
                {
                    e.printStackTrace();
                    Log.d("DataPath", "Error " + e.toString());
                }
        }
        else
        {
            Log.d("DataPath", "Existe");
        }

        //mTess.init(DATA_PATH, language);

        mTess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, whiteList);
        mTess.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, blackList);
        mTess.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
    }

    /**
     * Función que extra el texto de una imagen.
     * @param fotografia el Bitmap de la fotografía de la que se desea extraer el texto
     * @return String el texto extraído de la fotografía medianteel uso del OCR y en formato UTF - 8.
     * */
    public String extraerTexto(Bitmap fotografia) {
        mTess.setImage(fotografia);
        return mTess.getUTF8Text();
    }

    /**
     * Método para detener y destruir la instancia del OCR verificando antes que ésta no sea nula.
     * */
    public void onDestroy() {
        if (mTess != null) mTess.end();
    }
}
