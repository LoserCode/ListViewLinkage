package com.listviewlinkage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.listviewlinkage.R;
import com.listviewlinkage.bean.LeftBean;

import java.util.List;

/**
 * Created by dell on 2017/8/2.
 */

public class LeftAdapter extends BaseAdapter {
    private Context context;
    private List<LeftBean> leftData;
    private LayoutInflater inflater;

    public LeftAdapter(Context context, List<LeftBean> leftData) {
        this.context = context;
        this.leftData = leftData;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return leftData.size();
    }

    @Override
    public Object getItem(int i) {
        return leftData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_left, null);
            viewHolder.mLeftText = convertView.findViewById(R.id.left_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.upDateView(i);
        return convertView;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    class ViewHolder {
        public TextView mLeftText;

        public void upDateView(int position) {
            mLeftText.setText(leftData.get(position).getLeftString());
            if (leftData.get(position).isSelect) {
                mLeftText.setBackgroundColor(Color.WHITE);
            } else {
                mLeftText.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
}
