package com.mulitmenuselect.demo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by yuliang.zhao on 2015/3/5.
 */
public class MyViewPager extends ViewPager {

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    

    @Override
        public void scrollTo(int x, int y) {
         if (getAdapter()==null || x>getWidth()*(getAdapter().getCount()-2)){
            return;
        }
        super.scrollTo(x, y);
    }
}
