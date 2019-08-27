package com.tt.tt2.Algoritmos;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.ADAPTIVE_THRESH_MEAN_C;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;

public class OpenCV {

    public static Bitmap umbralizar(Bitmap imagenOriginal, double umbral, int tipo)
        {
            Mat imagenOriginalMat = new Mat();
            Utils.bitmapToMat(imagenOriginal, imagenOriginalMat);
            Imgproc.cvtColor(imagenOriginalMat, imagenOriginalMat, Imgproc.COLOR_BGR2GRAY, 4);
            Imgproc.threshold(imagenOriginalMat, imagenOriginalMat, umbral, 255, tipo);
            Utils.matToBitmap(imagenOriginalMat, imagenOriginal);
            return imagenOriginal;
        }

    public static Bitmap umbralizacionAdaptativa(Bitmap imagenOriginal)
    {
        Mat imagenOriginalMat = new Mat();
        Utils.bitmapToMat(imagenOriginal, imagenOriginalMat);
        Imgproc.cvtColor(imagenOriginalMat, imagenOriginalMat, Imgproc.COLOR_BGR2GRAY, 4);
        Imgproc.adaptiveThreshold(imagenOriginalMat, imagenOriginalMat, 255, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 15, 40);
        Utils.matToBitmap(imagenOriginalMat, imagenOriginal);
        return imagenOriginal;
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
                    Imgproc.cvtColor(imagenOriginal, imagenOriginal, Imgproc.COLOR_GRAY2RGBA, 3);
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
