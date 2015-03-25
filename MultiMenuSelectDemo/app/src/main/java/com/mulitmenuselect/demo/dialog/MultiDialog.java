package com.mulitmenuselect.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mulitmenuselect.demo.R;

/**
 * Created by yuliang.zhao on 2015/3/18.
 */
public class MultiDialog extends Dialog {

    public Context mContext;
    public LinearLayout containerViewGroup;
    public View mContentView;
    public TextView titleView;
    Window window = null;

    public MultiDialog(Context context) {
        super(context, R.style.dialog_change_card);
        mContext = context;
        containerViewGroup = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_dictdialog_container, null);
        titleView = (TextView) containerViewGroup.findViewById(R.id.dictdialog_title_tv);
    }

    public View findViewById(int id) {
        return mContentView.findViewById(id);
    }

    /**
     * 设置窗口显示
     */
    public void windowDeploy() {
        window = getWindow(); // 得到对话框
        window.setWindowAnimations(R.style.RegDialogAnimation); // 设置窗口弹出动画
        // window.setBackgroundDrawableResource(R.drawable.bg_picker_view);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0; // x小于0左移，大于0右移
        wl.y = 0; // y小于0上移，大于0下移
//		wl.height = 2*DajieApp.mScreenHeight/3;
        wl.height = 2 * mContext.getResources().getDisplayMetrics().heightPixels / 3;
        wl.width = LinearLayout.LayoutParams.FILL_PARENT;
        // wl.alpha = 0.6f; //设置透明度
        wl.gravity = Gravity.BOTTOM; // 设置重力
        window.setAttributes(wl);
    }

    @Override
    public void show() {
        if (mContentView != null) {
            containerViewGroup.addView(mContentView);
        }
        setContentView(containerViewGroup);
        setCanceledOnTouchOutside(true);
        windowDeploy();
        super.show();
    }

    @Override
    public void setTitle(CharSequence title) {
        // TODO Auto-generated method stub
        if (titleView != null)
            titleView.setText(title);
    }
}

