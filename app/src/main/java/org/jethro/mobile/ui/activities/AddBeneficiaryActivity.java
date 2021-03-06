package org.jethro.mobile.ui.activities;

import android.os.Bundle;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.fragments.BeneficiaryAddOptionsFragment;

/**
 * @author Rajan Maurya
 * On 04/06/18.
 */
public class AddBeneficiaryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        showBackButton();
        replaceFragment(BeneficiaryAddOptionsFragment.newInstance(), false, R.id.container);
    }
}
