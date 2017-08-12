package uk.co.ribot.androidboilerplate.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.PerfectImage;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainMvpView {

    public static final int IMAGES_COUNT = 20;
    private static int numberOfImages = IMAGES_COUNT;

    @Inject
    MainPresenter mPresenter;

    @Inject
    PerfectMulhimAdapter mPerfectMulhimAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView rvImages;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.attachView(this);

        setupToolbar();

        initializeRecyclerView();
        initializeViews();

        mPresenter.getPerfectImages(numberOfImages);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.app_name));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context, boolean bo) {
        return new Intent();
    }

    private void initializeRecyclerView() {
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        lm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvImages.setLayoutManager(lm);
        rvImages.setHasFixedSize(true);
        rvImages.setDrawingCacheEnabled(true);
        rvImages.setItemViewCacheSize(40);
        rvImages.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvImages.setAdapter(mPerfectMulhimAdapter);

        rvImages.addOnScrollListener(new EndlessRecyclerViewScrollListener(lm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                numberOfImages = numberOfImages * 2;
                mPresenter.getPerfectImages(numberOfImages);
            }
        });

    }


    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(mCoordinatorLayout, errorMessage, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void showPerfectImages(List<PerfectImage> imageList) {
        mPerfectMulhimAdapter.setImages(imageList);
        mPerfectMulhimAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.hide();
        }


    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyImages() {
        mPerfectMulhimAdapter.setImages(Collections.<PerfectImage>emptyList());
        mPerfectMulhimAdapter.notifyDataSetChanged();

    }

    private void initializeViews() {
        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .cancelable(false)
                .progress(true, 0);
        progressDialog = dialogBuilder.build();

    }

}
