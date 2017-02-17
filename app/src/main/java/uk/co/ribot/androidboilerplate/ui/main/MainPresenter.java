package uk.co.ribot.androidboilerplate.ui.main;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.DataManager;
import uk.co.ribot.androidboilerplate.data.model.PerfectImage;
import uk.co.ribot.androidboilerplate.injection.ConfigPersistent;
import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {


    private final DataManager mDataManager;
    private Subscription mSubscription;
    private List<Subscription> mSubscriptionList = new ArrayList<>();

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mSubscriptionList.add(mSubscription);
    }

    @Override
    public void detachView() {
        super.detachView();
        for (Subscription sub : mSubscriptionList) {
            sub.unsubscribe();
        }
    }

    public void getPerfectImages(double count) {
        getMvpView().showProgress(true);
        mSubscription = mDataManager.getPerfectImages(count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<PerfectImage>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException){
                            getMvpView().showErrorMessage("Sorry! Your Network is a bit sick :)");
                        }
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onNext(List<PerfectImage> perfectImages) {
                        Timber.d("Size %s", perfectImages.size());
                        getMvpView().showProgress(false);
                        getMvpView().showPerfectImages(perfectImages);
                    }
                });

    }

}
