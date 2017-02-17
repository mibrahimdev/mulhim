package uk.co.ribot.androidboilerplate.ui.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.PerfectImage;
import uk.co.ribot.androidboilerplate.ui.imagedetail.ImageDetailsActivity;

public class PerfectMulhimAdapter
    extends RecyclerView.Adapter<PerfectMulhimAdapter.MulhimViewHolder> {

  private List<PerfectImage> images;

  @Inject
  public PerfectMulhimAdapter() {
    images = new ArrayList<>();
  }

  public void setImages(List<PerfectImage> images) {
    this.images = images;
  }

  @Override public MulhimViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imag, parent, false);
    return new MulhimViewHolder(view, parent.getContext());
  }

  @Override public void onBindViewHolder(MulhimViewHolder holder, int position) {
    holder.bind(position);
  }

  @Override public int getItemCount() {
    return images.size();
  }

  class MulhimViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.grid_item) DynamicHeightImageView ivStaggeredItem;
    @BindView(R.id.card_view) CardView cvContainer;
    @BindView(R.id.image_title) TextView tvImageTitle;
    @BindView(R.id.image_author) TextView tvImageAuthor;

    Context mContext;

    MulhimViewHolder(View itemView, Context context) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.mContext = context;
      itemView.setOnClickListener(this);
    }

    void bind(int index) {
      final PerfectImage image = images.get(index);
      ivStaggeredItem.
          setAspectRatio(Float.valueOf(image.getImage().getWidth()) / Float.valueOf(
              image.getImage().getHeight()));
      Glide.with(mContext)
          .load(image.getImage().getUrl())
          .diskCacheStrategy(DiskCacheStrategy.SOURCE)
          .listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                boolean isFirstResource) {
              return false;
            }

            @Override public boolean onResourceReady(GlideDrawable resource, String model,
                Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
              Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
              Palette palette = Palette.generate(bitmap);
              int defaultColor = 0xFF333333;
              int color = palette.getDarkMutedColor(defaultColor);
              cvContainer.setBackgroundColor(color);
              image.setBackgroundColor(color);
              return false;
            }
          })
          .into(ivStaggeredItem);
      tvImageAuthor.setText(image.getImageDetails().getAuthor());
      tvImageTitle.setText(image.getImageDetails().getTitle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override public void onClick(View view) {
      int position = getAdapterPosition();
      Intent intent = ImageDetailsActivity.newStartIntent(mContext, images.get(position));
      View sharedView = cvContainer;
      String transitionName = mContext.getString(R.string.mulhim_shared_elemet);

      ActivityOptions transitionActivityOptions =
          ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, sharedView,
              transitionName);
      mContext.startActivity(intent, transitionActivityOptions.toBundle());
    }
  }

  private void setFadeAnimation(View view) {
    AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
    anim.setDuration(1000);
    view.startAnimation(anim);
  }
}
