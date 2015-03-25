package com.mulitmenuselect.demo.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;

import com.mulitmenuselect.demo.widget.MyViewPager;

import java.util.List;

/**
 * Created by yuliang.zhao on 2015/3/18.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> viewList;

    public MyPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (viewList != null) {
            return viewList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return (arg0 == arg1);
    }

    @Override
    public Object instantiateItem(View container, int position) {
        // TODO Auto-generated method stub
        ((MyViewPager) container).addView(viewList.get(position), 0);

        return viewList.get(position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        // TODO Auto-generated method stub
        ((MyViewPager) container).removeView(viewList.get(position));
    }

    @Override
    public float getPageWidth(int position) {
        Log.d("d","pagewidth = "+super.getPageWidth(position));
        return 0.5f;
    }
}
