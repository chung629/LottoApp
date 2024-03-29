package org.chunghyun.lottoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;

import org.chunghyun.lottoapp.R;

public class ProgressDialog extends Dialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);
    }
}
