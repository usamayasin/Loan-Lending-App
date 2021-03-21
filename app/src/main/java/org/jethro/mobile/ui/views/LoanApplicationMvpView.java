package org.jethro.mobile.ui.views;

import org.jethro.mobile.models.templates.loans.LoanTemplate;
import org.jethro.mobile.ui.views.base.MVPView;

/**
 * Created by Rajan Maurya on 06/03/17.
 */

public interface LoanApplicationMvpView extends MVPView {

    void showUserInterface();

    void showLoanTemplate(LoanTemplate loanTemplate);

    void showUpdateLoanTemplate(LoanTemplate loanTemplate);

    void showLoanTemplateByProduct(LoanTemplate loanTemplate);

    void showUpdateLoanTemplateByProduct(LoanTemplate loanTemplate);

    void showError(String message);
}
