package org.jethro.mobile.ui.views;

/*
 * Created by saksham on 23/July/2018
 */

import org.jethro.mobile.models.guarantor.GuarantorApplicationPayload;
import org.jethro.mobile.models.guarantor.GuarantorTemplatePayload;
import org.jethro.mobile.ui.views.base.MVPView;

public interface AddGuarantorView extends MVPView {

    void updatedSuccessfully(String message);
    void submittedSuccessfully(String message, GuarantorApplicationPayload payload);
    void showGuarantorUpdation(GuarantorTemplatePayload template);
    void showGuarantorApplication(GuarantorTemplatePayload template);
    void showError(String message);

}
