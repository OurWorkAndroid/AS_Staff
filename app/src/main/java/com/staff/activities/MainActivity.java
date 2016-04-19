package com.staff.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jauker.widget.BadgeView;
import com.staff.activities.base.BaseActivity;
import com.staff.adapters.ApprovalAdapter;
import com.staff.main.R;

public class MainActivity extends BaseActivity {
    private int count = 0;
    private BadgeView badgeView;
    ApprovalAdapter approvalAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn_test = new Button(this);
        badgeView = new BadgeView(this);
        badgeView.setTargetView(btn_test);
        badgeView.setBadgeCount(++count);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badgeView.setBadgeCount(++count);
                //startActivity(new Intent(MainActivity.this,QRCodeCaptureActivity.class));
            }
        });

        setTitleRightView(btn_test);

        ListView lv_approval = (ListView)findViewById(R.id.lv_approval);
         approvalAdapter = new ApprovalAdapter(this,"daemon");
        lv_approval.setAdapter(approvalAdapter);
        resolveRefreshScrollConflict(lv_approval);
    }


    @Override
    public boolean onPullDownRefreshAllData() {
        approvalAdapter.setName(String.valueOf(++count));
        approvalAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public int getContentLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {

    }

}
