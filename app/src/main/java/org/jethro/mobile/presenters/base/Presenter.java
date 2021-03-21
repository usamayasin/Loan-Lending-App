package org.jethro.mobile.presenters.base;

import org.jethro.mobile.ui.views.base.MVPView;

/**
 * @author ishan
 * @since 19/05/16
 */
public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();

}
