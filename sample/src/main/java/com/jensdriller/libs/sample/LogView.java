package com.jensdriller.libs.sample;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jensdriller.libs.undobar.UndoBar;

public class LogView extends ScrollView implements UndoBar.Listener {

    private int mLogCount;

    private TextView mTxtLog;

    public LogView(Context context) {
        super(context);
        init();
    }

    public LogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.log_view, this);
        mTxtLog = (TextView) findViewById(R.id.txt_log);
    }

    public void bind(UndoBar undoBar) {
        undoBar.setListener(this);
    }

    @Override
    public void onHide() {
        log("onHide()");
    }

    @Override
    public void onUndo(Parcelable token) {
        log("onUndo() " + token);
    }

    public void log(String text) {
        mTxtLog.append("\n");
        mTxtLog.append("#" + ++mLogCount + " ");
        mTxtLog.append(text);
    }

}
