package com.jensdriller.libs.undobar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewPropertyAnimator;

@SuppressLint("NewApi")
class ViewCompatImpl extends ViewCompat {

    private final ViewPropertyAnimator mViewPropertyAnimator;

    ViewCompatImpl(View view) {
        super(view);
        mViewPropertyAnimator = view.animate();
    }

    @Override
    void setAlpha(float alpha) {
        mView.setAlpha(alpha);
    }

    @Override
    void animateIn(long duration) {
        mViewPropertyAnimator.cancel();
        mViewPropertyAnimator.alpha(1)//
                .setDuration(duration)//
                .setListener(null);
    }

    @Override
    void animateOut(long duration, final AnimatorListener animatorListener) {
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
