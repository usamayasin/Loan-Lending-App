package org.jethro.mobile.ui.views;

/*
 * Created by saksham on 13/July/2018
 */

import org.jethro.mobile.ui.views.base.MVPView;

public interface UpdatePasswordView extends MVPView {

    void showError(String message);

    void showPasswordUpdatedSuccessfully();
}
