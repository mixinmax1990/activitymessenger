package com.example.apiconnect.activities.chatwindow;


import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ChatWindowAnimator {

    public ChatWindowAnimator() {
    }

    public static void slideChats(final FrameLayout slider, final float From, final float To){
        ValueAnimator va = ValueAnimator.ofFloat(From, To);
        int mDuration = 250; //in millis
        final ConstraintLayout.LayoutParams sliderLP = (ConstraintLayout.LayoutParams)  slider.getLayoutParams();
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {

                float animValue = (float) animation.getAnimatedValue();
                Log.d("Animation", ""+ animValue);

                sliderLP.width = (int) animValue;
                slider.setLayoutParams(sliderLP);

            }
        });
        //va.setRepeatCount(5);
        va.start();

    }




}
