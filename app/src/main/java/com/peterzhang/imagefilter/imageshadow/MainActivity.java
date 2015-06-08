package com.peterzhang.imagefilter.imageshadow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView)this.findViewById(R.id.imageView);
        Bitmap srcBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        mImageView.setImageBitmap(getShadowBitmap(srcBitmap));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Bitmap getShadowBitmap(Bitmap srcBitmap){
        Paint shadowPaint = new Paint();
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(6,BlurMaskFilter.Blur.NORMAL);
        shadowPaint.setMaskFilter(blurMaskFilter);
        int[] offsetXY = new int[2];
        Bitmap shadowBitmap = srcBitmap.extractAlpha(shadowPaint,offsetXY);
        Bitmap canvasBgBitmap = Bitmap.createBitmap(
                shadowBitmap.getWidth(), shadowBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(canvasBgBitmap);
        canvas.drawBitmap(shadowBitmap, 0, 0, shadowPaint);
        canvas.drawBitmap(srcBitmap, -offsetXY[0], -offsetXY[1], null);
        shadowBitmap.recycle();
        return canvasBgBitmap;
    }
}
