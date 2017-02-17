package uk.co.ribot.androidboilerplate.ui.images;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.model.Image;
import uk.co.ribot.androidboilerplate.ui.main.MainActivity;
import uk.co.ribot.androidboilerplate.ui.main.RibotsAdapter;
import uk.co.ribot.androidboilerplate.util.DialogFactory;

/**
 * Created by GeekyMind on 26/11/2016.
 * project: mulhim
 * Think but Code more:)
 */

public class ImagesFragment extends Fragment implements ImagesMvpView{
    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    ImagesPresenter mPresenter;
    @Inject
    RibotsAdapter mRibotsAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);
        ((MainActivity) getActivity()).activityComponent().inject(this);
        mPresenter.attachView(this);


        return  view;
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(getActivity(), getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showImages(List<Image> images) {
        mRibotsAdapter.setRibots(images);
        mRibotsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRibotsEmpty() {
        mRibotsAdapter.setRibots(Collections.<Image>emptyList());
        mRibotsAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }
}
