package org.jethro.mobile.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.fragments.SettingsFragment;
import org.jethro.mobile.utils.Constants;
import org.jethro.mobile.utils.Utils;

import androidx.core.app.ActivityCompat;

public class SettingsActivity extends BaseActivity {

    private boolean hasSettingsChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setToolbarTitle(getString(R.string.settings));
        showBackButton();
        replaceFragment(SettingsFragment.newInstance(), false, R.id.container);
        if (getIntent().hasExtra(Constants.HAS_SETTINGS_CHANGED)) {
            hasSettingsChanged = getIntent().getBooleanExtra(Constants.HAS_SETTINGS_CHANGED,
                    false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.ACTIVITY_FROM_NAME ="SettingsActivity";
        if (hasSettingsChanged) {
            ActivityCompat.finishAffinity(this);
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
    }
}
