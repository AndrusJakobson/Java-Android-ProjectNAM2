package com.example.andrus.projectnam.mooddetails;


import com.example.andrus.projectnam.models.DetailMood;

import java.util.List;

public interface OfferDetailView extends LocationInterface{
    void setDetailText(DetailMood detailList);

    void setOfferTitle(String title);

    void setOfferText(String text);

    void setTimeToOfferLocationText(String time);

    void requestWalkingTime();
}
