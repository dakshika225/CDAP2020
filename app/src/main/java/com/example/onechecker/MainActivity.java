package com.example.onechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = (ImageView) findViewById(R.id.homeId);
        if (iv != null) {
            iv.setOnTouchListener(this);

        }
    }public int getHotspotColor(int hotspotId, int x, int y) {
        int returnBVal = 0;
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", "Hot spot image not found");
            return returnBVal;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                return returnBVal;
            } else {
                img.setDrawingCacheEnabled(false);
                try {
                    returnBVal = hotspots.getPixel(x, y);
                    Log.d("returnBVal", "returnBVal is " + returnBVal + "");

                } catch (Exception e) {
                    Log.e("Inside Catch", "Clicked on Two Colors.");
                }
                return returnBVal;
            }

        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        boolean handledHere = false;

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;			// resource id of the next image to display

        // If we cannot find the imageView, return.
        ImageView imageView = (ImageView) v.findViewById (R.id.homeId);
        if (imageView == null) return false;

        Integer tagNum = (Integer) imageView.getTag ();
        int currentResource = (tagNum == null) ? R.drawable.home : tagNum.intValue ();

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        switch (action) {
            case MotionEvent.ACTION_DOWN :
                if (currentResource == R.drawable.home) {
                    nextImage = R.drawable.home;
                    handledHere = true;

                } else handledHere = true;
                break;

            case MotionEvent.ACTION_UP :

                int touchColor = getHotspotColor (R.id.home_mask_id, evX, evY);

                ColorTool ct = new ColorTool ();
                int tolerance = 25;
                nextImage = R.drawable.home;
                if (ct.closeMatch (Color.parseColor("#9acb31"), touchColor, tolerance)){
                    Intent intent = new Intent(getApplicationContext(), CalorieCounter.class);
                    startActivity(intent);
                    finish();
                }

                else if (ct.closeMatch (Color.parseColor("#cb3164"), touchColor, tolerance)){
                    Intent intent = new Intent(getApplicationContext(), DietPlan.class);
                    startActivity(intent);
                    finish();
                }

                else if (ct.closeMatch (Color.parseColor("#1029aa"), touchColor, tolerance)){
                    Intent intent = new Intent(getApplicationContext(), ActivityVideo.class);
                    startActivity(intent);
                    finish();
                }
                else if (ct.closeMatch (Color.parseColor("#8f7226"), touchColor, tolerance)){
                    Intent intent = new Intent(getApplicationContext(), DoctorChatBot.class);
                    startActivity(intent);
                    finish();
                }


                if (currentResource == nextImage) {
                    nextImage = R.drawable.home;
                }
                handledHere = true;
                break;

            default:
                handledHere = false;
        } // end switch

        if (handledHere) {

            if (nextImage > 0) {
                imageView.setImageResource (nextImage);
                imageView.setTag (nextImage);
            }
        }
        return handledHere;
    }


}
