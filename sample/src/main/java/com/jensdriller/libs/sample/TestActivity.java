package com.jensdriller.libs.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.jensdriller.libs.undobar.UndoBar;

public class TestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.undo_test);

        final LogView logView = (LogView) findViewById(R.id.log);

        findViewById(R.id.btn_show_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UndoBar undoBar = new UndoBar.Builder(TestActivity.this)//
                        .setMessage("Undo Me!")//
                        .create();
                logView.bind(undoBar);
                logView.log("show()");
                undoBar.show();
            }
        });
    }

}
