package org.jethro.mobile.ui.views;

import org.jethro.mobile.models.beneficiary.Beneficiary;
import org.jethro.mobile.models.templates.account.AccountOptionsTemplate;
import org.jethro.mobile.ui.views.base.MVPView;

import java.util.List;

/**
 * Created by dilpreet on 21/6/17.
 */

public interface ThirdPartyTransferView extends MVPView {

    void showUserInterface();

    void showToaster(String msg);

    void showThirdPartyTransferTemplate(AccountOptionsTemplate accountOptionsTemplate);

    void showBeneficiaryList(List<Beneficiary> beneficiaries);

    void showError(String msg);
}
