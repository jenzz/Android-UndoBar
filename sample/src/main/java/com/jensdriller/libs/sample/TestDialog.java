package com.jensdriller.libs.sample;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jensdriller.libs.undobar.UndoBar;

public class TestDialog extends Dialog {

    public TestDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.undo_test);
        setTitle(R.string.test_dialog);
        setHeight(0.75f); // 75% of the screen

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_styles);
        spinner.setAdapter(new ArrayAdapter<UndoBar.Style>(getContext(), android.R.layout.simple_list_item_1, UndoBar.Style.values()));

        final LogView logView = (LogView) findViewById(R.id.log);

        findViewById(R.id.btn_show_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UndoBar undoBar = new UndoBar.Builder(TestDialog.this)//
                        .setMessage("Undo Me!")//
                        .setStyle((UndoBar.Style) spinner.getSelectedItem())//
                        .setUndoColor(getContext().getResources().getColor(R.color.primaryColor))
                        .create();
                logView.bind(undoBar);
                logView.log("show()");
                undoBar.show();
            }
        });
    }

    private void setHeight(float height) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        getWindow().setLayout(getWindow().getAttributes().width, (int) (displaymetrics.heightPixels * height));
    }
}
