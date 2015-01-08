package com.jensdriller.libs.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), TestActivity.class));
            }
        });

        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TestDialog(v.getContext()).show();
            }
        });

        findViewById(R.id.btn_dialog_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TestDialogFragment().show(getSupportFragmentManager(), TAG);
            }
        });
    }

}
