package com.example.andrus.projectnam.moodgrid;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.andrus.projectnam.MainActivity;
import com.example.andrus.projectnam.R;
import com.example.andrus.projectnam.models.Category;
import com.example.andrus.projectnam.mooddetails.DetailViewPagerFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoodGridAdapter extends RecyclerView.Adapter<MoodGridAdapter.GridView> {
    private List<Category> categoryList;
    private MainActivity mainActivity;

    MoodGridAdapter(List<Category> category, MainActivity mainActivity) {
        this.categoryList = category;
        this.mainActivity = mainActivity;
    }

    @Override
    public GridView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.adapter_main_screen, parent, false);

        return new GridView(view);
    }

    @Override
    public void onBindViewHolder(GridView holder, int position) {
        Category category = categoryList.get(position);
        holder.moodText.setText(category.categoryName);


        byte[] imageBytes = Base64.decode(String.valueOf(category.categoryLogo), Base64.DEFAULT);
        Glide.with(mainActivity)
                .load(imageBytes)
                .asBitmap()
                .dontAnimate()
                .into(holder.moodIcon);

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class GridView extends RecyclerView.ViewHolder {
        @BindView(R.id.adapterMainScreen_moodIcon)
        ImageView moodIcon;
        @BindView(R.id.adapterMainScreen_moodText)
        TextView moodText;

        GridView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDetailFragmentAndPass(categoryList.get(getAdapterPosition()).categoryId);
                }
            });
        }
    }

    private void openDetailFragmentAndPass(int id) {
        DetailViewPagerFragment fragment = DetailViewPagerFragment.newInstance();
        FragmentManager manager = mainActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainActivity_frameLayout, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
