package uk.co.ribot.androidboilerplate.ui.imagedetail;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.PerfectImage;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;


public class ImageDetailsActivity extends BaseActivity implements ImageDetailsMvpView {


    private static final String MULHIM_IMAGE = "mulhim_image";

    @BindView(R.id.image)
    ImageView ivImage;
    @BindView(R.id.card_view)
    CardView cvContainer;
    @BindView(R.id.image_title)
    TextView tvTitle;
    @BindView(R.id.image_author)
    TextView tvAuthor;


    @Inject
    ImageDetailsPresenter mPresenter;

    public static Intent newStartIntent(Context context, PerfectImage image) {
        Intent intent = new Intent(context, ImageDetailsActivity.class);
        intent.putExtra(MULHIM_IMAGE, image);
        return intent;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        mPresenter.attachView(this);

        loadViews();

    }


    @Override
    public void openExternalLink(String url) {

    }

    @Override
    public void showProgress(boolean showProgress) {

    }

    @Override
    public void showError(String message) {

    }

    private void loadViews() {
        if (getIntent() != null) {
            if (getIntent().getParcelableExtra(MULHIM_IMAGE) != null) {
                final PerfectImage image = getIntent().getParcelableExtra(MULHIM_IMAGE);
                Glide.with(this)
                        .load(image.getImage().getUrl())
                        .into(ivImage);
                cvContainer.setBackgroundColor(image.getBackgroundColor());
                tvTitle.setText(image.getImageDetails().getTitle());
                tvAuthor.setText(image.getImageDetails().getAuthor());
            }
        }
    }
}
