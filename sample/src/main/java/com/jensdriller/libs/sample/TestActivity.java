package com.jensdriller.libs.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jensdriller.libs.undobar.UndoBar;

public class TestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.undo_test);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_styles);
        spinner.setAdapter(new ArrayAdapter<UndoBar.Style>(this, android.R.layout.simple_list_item_1, UndoBar.Style.values()));

        final LogView logView = (LogView) findViewById(R.id.log);

        findViewById(R.id.btn_show_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UndoBar undoBar = new UndoBar.Builder(TestActivity.this)//
                        .setMessage("Undo Me!")//
                        .setStyle((UndoBar.Style) spinner.getSelectedItem())//
                        .setUndoColor(getResources().getColor(R.color.primaryColor))
                        .create();
                logView.bind(undoBar);
                logView.log("show()");
                undoBar.show();
            }
        });
    }

}
