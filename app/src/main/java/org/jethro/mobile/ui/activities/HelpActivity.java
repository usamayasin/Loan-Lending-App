package org.jethro.mobile.ui.activities;

import android.os.Bundle;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.fragments.HelpFragment;

/**
 * @author Rajan Maurya
 * On 11/03/19.
 */
public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        setToolbarTitle(getString(R.string.help));
        showBackButton();
        replaceFragment(HelpFragment.newInstance(), false, R.id.container);
    }
}
