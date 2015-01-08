package com.jensdriller.libs.sample;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by jenzz on 29/10/14.
 */
public class TestDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TestDialog(getActivity());
    }
}
