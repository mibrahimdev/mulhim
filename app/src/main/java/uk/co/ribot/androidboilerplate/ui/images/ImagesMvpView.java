package uk.co.ribot.androidboilerplate.ui.images;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.Image;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

/**
 * Created by GeekyMind on 26/11/2016.
 * project: mulhim
 * Think but Code more:)
 */

public interface ImagesMvpView extends MvpView {

    void showRibotsEmpty();

    void showError();

    void showImages(List<Image> images);
}
