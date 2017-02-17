package uk.co.ribot.androidboilerplate.ui.imagedetail;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;

/**
 * Created by GeekyMind on 14/1/2017.
 * project: mulhim
 * Think but Code more:)
 */

public class ImageDetailsPresenter extends BasePresenter<ImageDetailsMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscriptionGetImageDetail;
    private List<Subscription> mSubscriptionList = new ArrayList<>();

    @Inject
    public ImageDetailsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mSubscriptionList.add(mSubscriptionGetImageDetail);
    }

    @Override
    public void detachView() {
        super.detachView();
        for (Subscription sub: mSubscriptionList) {
            sub.unsubscribe();
        }
    }

}
