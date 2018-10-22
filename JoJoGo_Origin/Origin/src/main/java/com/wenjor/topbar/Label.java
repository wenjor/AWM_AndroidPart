package com.wenjor.topbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Label extends RelativeLayout {

    private ImageButton bt;
    private TextView tv;

    public Label(Context context){super(context);}
    public Label(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.label,this);
        bt=(ImageButton)findViewById(R.id.bt1);
        tv = (TextView)findViewById(R.id.textView);
    }

    public void setTv(String tv1){
        tv.setText(tv1);
    }
    public void delete(OnClickListener listener){
        bt.setOnClickListener(listener);
    }
}
