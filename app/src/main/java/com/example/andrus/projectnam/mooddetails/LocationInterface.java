package com.example.andrus.projectnam.mooddetails;

import android.location.Location;

public interface LocationInterface extends MoodDetailInterface {
    void locationCallBack(Location location);
}
