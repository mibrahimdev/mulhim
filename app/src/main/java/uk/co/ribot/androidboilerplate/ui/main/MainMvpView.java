package uk.co.ribot.androidboilerplate.ui.main;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.PerfectImage;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showErrorMessage(String errorMessage);

    void showProgress(boolean show);

    void showToast(String message);

    void showEmptyImages();

    void showPerfectImages(List<PerfectImage> imageList);


}
