package com.jungu.zootycoon;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TooltipDialog extends Dialog {
    //Member
    public TextView textContents;

    public TooltipDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tooltip);

        //View
        textContents = findViewById(R.id.textContents);

        //Event
        (findViewById(R.id.textSubmit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}