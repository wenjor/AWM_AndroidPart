package com.wenjor.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
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
    private float tv1TextSize,priceTextSize,amountTextSize;

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

        tv1TextSize =ta.getDimension(R.styleable.Goods_nameTextSize,0);
        priceTextSize = ta.getDimension(R.styleable.Goods_priceTextSize,0);
        amountTextSize = ta.getDimension(R.styleable.Goods_amountTextSize,0);

        ta.recycle();







    }


}
