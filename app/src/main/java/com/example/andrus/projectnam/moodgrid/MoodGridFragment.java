package com.example.andrus.projectnam.moodgrid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.andrus.projectnam.MainActivity;
import com.example.andrus.projectnam.R;
import com.example.andrus.projectnam.models.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoodGridFragment extends Fragment implements MoodGridInterface {
    @BindView(R.id.fragmentMainScreen_recyclerView)
    RecyclerView mainRecyclerView;
    @BindView(R.id.fragmentMainScreen_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.fragmentMainScreen_gridHeader)
    TextView gridHeader;

    private List<Category> moodList;
    RecyclerView.LayoutManager mainLayoutManager;
    RecyclerView.Adapter mainAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (moodList == null) {
            loading();
        } else {
            setGridLogoAndText(moodList);
        }
        new MoodGridPresenter(this).getMoodList();

    }

    @Override
    public void setGridLogoAndText(List<Category> moodList) {

        notLoading();
        this.moodList = moodList;
        mainLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        mainAdapter = new MoodGridAdapter(moodList, (MainActivity) getActivity());
        mainRecyclerView.setLayoutManager(mainLayoutManager);
        mainRecyclerView.setAdapter(mainAdapter);
    }

    @Override
    public void notLoading() {
        progressBar.setVisibility(View.GONE);
        gridHeader.setVisibility(View.VISIBLE);
    }

    @Override
    public void loading() {
        progressBar.setVisibility(View.VISIBLE);
        gridHeader.setVisibility(View.GONE);
    }
}
