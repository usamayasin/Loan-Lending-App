package org.jethro.mobile.ui.views;

import org.jethro.mobile.ui.views.base.MVPView;

/**
 * Created by dilpreet on 7/6/17.
 */

public interface LoanAccountWithdrawView extends MVPView {

    public void showLoanAccountWithdrawSuccess();

    public void showLoanAccountWithdrawError(String message);
}
