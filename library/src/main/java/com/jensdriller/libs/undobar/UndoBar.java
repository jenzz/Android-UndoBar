package com.jensdriller.libs.undobar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

@SuppressWarnings({"UnusedDeclaration", "WeakerAccess"})
public class UndoBar {

    public enum Style {
        /**
         * The default style of the device's current API level.
         */
        DEFAULT(R.layout.undo_bar),
        /**
         * The default style for API Level <= 18.
         * <p/>
         * Example:<br>
         * <img src="https://camo.githubusercontent.com/3559ea695528c547ecdb918004b0c1df7ac83999/68747470733a2f2f7261772e6769746875622e636f6d2f6a656e7a7a2f416e64726f69642d556e646f4261722f6d61737465722f6173736574732f53637265656e73686f74312e706e67" />
         * <br>
         * <img src="https://camo.githubusercontent.com/22ac172d0a9e1273b87d9164a99c6a0933996164/68747470733a2f2f7261772e6769746875622e636f6d2f6a656e7a7a2f416e64726f69642d556e646f4261722f6d61737465722f6173736574732f53637265656e73686f74322e706e67" />
         */
        HOLO(R.layout.undo_bar_holo),
        /**
         * The default style for API Level 19 + 20.
         * <p/>
         * Example:<br>
         * <img src="https://camo.githubusercontent.com/bec5d8cf19564df3091cf5e2e77aff6760e88273/68747470733a2f2f7261772e6769746875622e636f6d2f6a656e7a7a2f416e64726f69642d556e646f4261722f6d61737465722f6173736574732f53637265656e73686f74332e706e67" />
         * <br>
         * <img src="https://camo.githubusercontent.com/107d8ed2fd880038b1d4a71dec9bbd1e02fd58e7/68747470733a2f2f7261772e6769746875622e636f6d2f6a656e7a7a2f416e64726f69642d556e646f4261722f6d61737465722f6173736574732f53637265656e73686f74342e706e67" />
         */
        KITKAT(R.layout.undo_bar_kitkat),
        /**
         * The default style for API Level >= 21.
         * <p/>
         * Example:<br>
         * <img src="https://camo.githubusercontent.com/a32255c0a1f5abe56607d46bb9782b8f338fd9e3/68747470733a2f2f7261772e6769746875622e636f6d2f6a656e7a7a2f416e64726f69642d556e646f4261722f6d61737465722f6173736574732f53637265656e73686f74352e706e67" />
         * <br>
         * <img src="https://camo.githubusercontent.com/62d186f3ce9d55fa2b114b62887c714733155d5e/68747470733a2f2f7261772e6769746875622e636f6d2f6a656e7a7a2f416e64726f69642d556e646f4261722f6d61737465722f6173736574732f53637265656e73686f74362e706e67" />
         */
        LOLLIPOP(R.layout.undo_bar_lollipop);

        private final int mLayoutResId;

        private Style(int layoutResId) {
            mLayoutResId = layoutResId;
        }

        int getLayoutResId() {
            return mLayoutResId;
        }
    }

    /**
     * Listener for actions of the undo bar.
     */
    public interface Listener {
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

    protected final Context mContext;
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
    protected boolean mUseEnglishLocale;
    protected Style mStyle = Style.DEFAULT;
    protected int mUndoColor = Color.WHITE;
    protected boolean mAlignParentBottom;

    /**
     * Creates a new undo bar instance to be displayed in the given {@link Activity}.
     */
    public UndoBar(Activity activity) {
        this(activity.getWindow());
    }

    /**
     * Creates a new undo bar instance to be displayed in the given {@link Activity}.
     * <p/>
     * The style forces the the undo bar to match the look and feel of a certain API level.<br>
     * By default, it uses the style of the device's current API level.
     * <p/>
     * This is useful, for example, if you want to show a consistent
     * Lollipop style across all API levels.
     */
    public UndoBar(Activity activity, Style style) {
        this(activity.getWindow(), style);
    }

    /**
     * Creates a new undo bar instance to be displayed in the given {@link Dialog}.
     */
    public UndoBar(Dialog dialog) {
        this(dialog.getWindow());
    }

