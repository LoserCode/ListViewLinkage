package com.listviewlinkage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.listviewlinkage.adapter.LeftAdapter;
import com.listviewlinkage.adapter.RightAdapter;
import com.listviewlinkage.bean.LeftBean;
import com.listviewlinkage.bean.RightBean;
import com.listviewlinkage.utils.MyToast;
import com.listviewlinkage.view.PinnedHeaderExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PinnedHeaderExpandableListView.OnHeaderUpdateListener {

    private ListView mLeftListView;
    private PinnedHeaderExpandableListView mRightListView;
    private String[] leftStr = new String[]{"编程语言", "语言", "国家", "电脑", "手机", "平板"};
    private boolean[] flagArray = {true, false, false, false, false, false, false, false, false};
    private String[][] rightStr = new String[][]{{"Java", "Python", "PHP", ".net", "C", "C++"},
            {"中文", "英文", "梵文"},
            {"中国", "新加坡", "印度尼西亚"}, {"笔记本", "台式机"}, {"小米", "华为", "魅族", "联想", "苹果"},
            {"ipad", "小米", "华为"}
    };
    private RightAdapter mRightAdapter;
    private List<RightBean> mRightData;
    private List<LeftBean> mLeftData;
    private LeftAdapter mLeftAdapter;
    private ViewGroup mHeaderView;
    private boolean isScroll = true;//是否是滚动
    private SparseArray<Integer> mSectionCache = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLeftListView = (ListView) findViewById(R.id.left_list);
        mRightListView = (PinnedHeaderExpandableListView) findViewById(R.id.right_list);
        initData();
        mRightListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                MyToast.show(MainActivity.this, mRightData.get(i).getHeadString());
                return true;
            }
        });
        mRightListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                MyToast.show(MainActivity.this, mRightData.get(i).getItemList().get(i1));
                return false;
            }
        });
        mRightListView.setOnHeaderUpdateListener(this);
        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isScroll = false;//左侧点击导致右侧滚动
                for (int x = 0; x < mLeftData.size(); x++) {
                    if (x == i) {
                        mLeftData.get(x).setSelect(true);
                    } else {
                        mLeftData.get(x).setSelect(false);
                    }
                    MyToast.show(MainActivity.this, mLeftData.get(i).getLeftString());
                    mLeftAdapter.notifyDataSetChanged();
                    //计算右侧ListView滚动的位置
                    int rightPosition = 0;
                    for (int y = 0; y < i; y++) {
                        rightPosition = rightPosition + mRightAdapter.getChildrenCount(y) + 1;
                    }
                    mRightListView.setSelection(rightPosition);
                }
            }
        });
        mRightListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (mRightListView.getLastVisiblePosition() == (mRightListView.getCount() - 1)) {
                            mLeftListView.setSelection(ListView.FOCUS_DOWN);
                        }

                        // 判断滚动到顶部
                        if (mRightListView.getFirstVisiblePosition() == 0) {
                            mLeftListView.setSelection(0);
                        }

                        break;
                }
            }

            /**
             * 右侧滚动，左侧联动
             * @param absListView
             * @param i
             * @param i1
             * @param i2
             */
            int x = 0;
            int y = 0;
            int z = 0;

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < mLeftData.size(); i++) {
                        if (i == getSectionForPosition(mRightListView.getFirstVisiblePosition())) {
                            mLeftData.get(i).setSelect(true);
                            x = i;
                        } else {
                            mLeftData.get(i).setSelect(false);
                        }
                    }
                    if (x != y) {
                        mLeftAdapter.notifyDataSetChanged();
                        y = x;
//                        if (y == mLeftListView.getLastVisiblePosition()) {
////                            z = z + 3;
//                            mLeftListView.setSelection(z);
//                        }
//                        if (x == mLeftListView.getFirstVisiblePosition()) {
////                            z = z - 1;
//                            mLeftListView.setSelection(z);
//                        }
//                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
//                            mLeftListView.setSelection(ListView.FOCUS_DOWN);
//                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });
    }

    private int getSectionForPosition(int position) {
        Integer cachedSection = mSectionCache.get(position);
        if (cachedSection != null) {
            return cachedSection;
        }
        int sectionStart = 0;
        for (int i = 0; i < mRightData.size(); i++) {
            int sectionCount = mRightData.get(i).getItemList().size();
            int sectionEnd = sectionStart + sectionCount + 1;
            if (position >= sectionStart && position < sectionEnd) {
                mSectionCache.put(position, i);
                return i;
            }
            sectionStart = sectionEnd;
        }
        return 0;
    }

    /**
     * init data
     */
    private void initData() {
        mLeftData = new ArrayList<>();
        mRightData = new ArrayList<>();
        for (int x = 0; x < rightStr.length; x++) {
            LeftBean leftBean = new LeftBean();
            if (x == 0) {
                leftBean.setSelect(true);
            } else {
                leftBean.setSelect(false);
            }
            leftBean.setLeftString(leftStr[x]);
            mLeftData.add(leftBean);
            RightBean bean = new RightBean();
            bean.setHeadString(leftStr[x]);
            List<String> list = new ArrayList<>();
            for (int y = 0; y < rightStr[x].length; y++) {
                list.add(rightStr[x][y]);
            }
            bean.setItemList(list);
            mRightData.add(bean);
        }
        mRightAdapter = new RightAdapter(this, mRightData);
        mRightListView.setAdapter(mRightAdapter);
        mRightListView.setGroupIndicator(null);
        openGroupItem(mRightListView);
        mLeftAdapter = new LeftAdapter(this, mLeftData);
        mLeftListView.setAdapter(mLeftAdapter);
    }

    private void openGroupItem(PinnedHeaderExpandableListView mRightListView) {
        for (int i = 0; i < mRightData.size(); i++) {
            mRightListView.expandGroup(i);
        }
    }

    @Override
    public View getPinnedHeader() {
        if (null == mHeaderView) {
            mHeaderView = (ViewGroup) getLayoutInflater().inflate(R.layout.item_right_group, null);
            mHeaderView.setBackground(getDrawable(R.drawable.ripple_gray_bg));
            mHeaderView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        return mHeaderView;
    }

    @Override
    public void updatePinnedHeader(int firstVisibleGroupPos) {
        final RightBean firstVisibleGroup = (RightBean) mRightAdapter.getGroup(firstVisibleGroupPos);
        View pinnedHeader = getPinnedHeader();
        TextView textView = pinnedHeader.findViewById(R.id.right_header_text);
        textView.setText(firstVisibleGroup.getHeadString());
    }

    @Override
    public void suspendPinnedHeaderOnClick(int groupPosition) {
        MyToast.show(MainActivity.this, mRightData.get(groupPosition).getHeadString());
    }
}
