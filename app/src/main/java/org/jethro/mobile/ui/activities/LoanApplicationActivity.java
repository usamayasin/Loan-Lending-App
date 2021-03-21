package org.jethro.mobile.ui.activities;

import android.os.Bundle;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.enums.LoanState;
import org.jethro.mobile.ui.fragments.LoanApplicationFragment;

public class LoanApplicationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);
        if (savedInstanceState == null) {
            replaceFragment(LoanApplicationFragment.newInstance(LoanState.CREATE), false,
                    R.id.container);
        }
        showBackButton();
    }
}
