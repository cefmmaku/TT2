package com.tt.tt2.Algoritmos;

import android.graphics.Bitmap;

public class Segmentacion {

    public static Bitmap cortarImagen(Bitmap fotoCompleta)
    {

        int centerx, centery, percentx, percenty;
        final int IMG_WIDTH = fotoCompleta.getWidth(), IMG_HEIGHT = fotoCompleta.getHeight();
        centerx = IMG_WIDTH/2;
        centery = IMG_HEIGHT/2;
        percentx = (int)(IMG_WIDTH * 0.6)/2;
        percenty = (int)(IMG_HEIGHT * 0.65)/2;
        return Bitmap.createBitmap(fotoCompleta, centerx - percentx, centery - percenty, percentx * 2, percenty * 2);
    }
}
