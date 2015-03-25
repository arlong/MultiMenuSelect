package com.mulitmenuselect.demo.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mulitmenuselect.demo.R;
import com.mulitmenuselect.demo.adapter.DictDialogAdapter;
import com.mulitmenuselect.demo.adapter.MyPagerAdapter;
import com.mulitmenuselect.demo.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuliang.zhao on 2015/3/18.
 */
public class ThirdDialog extends MultiDialog {
    private  int mWidth;
    private MyViewPager mViewPager;
    private  LinearLayout mRootView;
    private  View view1,view2,view3;
    private ListView mListView1,mListView2,mListView3;
    private DictDialogAdapter mListView1Adapter, mListView2Adapter, mListView3Adapter;
    private List<View> views = new ArrayList<View>();
    public DictDataManager mDictDataManager = DictDataManager.getInstance();
    private DictItemClickListener mDictItemClickListener;

    public ThirdDialog(Context context) {
        super(context);
        mWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_dialog,null);
        initViews();
        setTitle("三级列表");
    }

    private void initViews() {
        mRootView = (LinearLayout) findViewById(R.id.rootview);
        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view1 = inflater.inflate(R.layout.page1, null);
        view2 = inflater.inflate(R.layout.page1, null);
        view3 = inflater.inflate(R.layout.page1, null);
        mListView1 = (ListView) view1.findViewById(R.id.listview1);
        mListView2 = (ListView) view2.findViewById(R.id.listview1);
        mListView3 = (ListView) view3.findViewById(R.id.listview1);

        List<DictUnit> list1=mDictDataManager.getTripleColumnData(mContext, 0);
        mListView1Adapter = new DictDialogAdapter(mContext, list1);
        mListView1Adapter.setSelectedBackground(R.drawable.select_white);
        mListView1Adapter.setHasDivider(false);
        mListView1Adapter.setNormalBackgroundResource(R.color.dictdialog_bg_gray);
        mListView1.setAdapter(mListView1Adapter);

        views.add(view1);
        views.add(view2);
        mViewPager.setAdapter(new MyPagerAdapter(views));
        mRootView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListView1Adapter != null)
                    mListView1Adapter.setSelectedItem(position);
                if (mListView2Adapter != null)
                    mListView2Adapter.setSelectedItem(-1);

                if (views.contains(view3)) {
                    views.remove(view3);
                    mViewPager.getAdapter().notifyDataSetChanged();
                }
                DictUnit dictUnit = (DictUnit) parent.getItemAtPosition(position);
                if (dictUnit.id == 0) {//不限
                    if (mListView2Adapter != null) {
                        mListView2Adapter.setData(new ArrayList<DictUnit>());
                        mListView2Adapter.notifyDataSetChanged();
                    }
                    setDictItemClickListener(dictUnit);
                } else {
                    List<DictUnit> list2 = mDictDataManager.getTripleColumnData(mContext, dictUnit.id);
                    if (mListView2Adapter == null) {
                        mListView2Adapter = new DictDialogAdapter(mContext, list2);
                        mListView2Adapter.setNormalBackgroundResource(R.color.white);
                        mListView2.setAdapter(mListView2Adapter);
                    } else {
                        mListView2Adapter.setData(list2);
                        mListView2Adapter.notifyDataSetChanged();
                    }

                }
            }
        });
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListView2Adapter != null) {
                    mListView2Adapter.setSelectedItem(position);
                    mListView2Adapter.setSelectedBackground(R.drawable.select_gray);
                }

                if (views.contains(view3)) {
                    views.remove(view3);
                }

                DictUnit dictUnit = (DictUnit) parent.getItemAtPosition(position);
                List<DictUnit> list3 = mDictDataManager.getTripleColumnData(mContext, dictUnit.id);
                if (mListView3Adapter == null) {
                    mListView3Adapter = new DictDialogAdapter(mContext, list3);
                    mListView3Adapter.setHasDivider(false);
                    mListView3Adapter.setNormalBackgroundResource(R.color.dictdialog_bg_gray);
                    mListView3.setAdapter(mListView3Adapter);
                } else {
                    mListView3Adapter.setData(list3);
                    mListView3Adapter.notifyDataSetChanged();
                }

                views.add(view3);
                mViewPager.getAdapter().notifyDataSetChanged();

                if (mViewPager.getCurrentItem() != 1) {
                    mViewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(1);
                        }
                    }, 300);
                }
            }
        });
        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DictUnit dictUnit = (DictUnit) parent.getItemAtPosition(position);
                setDictItemClickListener(dictUnit);
            }
        });
    }

    private void setDictItemClickListener(DictUnit dictUnit) {
        if (mDictItemClickListener != null) {
            mDictItemClickListener.onDictItemClick(dictUnit);
        }
        dismiss();
    }

    public final void setonItemClickListener(DictItemClickListener listener) {
        mDictItemClickListener = listener;
    }

    public interface DictItemClickListener {
        public void onDictItemClick(DictUnit dictUnit);
    }
}
