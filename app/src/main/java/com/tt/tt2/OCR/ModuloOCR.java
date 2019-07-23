package com.tt.tt2.OCR;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

        String rutaArchivo = context.getFilesDir() + "/tesseract/";
        String extension = ".traineddata";
        copiarArchivoIdioma(context, language, rutaArchivo + "tessdata/", extension);
        File dir = new File(rutaArchivo + "tessdata/");
        if (!dir.exists())
            dir.mkdirs();
        mTess = new TessBaseAPI();
        mTess.init(rutaArchivo, language);
        mTess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, whiteList);
        mTess.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, blackList);
        mTess.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
    }

    /**
     * Método que copia el archivo de entrenamiento de idioma en la carpeta de datos del dispositivo.
     * @param contexto Contexto de la aplicación para el uso de métodos.
     * @param idioma idioma del archivo de configuración en formato ISO 639-2/B.
     * @param rutaArchivo la ruta dentro del dispositivo donde se va a copiar el archivo de entrenamiento.
     * @param extension la extensión del archivo de entrenamiento.
     * */
    private void copiarArchivoIdioma(Context contexto, String idioma, String rutaArchivo, String extension) {

        AssetManager assetManager = contexto.getAssets();

            InputStream in = null;
            OutputStream out = null;
            try
                {
                    in = assetManager.open(idioma + extension);
                    File directorio = new File(rutaArchivo);
                    directorio.mkdirs();
                    File outFile = new File(rutaArchivo, idioma + extension);
                    outFile.createNewFile();
                    out = new FileOutputStream(outFile);
                    copiarArchivo(in, out);
                }
            catch(IOException e)
                {
                    Log.e("tag", "Failed to copy asset file: " + idioma, e);
                }
            finally
                {
                    if (in != null)
                        {
                            try
                                {
                                    in.close();
                                }
                            catch (IOException e)
                                {
                                    // No hago nada 6u6
                                }
                        }
                    if (out != null)
                        {
                            try
                                {
                                    out.close();
                                }
                            catch (IOException e)
                                {
                                    // Acá tampoco @3@
                                }
                    }

        }
    }

    /**
     * Método que copia el contenido de un archivo entrante A en un nuevo Archivo B.
     * @param in el InputStream del archivo que va a ser copiado.
     * @param out el OutputStream donde se va a copiar el contenido del archivo en el InputStream.
     * */
    private void copiarArchivo(InputStream in, OutputStream out) throws IOException
        {
            byte[] buffer = new byte[1024];
            int read;
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
            }
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
