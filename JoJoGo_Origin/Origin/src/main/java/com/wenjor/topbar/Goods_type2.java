package com.wenjor.topbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Goods_type2 extends RelativeLayout {
    private TextView name,price,amount;
    private Button bt1,bt2;
    private ImageView image;
    public Goods_type2(Context context){//, AttributeSet attrs){
        super(context);//,attrs);
        LayoutInflater.from(context).inflate(R.layout.goods_type2,this);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        amount = findViewById(R.id.amount);
        bt1 = findViewById(R.id.button1);
        bt2 = findViewById(R.id.button2);
        image =findViewById(R.id.imageView2);
    }



    public void setGood(String n,String p,String a){
        name.setText(n);
        price.setText(p);
        amount.setText(a);
    }
    public  void setImage(Drawable d){
        image.setImageDrawable(d);
    }
    public void setOnclickPull(OnClickListener listener){bt1.setOnClickListener(listener);}
    public void setOnclickEdit(OnClickListener listener){bt2.setOnClickListener(listener);}
}
