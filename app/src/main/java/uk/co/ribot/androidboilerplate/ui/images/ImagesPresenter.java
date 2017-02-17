package uk.co.ribot.androidboilerplate.ui.images;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.Image;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;
import uk.co.ribot.androidboilerplate.util.RxUtil;

/**
 * Created by GeekyMind on 26/11/2016.
 * project: mulhim
 * Think but Code more:)
 */

public class ImagesPresenter extends BasePresenter<ImagesMvpView>{
    private final DataManager mDataManager;
    private Subscription mSubscription;
    private Subscription mLoadSubscription;
    private List<Subscription> mSubscriptionList = new ArrayList<>();

    @Inject
    public ImagesPresenter(DataManager dataManager) {
        mDataManager = dataManager;

    }

    @Override
    public void attachView(ImagesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        for (Subscription s : mSubscriptionList) {
            s.unsubscribe();
        }
    }

    public void loadImages(double count) {
        checkViewAttached();
        RxUtil.unsubscribe(mLoadSubscription);
        mLoadSubscription = mDataManager
                .getImages(count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Image>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<Image> images) {
                        if (images.isEmpty()) {
                            getMvpView().showRibotsEmpty();
                        } else {
                            getMvpView().showImages(images);
                        }
                    }
                });
    }

}
