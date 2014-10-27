package com.jensdriller.libs.undobar;

import android.view.View;

abstract class ViewCompat {

    final View mView;

    ViewCompat(View view) {
        mView = view;
    }

    abstract void setAlpha(float alpha);

    abstract void animateIn(long duration);

    abstract void animateOut(long duration, AnimatorListener animatorListener);

    interface AnimatorListener {
        void onAnimationEnd();
    }

}
