package com.jensdriller.libs.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jensdriller.libs.undobar.UndoBar;

public class MainActivity extends Activity {

    private static int mLogCount;

    private TextView mTxtLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtLog = (TextView) findViewById(R.id.txt_log);

        Button btnActivity = (Button) findViewById(R.id.btn_activity);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("show");

                new UndoBar.Builder(MainActivity.this)//
                        .setMessage("Undo Me!")//
                        .setListener(new UndoBar.Listener() {
                            @Override
                            public void onHide() {
                                log("onHide");
                            }

                            @Override
                            public void onUndo(Parcelable token) {
                                log("onUndo " + token);
                            }
                        })
                        .show();
            }
        });
    }

    private void log(String text) {
        mTxtLog.append("\n");
        mTxtLog.append("#" + ++mLogCount + " ");
        mTxtLog.append(text);
    }

}
