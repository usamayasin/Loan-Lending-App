package org.jethro.mobile.ui.views;

import org.jethro.mobile.models.FAQ;
import org.jethro.mobile.ui.views.base.MVPView;

import java.util.ArrayList;

/**
 * Created by dilpreet on 12/8/17.
 */

public interface HelpView extends MVPView {

    void showFaq(ArrayList<FAQ> faqArrayList);

}
