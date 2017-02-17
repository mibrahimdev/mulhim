package uk.co.ribot.androidboilerplate.data.remote;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;
import uk.co.ribot.androidboilerplate.BuildConfig;
import uk.co.ribot.androidboilerplate.data.model.Image;
import uk.co.ribot.androidboilerplate.data.model.ImageDetails;
import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.util.MyGsonTypeAdapterFactory;
import uk.co.ribot.androidboilerplate.util.NetworkUtil;

public interface MulhimService {

    String ENDPOINT = "http://mulhim.net/api/";

    @GET("ribots")
    Observable<List<Ribot>> getRibots();


    @GET("images")
    Observable<List<Image>> getImages(@Query("count") double count);

    @GET("image")
    Observable<ImageDetails> getImageDetails(@Query("id") String imageID);

    @Multipart
    @Headers("Content-type:multipart/form-data")
    @GET("/image")
    Observable<Response> postImage(@Part ("image-author")RequestBody author,
                                   @Part("image-title") RequestBody title,
                                   @Part("image-link") RequestBody link,
                                   @Part MultipartBody.Part file);
    /********
     * Helper class that sets up a new services
     *******/
    class Creator {

        public static MulhimService newMulhimService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MulhimService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(MulhimService.class);
        }

        public static MulhimService newMulhimServiceWithCache(Context appContext){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MulhimService.ENDPOINT)
                    .client(Creator.getOkHttpClientWithCache(appContext))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(MulhimService.class);
        }

        static OkHttpClient getOkHttpClientWithCache(Context appContext){

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.HEADERS: HttpLoggingInterceptor.Level.NONE);

            int cacheSize = 20 * 1024 * 1024; // 20 MiB
            return new OkHttpClient.Builder()
                    .cache(new Cache(appContext.getCacheDir(), cacheSize))
                    .addNetworkInterceptor(new RemoveHeadersInterceptor())
                    .addNetworkInterceptor(new CacheInterceptor())
                    .addNetworkInterceptor(new OfflineCacheInterceptor())
                    .addInterceptor(logging)
                    .build();
        }
    }



    static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(6, TimeUnit.HOURS)
                    .build();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build();
        }
    }

    static class OfflineCacheInterceptor implements Interceptor {

        @Inject
        Context mContext;

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkConnected(mContext)){
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(6, TimeUnit.HOURS)
                        .build();
                request = request.newBuilder()
                        .removeHeader("Cache-Control")
                        .cacheControl(cacheControl)
                        .build();

            }
            return chain.proceed(request);
        }
    }

    static class RemoveHeadersInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .removeHeader("Access-Control-Allow-Origin")
                    .removeHeader("Vary")
                    .removeHeader("Age")
                    .removeHeader("Via")
                    .removeHeader("C3-Request")
                    .removeHeader("C3-Domain")
                    .removeHeader("C3-Date")
                    .removeHeader("C3-Hostname")
                    .removeHeader("C3-Cache-Control")
                    .removeHeader("X-Varnish-back")
                    .removeHeader("X-Varnish")
                    .removeHeader("X-Cache")
                    .removeHeader("X-Cache-Hit")
                    .removeHeader("X-Varnish-front")
                    .removeHeader("Connection")
                    .removeHeader("Accept-Ranges")
                    .removeHeader("Transfer-Encoding")
                    .removeHeader("Pragma")
                    .build();
        }
    }

}
