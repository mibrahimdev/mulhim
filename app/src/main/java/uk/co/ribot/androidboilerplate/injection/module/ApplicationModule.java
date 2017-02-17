package uk.co.ribot.androidboilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.androidboilerplate.data.remote.MulhimService;
import uk.co.ribot.androidboilerplate.injection.ApplicationContext;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Named("defaultMulhimService")
    @Singleton
    MulhimService provideRibotsService() {
        return MulhimService.Creator.newMulhimService();
    }

    @Provides
    @Named("MulhimServiceWithCache")
    @Singleton
    MulhimService providesMulhimServiceWithCache(){
        return MulhimService.Creator.newMulhimServiceWithCache(mApplication);
    }

}
