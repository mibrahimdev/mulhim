package uk.co.ribot.androidboilerplate.data;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;
import uk.co.ribot.androidboilerplate.data.local.DatabaseHelper;
import uk.co.ribot.androidboilerplate.data.local.PreferencesHelper;
import uk.co.ribot.androidboilerplate.data.model.Image;
import uk.co.ribot.androidboilerplate.data.model.ImageDetails;
import uk.co.ribot.androidboilerplate.data.model.PerfectImage;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.data.remote.MulhimService;

@Singleton
public class DataManager {

    //to get cache client you can setup a whole new service with Okhttp caching 
    private final MulhimService mMulhimService;
    private final MulhimService mMulhimServiceWithCache;

    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(@Named("defaultMulhimService") MulhimService mulhimService,
                       @Named("MulhimServiceWithCache") MulhimService mulhimServiceWithCache,
                       PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mMulhimService = mulhimService;
        mMulhimServiceWithCache = mulhimServiceWithCache;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    //sync local with remote data
    public Observable<Ribot> syncRibots() {
        return mMulhimService.getRibots()
                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
                    @Override
                    public Observable<Ribot> call(List<Ribot> ribots) {
                        return mDatabaseHelper.setRibots(ribots);
                    }
                });
    }


    public Observable<List<Image>> getImages(double count) {
        return mMulhimServiceWithCache.getImages(count);
    }

    public Observable<ImageDetails> getImageDetails(String imageID) {
        return mMulhimServiceWithCache.getImageDetails(imageID);
    }

    @RxLogObservable()
    public Observable<List<PerfectImage>> getPerfectImages(double count) {
        return getImages(count)
                .flatMapIterable(new Func1<List<Image>, Iterable<Image>>() {
                    @Override
                    public Iterable<Image> call(List<Image> images) {
                        return images;
                    }
                })
                .flatMap(new Func1<Image, Observable<PerfectImage>>() {
                    @Override
                    public Observable<PerfectImage> call(final Image image) {
                        return getImageDetails(image.getId())
                                .map(new Func1<ImageDetails, PerfectImage>() {
                                    @Override
                                    public PerfectImage call(ImageDetails imageDetails) {
                                        return new PerfectImage(image, imageDetails);
                                    }
                                });
                    }
                }).toList().doOnNext(new Action1<List<PerfectImage>>() {
                    @Override
                    public void call(List<PerfectImage> imageList) {
                        Timber.d("Final Size %s", imageList.size());
                    }
                });
    }
}
