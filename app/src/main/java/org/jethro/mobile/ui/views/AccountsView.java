package org.jethro.mobile.ui.views;

import org.jethro.mobile.models.accounts.loan.LoanAccount;
import org.jethro.mobile.models.accounts.savings.SavingAccount;
import org.jethro.mobile.models.accounts.share.ShareAccount;
import org.jethro.mobile.ui.views.base.MVPView;

import java.util.List;

/**
 * Created by Rajan Maurya on 23/10/16.
 */

public interface AccountsView extends MVPView {

    void showLoanAccounts(List<LoanAccount> loanAccounts);

    void showSavingsAccounts(List<SavingAccount> savingAccounts);

    void showShareAccounts(List<ShareAccount> shareAccounts);

    void showError(String errorMessage);
}
