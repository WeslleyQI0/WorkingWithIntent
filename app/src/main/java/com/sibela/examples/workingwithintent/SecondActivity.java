package com.sibela.examples.workingwithintent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SecondActivity extends Activity {

    @Bind(R.id.message_text)
    TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        mMessage.setText(getIntent().getStringExtra("key"));
    }
}
