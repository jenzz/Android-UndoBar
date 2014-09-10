package com.jensdriller.libs.undobar;

import android.view.View;

public abstract class ViewCompat {

    protected final View mView;

    public ViewCompat(View view) {
        mView = view;
    }

    protected abstract void setAlpha(float alpha);

    protected abstract void animateIn(long duration);

    protected abstract void animateOut(long duration, AnimatorListener animatorListener);

    public interface AnimatorListener {
        void onAnimationEnd();
    }

}
