package com.jensdriller.libs.undobar;

import android.app.Activity;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

@SuppressWarnings({"UnusedDeclaration", "WeakerAccess"})
public class UndoBar {

    /**
     * Listener for actions of the undo bar.
     */
    public static interface Listener {
        /**
         * Will be fired when the undo bar disappears without being actioned.
         */
        void onHide();

        /**
         * Will be fired when the undo button is pressed.
         */
        void onUndo(Parcelable token);
    }

    /**
     * Default duration in milliseconds the undo bar will be displayed.
     */
    public static final int DEFAULT_DURATION = 5000;
    /**
     * Default duration in milliseconds of the undo bar show and hide animation.
     */
    public static final int DEFAULT_ANIMATION_DURATION = 300;

    protected final Activity mActivity;
    protected final UndoBarView mView;
    protected final ViewCompat mViewCompat;
    protected final Handler mHandler = new Handler();

    private final Runnable mHideRunnable = new Runnable() {

        @Override
        public void run() {
            onHide();
        }
    };

    @SuppressWarnings("FieldCanBeLocal")
    private final OnClickListener mOnUndoClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            onUndo();
        }
    };

    protected Listener mUndoListener;
    protected Parcelable mUndoToken;
    protected CharSequence mUndoMessage;
    protected int mDuration = DEFAULT_DURATION;
    protected int mAnimationDuration = DEFAULT_ANIMATION_DURATION;

    public UndoBar(Activity activity) {
        mActivity = activity;
        mView = getView(activity);
        mView.setOnUndoClickListener(mOnUndoClickListener);
        mViewCompat = new ViewCompatImpl(mView);

        hide(false);
    }

    /**
     * Sets the message to be displayed on the left of the undo bar.
     */
    public void setMessage(CharSequence message) {
        mUndoMessage = message;
    }

    /**
     * Sets the message to be displayed on the left of the undo bar.
     */
    public void setMessage(int messageResId) {
        mUndoMessage = mActivity.getString(messageResId);
    }

    /**
     * Sets the {@link Listener UndoBar.Listener}.
     */
    public void setListener(Listener undoListener) {
        mUndoListener = undoListener;
    }

    /**
     * Sets a {@link Parcelable} token to the undo bar which will be returned in
     * the {@link Listener UndoBar.Listener}.
     */
    public void setUndoToken(Parcelable undoToken) {
        mUndoToken = undoToken;
    }

    /**
     * Sets the duration the undo bar will be shown.<br>
     * Default is {@link #DEFAULT_DURATION}.
     *
     * @param duration in milliseconds
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    /**
     * Sets the duration of the animation for showing and hiding the undo bar.<br>
     * Default is {@link #DEFAULT_ANIMATION_DURATION}.
     *
     * @param animationDuration in milliseconds
     */
    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    /**
     * Calls {@link #show(boolean)} with {@code shouldAnimate = true}.
     */
    public void show() {
        show(true);
    }

    /**
     * Shows the {@link UndoBar}.
     *
     * @param shouldAnimate whether the {@link UndoBar} should animate in
     */
    public void show(boolean shouldAnimate) {
        mView.setMessage(mUndoMessage);

        mHandler.removeCallbacks(mHideRunnable);
        mHandler.postDelayed(mHideRunnable, mDuration);

        mView.setVisibility(View.VISIBLE);
        if (shouldAnimate) {
            animateIn();
        } else {
            mViewCompat.setAlpha(1);
        }
    }

    /**
     * Calls {@link #hide(boolean)} with {@code shouldAnimate = true}.
     */
    public void hide() {
        hide(true);
    }

    /**
     * Hides the {@link UndoBar}.
     *
     * @param shouldAnimate whether the {@link UndoBar} should animate out
     */
    public void hide(boolean shouldAnimate) {
        mHandler.removeCallbacks(mHideRunnable);

        if (shouldAnimate) {
            animateOut();
        } else {
            mViewCompat.setAlpha(0);
            mView.setVisibility(View.GONE);
            mUndoMessage = null;
            mUndoToken = null;
        }
    }

    /**
     * Checks if the undo bar is currently visible.
     *
     * @return {@code true} if visible, {@code false} otherwise
     */
    public boolean isVisible() {
        return mView.getVisibility() == View.VISIBLE;
    }

    /**
     * Performs the actual show animation.
     */
    protected void animateIn() {
        mViewCompat.animateIn(mAnimationDuration);
    }

    /**
     * Performs the actual hide animation.
     */
    protected void animateOut() {
        mViewCompat.animateOut(mAnimationDuration, new ViewCompat.AnimatorListener() {
            @Override
            public void onAnimationEnd() {
                mView.setVisibility(View.GONE);
                mUndoMessage = null;
                mUndoToken = null;
            }
        });
    }

    /**
     * Called when the undo bar disappears without being actioned.<br>
     * Hides the undo bar and notifies potential listener.
     */
    protected void onHide() {
        hide(true);
        safelyNotifyOnHide();
    }

    /**
     * Called when the undo button is pressed.<br>
     * Hides the undo bar and notifies potential listener.
     */
    protected void onUndo() {
        hide(true);
        safelyNotifyOnUndo();
    }

    /**
     * Notifies listener if available.
     */
    protected void safelyNotifyOnHide() {
        if (mUndoListener != null) {
            mUndoListener.onHide();
        }
    }

    /**
     * Notifies listener if available.
     */
    protected void safelyNotifyOnUndo() {
        if (mUndoListener != null) {
            mUndoListener.onUndo(mUndoToken);
        }
    }

    /**
     * Checks if there is already an {@link UndoBarView} instance added to the
     * given {@link Activity}.<br>
     * If {@code true}, returns that instance.<br>
     * If {@code false}, inflates a new {@link UndoBarView} and returns it.
     */
    protected UndoBarView getView(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
        UndoBarView undoBarView = (UndoBarView) rootView.findViewById(R.id.undoBar);
        if (undoBarView == null) {
            undoBarView = (UndoBarView) LayoutInflater.from(activity).inflate(R.layout.undo_bar, rootView, false);
            rootView.addView(undoBarView);
        }
        return undoBarView;
    }

    public static class Builder {

        private final Activity mActivity;
        private CharSequence mUndoMessage;
        private Listener mUndoListener;
        private Parcelable mUndoToken;
        private int mDuration = DEFAULT_DURATION;
        private int mAnimationDuration = DEFAULT_ANIMATION_DURATION;

        /**
         * Constructor using the {@link Activity} in which the undo bar will be
         * displayed
         */
        public Builder(Activity activity) {
            mActivity = activity;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setMessage(int messageResId) {
            mUndoMessage = mActivity.getString(messageResId);
            return this;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setMessage(CharSequence message) {
            mUndoMessage = message;
            return this;
        }

        /**
         * Sets the {@link Listener UndoBar.Listener}.
         */
        public Builder setListener(Listener undoListener) {
            mUndoListener = undoListener;
            return this;
        }

        /**
         * Sets a {@link Parcelable} token to the undo bar which will be
         * returned in the {@link Listener UndoBar.Listener}.
         */
        public Builder setUndoToken(Parcelable undoToken) {
            mUndoToken = undoToken;
            return this;
        }

        /**
         * Sets the duration the undo bar will be shown.<br>
         * Default is {@link #DEFAULT_DURATION}.
         *
         * @param duration in milliseconds
         */
        public Builder setDuration(int duration) {
            mDuration = duration;
            return this;
        }

        /**
         * Sets the duration of the animation for showing and hiding the undo
         * bar.<br>
         * Default is {@link #DEFAULT_ANIMATION_DURATION}.
         *
         * @param animationDuration in milliseconds
         */
        public Builder setAnimationDuration(int animationDuration) {
            mAnimationDuration = animationDuration;
            return this;
        }

        /**
         * Creates an {@link UndoBar} instance with this Builder's
         * configuration.
         */
        public UndoBar create() {
            UndoBar undoBarController = new UndoBar(mActivity);
            undoBarController.setListener(mUndoListener);
            undoBarController.setUndoToken(mUndoToken);
            undoBarController.setMessage(mUndoMessage);
            undoBarController.setDuration(mDuration);
            undoBarController.setAnimationDuration(mAnimationDuration);
            return undoBarController;
        }

        /**
         * Calls {@link #show(boolean)} with {@code shouldAnimate = true}.
         */
        public void show() {
            show(true);
        }

        /**
         * Shows the {@link UndoBar} with this Builder's configuration.
         *
         * @param shouldAnimate whether the {@link UndoBar} should animate in and out.
         */
        @SuppressWarnings("SameParameterValue")
        public void show(boolean shouldAnimate) {
            create().show(shouldAnimate);
        }
    }
}
