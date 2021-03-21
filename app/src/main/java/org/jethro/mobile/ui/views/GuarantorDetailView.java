package org.jethro.mobile.ui.views;

/*
 * Created by saksham on 25/July/2018
 */

import org.jethro.mobile.ui.views.base.MVPView;

public interface GuarantorDetailView extends MVPView {
    void guarantorDeletedSuccessfully(String message);
    void showError(String message);
}
