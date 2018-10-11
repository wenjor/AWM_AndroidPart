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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

public class Goods extends RelativeLayout {
    private Button bt1;//confirm
    private ImageButton ibt1;
    private TextView tv1,price,amount;//name,price,amount
    private LayoutParams btP,ibtP,tvP,priceP,amountP;

    private String bt1Text,tv1Text,priceText,amountText;
    private int bt1TextColor,tv1TextColor,priceTextColor,amountTextColor;
    private Drawable bt1Background,image;
    private float bt1TextSize,tv1TextSize,priceTextSize,amountTextSize;

    private goodsClickListener listener;

    public interface goodsClickListener{
        public void Click_1();
    }

    public void setOnGoodsClickListener(goodsClickListener listener){
        this.listener = listener;
    }

    public Goods(Context context, AttributeSet attrs){
        super(context,attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.Goods);
        bt1Text = ta.getString(R.styleable.Goods_bt1);
        tv1Text = ta.getString(R.styleable.Goods_name);
        priceText = ta.getString(R.styleable.Goods_price);
        amountText = ta.getString(R.styleable.Goods_amount);

        bt1TextColor = ta.getColor(R.styleable.Goods_bt1TextColor,0);
        tv1TextColor = ta.getColor(R.styleable.Goods_nameTextColor,0);
        priceTextColor = ta.getColor(R.styleable.Goods_priceTextColor,0);
        amountTextColor = ta.getColor(R.styleable.Goods_amountTextColor,0);

        bt1Background = ta.getDrawable(R.styleable.Goods_bt1Background);
        image = ta.getDrawable(R.styleable.Goods_image);

        bt1TextSize = ta.getDimension(R.styleable.Goods_bt1TextSize,0);
        tv1TextSize =ta.getDimension(R.styleable.Goods_nameTextSize,0);
        priceTextSize = ta.getDimension(R.styleable.Goods_priceTextSize,0);
        amountTextSize = ta.getDimension(R.styleable.Goods_amountTextSize,0);

        ta.recycle();

        bt1 = new Button(context);
        ibt1 = new ImageButton(context);
        tv1 = new TextView(context);
        price = new TextView(context);
        amount = new TextView(context);

        bt1.setText(bt1Text);
        bt1.setTextSize(bt1TextSize);
        bt1.setTextColor(bt1TextColor);
        bt1.setBackground(bt1Background);
        bt1.setId(1);
        
        ibt1.setBackground(image);
        ibt1.setId(2);
        
        tv1.setText(tv1Text);
        tv1.setTextColor(tv1TextColor);
        tv1.setTextSize(tv1TextSize);
        tv1.setGravity(Gravity.LEFT);
        tv1.setId(3);

        price.setText(priceText);
        price.setTextColor(priceTextColor);
        price.setTextSize(priceTextSize);
        price.setGravity(Gravity.LEFT);
        price.setId(4);

        amount.setText(amountText);
        amount.setTextColor(amountTextColor);
        amount.setTextSize(amountTextSize);
        amount.setGravity(Gravity.LEFT);
        amount.setId(5);

        setBackgroundColor(Color.WHITE);

        btP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(bt1,btP);

        ibtP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ibtP.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(ibt1,ibtP);

        tvP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvP.addRule(RelativeLayout.RIGHT_OF,2);
        tvP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(tv1,tvP);

        priceP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        priceP.addRule(RIGHT_OF,2);
        priceP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(price,priceP);

        amountP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        amountP.addRule(RIGHT_OF,4);
        amountP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(amount,amountP);

        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Click_1();
            }
        });








    }
    public void setBt1Isvisable(boolean flag){
        if(flag){
            bt1.setVisibility(View.VISIBLE);
        }else{
            bt1.setVisibility(View.GONE);
        }
    }

    public void setAmountIsvisable(boolean flag){
        if(flag)amount.setVisibility(View.VISIBLE);
        else amount.setVisibility(View.GONE);
    }

}
