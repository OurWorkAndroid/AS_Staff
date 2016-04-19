package com.staff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.staff.customviews.CircleImageView;
import com.staff.main.R;

/**
 * Created by dengyaoning on 16/4/19.
 * 审批流程适配器
 */
public class ApprovalAdapter extends BaseAdapter{
    private Context context;
    private String name;

    public ApprovalAdapter(Context context,String name) {
        this.context = context;
        setName(name);
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_approval,parent,false);
            holder = new ViewHolder();

            holder.tv_approval_name = (TextView)convertView.findViewById(R.id.tv_approval_name);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_approval_name.setText(name);
        return convertView;
    }

    static class ViewHolder {
        TextView tv_approval_name,tv_approval_time,tv_approval_flow,tv_approval_reason,
                tv_approval_state;
        CircleImageView iv_approval_head;

    }
}