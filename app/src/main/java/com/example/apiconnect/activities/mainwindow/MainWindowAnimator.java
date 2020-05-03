package com.example.apiconnect.activities.mainwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiconnect.R;

import java.lang.invoke.ConstantCallSite;


public class MainWindowAnimator {

    public static Context context;
    private static ConstraintLayout eye;
    public static ImageView moreICN;
    static Handler handler;



    public MainWindowAnimator(ConstraintLayout eye, Context context, ImageView more){

        this.eye = eye;
        this.context = context;
        handler = new Handler();
        moreICN = more;

    }


    public static void flipButtonFire(final FloatingActionButton fire){

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(fire, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(fire, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());

        oa1.setDuration(150);
        oa2.setDuration(150);

        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                fire.hide();
            }
        });
        oa1.start();


    }
    public static void flipButtonAdd(final FloatingActionButton fire){

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(fire, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(fire, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());

        oa1.setDuration(150);
        oa2.setDuration(150);

        fire.show();

        oa2.start();


    }

    public static void flipButtonContact(final FloatingActionButton contact){

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(contact, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(contact, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());

        oa1.setDuration(100);
        oa2.setDuration(100);

        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                contact.hide();

            }
        });
        oa1.start();


    }

    public static void flipButtonLocation(final FloatingActionButton contact){

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(contact, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(contact, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());

        oa1.setDuration(100);
        oa2.setDuration(100);
        //contact.show();

        oa2.start();


    }

    public static int visibleLabel = 0;


    public static void slideInLabel(final FrameLayout label, final FrameLayout cameraline, final ConstraintLayout tinyMenu, final ImageView cameraIcon, final TextView text, int end, int visible){


        if(!menuIsOpend){
            toggleTinyMenu(tinyMenu, cameraIcon);
            rotateMoreIcon(moreICN);

        }
        final int newend = end;
        cameraline.setVisibility(View.INVISIBLE);

        visibleLabel = visible;

        final ConstraintLayout.LayoutParams LP = (ConstraintLayout.LayoutParams) label.getLayoutParams();
        final ConstraintLayout.LayoutParams LPCamera = (ConstraintLayout.LayoutParams) cameraline.getLayoutParams();
        final ConstraintLayout.LayoutParams LPText = (ConstraintLayout.LayoutParams) text.getLayoutParams();

        ValueAnimator va = ValueAnimator.ofFloat(10, newend);
        int duration = 150;


        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

                LP.topMargin = (int) animValue;
                LPCamera.topMargin = (int) animValue;
                LPText.bottomMargin = newend - (int) animValue;

                text.setLayoutParams(LPText);
                cameraline.setLayoutParams(LPCamera);
                label.setLayoutParams(LP);
            }
        });
        handler.removeCallbacksAndMessages(null);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cameraline.setVisibility(View.VISIBLE);
                toggleTinyMenu(tinyMenu, cameraIcon);
                rotateMoreIcon(moreICN);
                slideOutLabel(label, cameraline, text, newend);

            }
        }, 2000);

        va.start();

    }

    private static boolean menuIsOpend = true;
    private static int rotationMoreState = 0;

    public static void rotateMoreIcon(final ImageView more){


        final ConstraintLayout.LayoutParams LP = (ConstraintLayout.LayoutParams) more.getLayoutParams();

        ValueAnimator va;
        if(rotationMoreState == 0){
            va = ValueAnimator.ofFloat(0, 90);
            rotationMoreState = 90;
        }

        else{
            va = ValueAnimator.ofFloat(90, 0);
            rotationMoreState = 0;
        }

        int duration = 150;


        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

                more.setRotation(animValue);


            }
        });

        va.start();
    }


    public static void slideOutLabel(final FrameLayout label, final FrameLayout cameraline, final TextView text, int end){

        final int newend = end;

        final ConstraintLayout.LayoutParams LP = (ConstraintLayout.LayoutParams) label.getLayoutParams();
        final ConstraintLayout.LayoutParams LPCamera = (ConstraintLayout.LayoutParams) cameraline.getLayoutParams();
        final ConstraintLayout.LayoutParams LPText = (ConstraintLayout.LayoutParams) text.getLayoutParams();

        ValueAnimator va = ValueAnimator.ofFloat(newend, 10);
        int duration = 150;


        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

                LP.topMargin = (int) animValue;
                LPCamera.topMargin = (int) animValue;
                LPText.bottomMargin = newend - (int) animValue;

                text.setLayoutParams(LPText);
                cameraline.setLayoutParams(LPCamera);
                label.setLayoutParams(LP);

            }
        });

        va.start();

    }

    public static void toggleTinyMenu(final ConstraintLayout tinyMenu, final ImageView cameraIcon){


        final ConstraintLayout.LayoutParams LP = (ConstraintLayout.LayoutParams) tinyMenu.getLayoutParams();
        ValueAnimator va;

        if(menuIsOpend){
             va = ValueAnimator.ofFloat(convertDpToPixel(35), convertDpToPixel(20));
            va.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    cameraIcon.setVisibility(View.INVISIBLE);

                }
            });

             menuIsOpend = false;
        }
        else {
             va = ValueAnimator.ofFloat(convertDpToPixel(20), convertDpToPixel(35));
            cameraIcon.setVisibility(View.VISIBLE);
            menuIsOpend = true;
        }

        int duration = 150;


        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

                LP.height = (int) animValue;
                tinyMenu.setLayoutParams(LP);
            }
        });



        va.start();

    }

    public static void slideOutBottomTab(final View tab, int end, int start){



        final ConstraintLayout.LayoutParams LP = (ConstraintLayout.LayoutParams) tab.getLayoutParams();
        ValueAnimator va = ValueAnimator.ofFloat(start, end);
        int duration = 200;


        va.setDuration(duration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = Math.round((float) animation.getAnimatedValue());

                LP.height = (int) animValue;
                tab.setLayoutParams(LP);

            }
        });

        va.start();

    }

    public static float convertDpToPixel(float dp){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


}
