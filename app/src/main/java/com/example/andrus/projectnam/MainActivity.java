package com.example.andrus.projectnam;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.andrus.projectnam.moodgrid.MoodGridFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMainScreenFragment();
    }

    private void showMainScreenFragment() {
        MoodGridFragment moodGridFragment = new MoodGridFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.mainActivity_frameLayout, moodGridFragment);

        transaction.commit();
    }
}
