package org.jethro.mobile.ui.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;

public class BiometricActivity extends BaseActivity {

    ImageView fingerprint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);
        fingerprint  =  findViewById(R.id.iv_fingerprint);

        fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fingerprint.setImageResource(R.drawable.ic_fingerprint_green);
            }
        });
    }
}