    /**
     * Creates a new undo bar instance to be displayed in the given {@link Dialog}.
     * <p/>
     * The style forces the the undo bar to match the look and feel of a certain API level.<br>
     * By default, it uses the style of the device's current API level.
     * <p/>
     * This is useful, for example, if you want to show a consistent
     * Lollipop style across all API levels.
     */
    public UndoBar(Dialog dialog, Style style) {
        this(dialog.getWindow(), style);
    }

    /**
     * Creates a new undo bar instance to be displayed in the given {@link Window}.
     */
    public UndoBar(Window window) {
        this(window, null);
    }

    /**
     * Creates a new undo bar instance to be displayed in the given {@link Window}.
     * <p/>
     * The style forces the the undo bar to match the look and feel of a certain API level.<br>
     * By default, it uses the style of the device's current API level.
     * <p/>
     * This is useful, for example, if you want to show a consistent
     * Lollipop style across all API levels.
     */
    public UndoBar(Window window, Style style) {
        if (style == null) {
            style = Style.DEFAULT;
        }

        mContext = window.getContext();
        mStyle = style;
        mView = getView(window);
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
        mUndoMessage = mContext.getString(messageResId);
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
     * Forces the English {@link java.util.Locale Locale} to be used explicitly.<br>
     * This means that the undo bar label will always show <b>UNDO</b>
     * regardless of the device's current {@link java.util.Locale Locale}.
     */
    public void setUseEnglishLocale(boolean useEnglishLocale) {
        mUseEnglishLocale = useEnglishLocale;
    }

    /**
     * Sets the text color of the undo button.<br>
     * The default color is white.<br>
     * <b>Note:</b> This is only applied to the {@link UndoBar.Style#LOLLIPOP}
     * style and ignored otherwise.
     */
    public void setUndoColor(int color) {
        mUndoColor = color;
    }

    /**
     * Sets the text color resource id of the undo button.<br>
     * The default color is white.<br>
     * <b>Note:</b> This is only applied to the {@link UndoBar.Style#LOLLIPOP}
     * style and ignored otherwise.
     */
    public void setUndoColorResId(int colorResId) {
        mUndoColor = mContext.getResources().getColor(colorResId);
    }

    /**
     * If set to {@code true}, the undo bar will appear stuck at the bottom without any margins.<br>
     * The default is {@code false}.<br>
     * <b>Note:</b> This is only applied to the {@link UndoBar.Style#LOLLIPOP}
     * style on devices with a smallest width of less than 600dp and ignored otherwise.
     */
    public void setAlignParentBottom(boolean alignParentBottom) {
        mAlignParentBottom = alignParentBottom;
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
        mView.setButtonLabel(mUseEnglishLocale ? R.string.undo_english : R.string.undo);
        if (isLollipopStyle(mStyle)) {
            mView.setUndoColor(mUndoColor);
            if (mAlignParentBottom && isAlignBottomPossible()) {
                removeMargins(mView);
            }
        }

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
     * Checks whether the given style is {@link Style#LOLLIPOP}.
     * Either explicitly set or the system default.
     */
    private boolean isLollipopStyle(Style style) {
        return style == Style.LOLLIPOP || (style == Style.DEFAULT && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    /**
     * Checks whether aligning the undo bar at the bottom is possible
     * for the current device configuration.
     */
    private boolean isAlignBottomPossible() {
        return mContext.getResources().getBoolean(R.bool.is_align_bottom_possible);
    }

    /**
     * Removes any margins from the given view.
     */
    private static void removeMargins(View view) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = layoutParams.topMargin = layoutParams.rightMargin = layoutParams.bottomMargin = 0;
        view.setLayoutParams(layoutParams);
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
        mUndoListener = null;
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
     * given {@link Window}.<br>
     * If {@code true}, returns that instance.<br>
     * If {@code false}, inflates a new {@link UndoBarView} and returns it.
     */
    protected UndoBarView getView(Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();

        // if we're operating within an Activity, limit ourselves to the content view.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        if (rootView == null) {
            rootView = decorView;
        }

        // if it's the first undo bar in this window or a different style, inflate a new instance
        UndoBarView undoBarView = (UndoBarView) rootView.findViewById(R.id.undoBar);
        if (undoBarView == null || undoBarView.getTag() != mStyle) {
            rootView.removeView(undoBarView); // remove potential undo bar w/ different style
            undoBarView = (UndoBarView) LayoutInflater.from(rootView.getContext())
                    .inflate(mStyle.getLayoutResId(), rootView, false);
            undoBarView.setTag(mStyle);
            rootView.addView(undoBarView);
        }

        return undoBarView;
    }

    public static class Builder {

        private final Window mWindow;

        private CharSequence mUndoMessage;
        private Listener mUndoListener;
        private Parcelable mUndoToken;
        private int mDuration = DEFAULT_DURATION;
        private int mAnimationDuration = DEFAULT_ANIMATION_DURATION;
        private boolean mUseEnglishLocale;
        private Style mStyle;
        private int mUndoColor = Color.WHITE;
        private boolean mAlignParentBottom;

        /**
         * Constructor using the {@link android.app.Activity} in which the undo bar will be
         * displayed
         */
        public Builder(Activity activity) {
            mWindow = activity.getWindow();
        }

        /**
         * Constructor using the {@link android.app.Dialog} in which the undo bar will be
         * displayed
         */
        public Builder(Dialog dialog) {
            mWindow = dialog.getWindow();
        }

        /**
         * Constructor using the {@link Window} in which the undo bar will be
         * displayed
         */
        public Builder(Window window) {
            mWindow = window;
        }

        /**
         * Sets the message to be displayed on the left of the undo bar.
         */
        public Builder setMessage(int messageResId) {
            mUndoMessage = mWindow.getContext().getString(messageResId);
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
         * Forces the English {@link java.util.Locale Locale} to be used explicitly.<br>
         * This means that the undo bar label will always show <b>UNDO</b>
         * regardless of the device's current {@link java.util.Locale Locale}.
         */
        public Builder setUseEnglishLocale(boolean useEnglishLocale) {
            mUseEnglishLocale = useEnglishLocale;
            return this;
        }

        /**
         * Forces the style of the undo bar to match a certain API level.<br>
         * By default, it uses the style of the device's current API level.
         * <p/>
         * This is useful, for example, if you want to show a consistent
         * Lollipop style across all API levels.
         */
        public Builder setStyle(Style style) {
            mStyle = style;
            return this;
        }

        /**
         * Sets the text color of the undo button.<br>
         * The default color is white.<br>
         * <b>Note:</b> This is only applied to the {@link UndoBar.Style#LOLLIPOP}
         * style and ignored otherwise.
         */
        public Builder setUndoColor(int undoColor) {
            mUndoColor = undoColor;
            return this;
        }

        /**
         * Sets the text color resource id of the undo button.<br>
         * The default color is white.<br>
         * <b>Note:</b> This is only applied to the {@link UndoBar.Style#LOLLIPOP}
         * style and ignored otherwise.
         */
        public Builder setUndoColorResId(int undoColorResId) {
            mUndoColor = mWindow.getContext().getResources().getColor(undoColorResId);
            return this;
        }

        /**
         * If set to {@code true}, the undo bar will appear stuck at the bottom without any margins.<br>
         * The default is {@code false}.<br>
         * <b>Note:</b> This is only applied to the {@link UndoBar.Style#LOLLIPOP}
         * style on devices with a smallest width of less than 600dp and ignored otherwise.
         */
        public Builder setAlignParentBottom(boolean alignParentBottom) {
            mAlignParentBottom = alignParentBottom;
            return this;
        }

        /**
         * Creates an {@link UndoBar} instance with this Builder's
         * configuration.
         */
        public UndoBar create() {
            UndoBar undoBarController = new UndoBar(mWindow, mStyle);
            undoBarController.setListener(mUndoListener);
            undoBarController.setUndoToken(mUndoToken);
            undoBarController.setMessage(mUndoMessage);
            undoBarController.setDuration(mDuration);
            undoBarController.setAnimationDuration(mAnimationDuration);
            undoBarController.setUseEnglishLocale(mUseEnglishLocale);
            undoBarController.setUndoColor(mUndoColor);
            undoBarController.setAlignParentBottom(mAlignParentBottom);
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
