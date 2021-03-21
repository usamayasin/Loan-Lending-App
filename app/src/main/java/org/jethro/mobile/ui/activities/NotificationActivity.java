package org.jethro.mobile.ui.activities;

import android.os.Bundle;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.fragments.NotificationFragment;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        replaceFragment(NotificationFragment.newInstance(), false, R.id.container);
    }
}
