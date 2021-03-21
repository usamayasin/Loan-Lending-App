package org.jethro.mobile.ui.views;

import org.jethro.mobile.ui.views.base.MVPView;

/**
 * Created by dilpreet on 31/7/17.
 */

public interface RegistrationView extends MVPView {

    void showRegisteredSuccessfully();

    void showError(String msg);
}
