package com.wenjor.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

    public class Topbar extends RelativeLayout{
    private Button leftButton,rightButton;
    private TextView tvTitle;

    private int leftTextColor,rightTextColor;
    private Drawable leftBackground,rightBackground;
    private String leftText,rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String title;

    private LayoutParams leftParams,rightParams,titleParams;

    private topbarClickListener listener;

    public interface topbarClickListener{
        public void leftClick();
        public void rightClick();
    }

    public void setOnTopbarClickListener(topbarClickListener listener){
        this.listener = listener;
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Topbar);//引入属性
        leftTextColor= ta.getColor(R.styleable.Topbar_leftTextColor,0);
        rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor,0);
        leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);
        rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);
        leftText = ta.getString(R.styleable.Topbar_leftText);
        rightText =  ta.getString(R.styleable.Topbar_rightText);

        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor,0);
        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize,0);
        title = ta.getString(R.styleable.Topbar_title);

        ta.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);
        leftButton.setText(leftText);
        leftButton.setId(33);

        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);
        rightButton.setText(rightText);
        rightButton.setId(44);

        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setId(55);

        setBackgroundColor(Color.rgb(102,178,225));

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(leftButton,leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(rightButton,rightParams);

//            rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            rightParams.addRule(RelativeLayout.RIGHT_OF,33);
//            addView(rightButton,rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(tvTitle,titleParams);

//        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        titleParams.addRule(RelativeLayout.LEFT_OF,33);
//                addView(tvTitle,titleParams);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
                //Toast.makeText(context,"Haven't been down",Toast.LENGTH_SHORT).show();
            }
        });

        rightButton.setOnClickListener((new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        }));

    }
    public  void setLeftIsvisable(boolean flag){
        if(flag){
            leftButton.setVisibility(View.VISIBLE);
        }else{
            leftButton.setVisibility(View.GONE);
        }
    }
    public  void setrightIsvisable(boolean flag){
        if(flag){
            rightButton.setVisibility(View.VISIBLE);
        }else{
            rightButton.setVisibility(View.GONE);
        }
    }
}
