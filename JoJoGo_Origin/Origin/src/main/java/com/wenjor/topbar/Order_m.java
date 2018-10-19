package com.wenjor.topbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Order_m extends RelativeLayout {
    private TextView uname,tel,addr,orderTime,orderNum,price,turn;
    private Button confirm;
    private ImageButton call;
    public Order_m(Context context){
        super(context);
    }

    public Order_m(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.order_for_m,this);
        uname = (TextView)findViewById(R.id.uname);
        tel = (TextView)findViewById(R.id.Tel);
        addr = (TextView)findViewById(R.id.addr);
        orderTime = (TextView)findViewById(R.id.textView6);
        orderNum = (TextView)findViewById(R.id.textView7);
        price = (TextView)findViewById(R.id.price);
        confirm = (Button)findViewById(R.id.button7);
        turn = (TextView)findViewById(R.id.turn);
        call = (ImageButton)findViewById(R.id.imageButton);

    }

    public void setTurn(String title){
        turn.setText(title);
    }
    public void setText(String suname,String stel,String saddr,String sorderTime,String sorderNum,String sprice){
        uname.setText(suname);
        tel.setText(stel);
        addr.setText(saddr);
        orderTime.setText(sorderTime );
        orderNum.setText(sorderNum);
        price.setText(sprice);
    }

    public void setOnclickConfirm(OnClickListener listener){
        confirm.setOnClickListener(listener);
    }
    
    public void setOnclickCall(OnClickListener listener){
        call.setOnClickListener(listener);
    }

//    public  void setCallIsvisable(boolean flag){
//        if(flag){
//            call.setVisibility(View.VISIBLE);
//        }else{
//            call.setVisibility(View.GONE);
//        }
//    }
}
