package com.listviewlinkage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.listviewlinkage.R;
import com.listviewlinkage.bean.RightBean;

import java.util.List;

/**
 * Created by dell on 2017/8/2.
 */

public class RightAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<RightBean> rightData;
    private LayoutInflater inflater;

    public RightAdapter(Context context, List<RightBean> rightData) {
        this.context = context;
        this.rightData = rightData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return rightData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return rightData.get(groupPosition).getItemList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return rightData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return rightData.get(groupPosition).getItemList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new GroupViewHolder();
            convertView = inflater.inflate(R.layout.item_right_group, null);
            viewHolder.mHeaderText = convertView.findViewById(R.id.right_header_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        viewHolder.mHeaderText.setText(rightData.get(groupPosition).getHeadString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.item_right_child, null);
            viewHolder.mItemImage = convertView.findViewById(R.id.item_image);
            viewHolder.mItemText = convertView.findViewById(R.id.item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        viewHolder.mItemText.setText(rightData.get(groupPosition).getItemList().get(childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        public TextView mHeaderText;
    }

    class ChildViewHolder {
        public ImageView mItemImage;
        public TextView mItemText;
    }
}
