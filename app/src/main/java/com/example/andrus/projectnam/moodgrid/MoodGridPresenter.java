package com.example.andrus.projectnam.moodgrid;

import android.util.Log;

import com.example.andrus.projectnam.models.Category;
import com.example.andrus.projectnam.models.DetailMood;

import java.util.List;

class MoodGridPresenter {
    private MoodGridInterface moodGrid;

    MoodGridPresenter(MoodGridInterface moodGrid) {
        this.moodGrid = moodGrid;
    }

    void getMoodList() {
        MoodGridRequester.getServerResponse(this);
    }

    void successfulRetrieval(List<Category> moodList) {
        moodGrid.setGridLogoAndText(moodList);
    }

    void failedRetrieval(Throwable t) {
        Log.i("presenter", "failedRetrieval: " + t);
    }
}
