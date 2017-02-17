package uk.co.ribot.androidboilerplate.ui.imagedetail;

import uk.co.ribot.androidboilerplate.ui.base.MvpView;


public interface ImageDetailsMvpView extends MvpView {

    void openExternalLink(String url);

    void showProgress(boolean showProgress);

    void showError(String message);

}
