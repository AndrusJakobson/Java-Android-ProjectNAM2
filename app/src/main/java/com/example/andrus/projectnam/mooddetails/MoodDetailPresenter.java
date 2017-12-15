package com.example.andrus.projectnam.mooddetails;

import com.example.andrus.projectnam.models.DetailMood;

class MoodDetailPresenter {
    private MoodDetailInterface detailInterface;

    MoodDetailPresenter(MoodDetailInterface detailInterface) {
        this.detailInterface = detailInterface;
    }

    void getViewData() {
        MoodDetailRequester.getResponse(this);
    }

    void successfulResponse(DetailMood detailMoods) {
        detailInterface.setDetailText(detailMoods);
    }

    void failureResponse(Throwable t) {

    }
}
