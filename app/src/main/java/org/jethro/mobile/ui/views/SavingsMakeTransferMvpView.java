package org.jethro.mobile.ui.views;

import org.jethro.mobile.models.templates.account.AccountOptionsTemplate;
import org.jethro.mobile.ui.views.base.MVPView;

/**
 * Created by Rajan Maurya on 10/03/17.
 */

public interface SavingsMakeTransferMvpView extends MVPView {

    void showUserInterface();

    void showSavingsAccountTemplate(AccountOptionsTemplate accountOptionsTemplate);

    void showToaster(String message);

    void showError(String message);

    void showProgressDialog();

    void hideProgressDialog();
}
