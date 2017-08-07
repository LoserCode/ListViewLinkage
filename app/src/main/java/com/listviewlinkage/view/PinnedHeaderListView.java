package com.listviewlinkage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by dell on 2017/8/2.
 */

public class PinnedHeaderListView extends ListView implements AbsListView.OnScrollListener {
    public PinnedHeaderListView(Context context) {
        this(context,null);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PinnedHeaderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
