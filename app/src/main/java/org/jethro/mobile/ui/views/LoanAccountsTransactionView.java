package org.jethro.mobile.ui.views;

import org.jethro.mobile.models.accounts.loan.LoanWithAssociations;
import org.jethro.mobile.ui.views.base.MVPView;

/**
 * Created by dilpreet on 4/3/17.
 */

public interface LoanAccountsTransactionView extends MVPView {

    void showUserInterface();

    void showLoanTransactions(LoanWithAssociations loanWithAssociations);

    void showEmptyTransactions(LoanWithAssociations loanWithAssociations);

    void showErrorFetchingLoanAccountsDetail(String message);
}
