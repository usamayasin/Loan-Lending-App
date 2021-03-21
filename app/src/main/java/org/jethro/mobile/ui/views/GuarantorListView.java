package org.jethro.mobile.ui.views;

/*
 * Created by saksham on 24/July/2018
 */

import org.jethro.mobile.models.guarantor.GuarantorPayload;
import org.jethro.mobile.ui.views.base.MVPView;

import java.util.List;

public interface GuarantorListView extends MVPView {

    void showGuarantorListSuccessfully(List<GuarantorPayload> list);
    void showError(String message);
}
