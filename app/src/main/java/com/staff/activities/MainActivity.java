package com.staff.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jauker.widget.BadgeView;
import com.staff.activities.base.BaseActivity;
import com.staff.main.R;

public class MainActivity extends BaseActivity {
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn_test = (Button)findViewById(R.id.btn_test);
        final BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(btn_test);
        badgeView.setBadgeCount(++count);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,QRCodeCaptureActivity.class));
            }
        });

    }

    @Override
    public int getContentLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {

    }

}
