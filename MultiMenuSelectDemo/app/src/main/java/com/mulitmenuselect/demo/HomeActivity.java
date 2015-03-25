package com.mulitmenuselect.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mulitmenuselect.demo.dialog.DictUnit;
import com.mulitmenuselect.demo.dialog.ThirdDialog;

/**
 * Created by yuliang.zhao on 2015/3/18.
 */
public class HomeActivity extends Activity {
    private static final int REQUEST_CODE = 100;
    private TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv1 = (TextView) findViewById(R.id.textview1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HomeActivity.this, MainActivity.class),REQUEST_CODE);
            }
        });
        tv2 = (TextView) findViewById(R.id.textview2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdDialog dialog=new ThirdDialog(HomeActivity.this);
                dialog.setonItemClickListener(new ThirdDialog.DictItemClickListener() {
                    @Override
                    public void onDictItemClick(DictUnit dictUnit) {
                        if (dictUnit!=null)
                            tv2.setText(dictUnit.name);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                if (data !=null){
                    DictUnit dictUnit= (DictUnit) data.getSerializableExtra("dict");
                    if (dictUnit!=null)
                        tv1.setText(dictUnit.name);
                }
                break;
            default:

        }
    }
}
