package org.jethro.mobile.ui.activities;
/*
~This project is licensed under the open source MPL V2.
~See https://github.com/openMF/self-service-app/blob/master/LICENSE.md
*/

import android.os.Bundle;

import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.fragments.LoanAccountsDetailFragment;
import org.jethro.mobile.utils.Constants;

public class LoanAccountContainerActivity extends BaseActivity {

    private long loanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        loanId = getIntent().getExtras().getLong(Constants.LOAN_ID);

        replaceFragment(LoanAccountsDetailFragment.newInstance(loanId), false, R.id.container);
        showBackButton();
    }
}
