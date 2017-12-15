package com.example.andrus.projectnam.mooddetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrus.projectnam.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailViewPagerFragment extends Fragment {
    private DetailSwipeAdapter pagerAdapter;
    @BindView(R.id.fragmentDetailViewPager_viewPager)
    ViewPager pager;

    public static DetailViewPagerFragment newInstance(){
        return new DetailViewPagerFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_detail_viewpager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        pagerAdapter = new DetailSwipeAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }
}
