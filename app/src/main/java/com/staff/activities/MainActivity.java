package com.staff.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jauker.widget.BadgeView;
import com.staff.main.R;

public class MainActivity extends Activity {
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_test = (Button)findViewById(R.id.btn_test);
        final BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(btn_test);
        badgeView.setBadgeCount(++count);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badgeView.setBadgeCount(++count);
            }
        });
    }

}
