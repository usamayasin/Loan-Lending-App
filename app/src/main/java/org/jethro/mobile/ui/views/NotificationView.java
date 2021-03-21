package org.jethro.mobile.ui.views;

import org.jethro.mobile.models.notification.MifosNotification;
import org.jethro.mobile.ui.views.base.MVPView;

import java.util.List;

/**
 * Created by dilpreet on 14/9/17.
 */

public interface NotificationView extends MVPView {

    void showNotifications(List<MifosNotification> notifications);

    void showError(String msg);
}
