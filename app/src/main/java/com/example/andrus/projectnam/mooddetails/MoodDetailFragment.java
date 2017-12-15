package com.example.andrus.projectnam.mooddetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrus.projectnam.R;
import com.example.andrus.projectnam.models.DetailMood;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoodDetailFragment extends Fragment implements MoodDetailInterface {
    @BindView(R.id.fragmentDetailThirsty_header)
    TextView detailHeader;
    @BindView(R.id.fragmentDetailThirsty_deal)
    TextView detailDeal;
    @BindView(R.id.fragmentDetailThirsty_advertisement)
    TextView detailAdvertisement;

    public static MoodDetailFragment newInstance() {
        return new MoodDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_detail_thirsty, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        new MoodDetailPresenter(this).getViewData();
    }

    @Override
    public void setDetailText(DetailMood detailList) {
        detailHeader.setText(detailList.offerTitle);
        detailDeal.setText(detailList.OfferListByCategory.get(0).offerDescription);
    }
}
