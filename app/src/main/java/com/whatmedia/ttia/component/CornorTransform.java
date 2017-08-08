package com.whatmedia.ttia.component;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

/**
 * Created by neo_mac on 2017/8/6.
 */

public class CornorTransform implements com.squareup.picasso.Transformation {
    private final int radius;
    private final int margin;  // dp
    private final String key;

    // radius is corner radii in dp
    // margin is the CornorTransform in dp
    public CornorTransform(final int radius, final int margin) {
        this.radius = radius;
        this.margin = margin;
        this.key = "rounded(radius=" + radius + ", margin=" + margin + ")";
    }

    @Override
    public Bitmap transform(final Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return this.key;
    }
}
