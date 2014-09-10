package com.jensdriller.libs.undobar;


import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class ViewCompatImpl extends ViewCompat {

    private final ViewPropertyAnimator mViewPropertyAnimator;

    public ViewCompatImpl(View view) {
        super(view);
        mViewPropertyAnimator = ViewPropertyAnimator.animate(view);
    }

    @Override
    protected void setAlpha(float alpha) {
        ViewHelper.setAlpha(mView, alpha);
    }

    @Override
    protected void animateIn(long duration) {
        mViewPropertyAnimator.cancel();
        mViewPropertyAnimator.alpha(1)//
                .setDuration(duration)//
                .setListener(null);
    }

    @Override
    protected void animateOut(long duration, final AnimatorListener animatorListener) {
        mViewPropertyAnimator.cancel();
        mViewPropertyAnimator.alpha(0)//
                .setDuration(duration)//
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatorListener.onAnimationEnd();
                    }
                });
    }


}
