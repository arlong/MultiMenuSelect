package com.mulitmenuselect.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mulitmenuselect.demo.adapter.DictDialogAdapter;
import com.mulitmenuselect.demo.adapter.MyPagerAdapter;
import com.mulitmenuselect.demo.dialog.DictDataManager;
import com.mulitmenuselect.demo.dialog.DictUnit;
import com.mulitmenuselect.demo.widget.MyViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private Context mContext;
    public DictDataManager mDictDataManager = DictDataManager.getInstance();
    private MyViewPager mViewPager;
    private  View view1,view2,view3;
    private ListView mListView1,mListView2,mListView3;
    private DictDialogAdapter mListView1Adapter, mListView2Adapter, mListView3Adapter;
    private List<View> views = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initViews();
    }

    private void initViews() {
        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        LayoutInflater inflater = LayoutInflater.from(this);
        view1 = inflater.inflate(R.layout.page1, null);
        view2 = inflater.inflate(R.layout.page1, null);
        view3 = inflater.inflate(R.layout.page1, null);
        mListView1 = (ListView) view1.findViewById(R.id.listview1);
        mListView2 = (ListView) view2.findViewById(R.id.listview1);
        mListView3 = (ListView) view3.findViewById(R.id.listview1);

        List<DictUnit> list1=mDictDataManager.getTripleColumnData(this, 0);
        mListView1Adapter = new DictDialogAdapter(this, list1);
        mListView1Adapter.setSelectedBackground(R.drawable.select_white);
        mListView1Adapter.setHasDivider(false);
        mListView1Adapter.setNormalBackgroundResource(R.color.dictdialog_bg_gray);
        mListView1.setAdapter(mListView1Adapter);

        views.add(view1);
        views.add(view2);
        mViewPager.setAdapter(new MyPagerAdapter(views));

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

                    setResultDate(dictUnit);
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


//                    mRootView.invalidate();
                }

            }
        });
        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
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
                mViewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewPager.setCurrentItem(views.size()-1);
                    }
                }, 300);
            }
        });
        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DictUnit dictUnit = (DictUnit) parent.getItemAtPosition(position);
                setResultDate(dictUnit);
            }
        });
    }

    private void setResultDate(DictUnit dictUnit){
        Intent intent=new Intent();
        intent.putExtra("dict",(Serializable)dictUnit);
        setResult(0, intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"settings",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
