package com.tt.tt2.Algoritmos;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class OpenCV {

    public static Bitmap umbralizar(Bitmap imagenOriginal, double umbral, int tipo)
        {
            Mat imagenOriginalMat = new Mat (imagenOriginal.getWidth(), imagenOriginal.getHeight(), CvType.CV_8UC1);
            Mat imagenBinaria = new Mat();
            Imgproc.cvtColor(imagenOriginalMat, imagenOriginalMat, Imgproc.COLOR_GRAY2RGB);
            Imgproc.threshold(imagenOriginalMat, imagenBinaria, umbral, 255, tipo);
            return matToBitmap(imagenBinaria);
        }

    public static Bitmap umbralizacionAdaptativa()
        {
            return null;
        }

    /**
     * Función que convierte una imagen en formato Bitmap a una nueva en formato Mat, el cual
     * es utilizado por OpenCV.
     * @param imagenOriginal la imagen de tipo Bitmap que es obtenida por la cámara o galería.
     * */
    public static Mat bitmapToMat(Bitmap imagenOriginal)
        {
            Mat mat = new Mat();
            Bitmap bitmap = imagenOriginal.copy(Bitmap.Config.ARGB_8888, true);
            Utils.bitmapToMat(bitmap, mat);
            return mat;
        }

    /**
     * Función que convierte un Mat procesado por OpenCV a un Bitmap el cual puede
     * ser tratado por el dispositivo.
     * @param imagenOriginal imagen en formato Mat procesada por OpenCV.
     * @return Bitmap imagen Bitmap que puede ser interpretada por el SO Android.
     * */
    private static Bitmap matToBitmap(Mat imagenOriginal)
        {
            Bitmap bmp;
            try {
                    Imgproc.cvtColor(imagenOriginal, imagenOriginal, Imgproc.COLOR_GRAY2RGBA, 4);
                    bmp = Bitmap.createBitmap(imagenOriginal.cols(), imagenOriginal.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(imagenOriginal, bmp);
                    return bmp;
                }
            catch (CvException e)
                {
                    return null;
                }
        }
}
