package org.jethro.mobile.ui.activities;

import android.os.Bundle;
import org.jethro.mobile.R;
import org.jethro.mobile.ui.activities.base.BaseActivity;
import org.jethro.mobile.ui.fragments.SavingAccountsDetailFragment;
import org.jethro.mobile.utils.Constants;

/**
 * Created by Rajan Maurya on 05/03/17.
 */

public class SavingsAccountContainerActivity extends BaseActivity {

    private long savingsId;
    public static boolean transferSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        savingsId = getIntent().getExtras().getLong(Constants.SAVINGS_ID);

        replaceFragment(SavingAccountsDetailFragment.newInstance(savingsId), false, R.id.container);
        showBackButton();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 2 && transferSuccess == true) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().popBackStack();
            transferSuccess = false;
        } else {
            super.onBackPressed();
        }
    }
}
