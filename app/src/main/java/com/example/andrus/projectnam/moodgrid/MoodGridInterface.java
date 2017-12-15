package com.example.andrus.projectnam.moodgrid;

import com.example.andrus.projectnam.models.Category;

import java.util.List;

public interface MoodGridInterface {
    void setGridLogoAndText(List<Category> moodsList);
    void notLoading();
    void loading();

}
