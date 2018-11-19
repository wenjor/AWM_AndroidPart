package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetails extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);

        int N;
        //获得商品
        SharedPreferences sp = getSharedPreferences("OrderGoods",MODE_PRIVATE);
        String json = sp.getString("OrderGoods",null);
        if (json == null) {
            return;
        }
        Log.d("GoodManagerGGGGGGGGG",json);
        Gson gson = new Gson();Map<String,Object> map =new HashMap<String, Object>();
        String ins = new String();
        ins =gson.fromJson(json,ins.getClass());
        map = gson.fromJson(ins,map.getClass());
        if (map == null || map.get("data") == null) {
            return;
        }
        Map<String,Object> status =new HashMap<String, Object>();
        final List<Map<String,Object>> list= //new ArrayList<Map<String,Object>>();
                (ArrayList<Map<String,Object>>)map.get("data");

        N= list.size();

        LinearLayout LL = findViewById(R.id.LL);


        for(int i=0;i<N;i++){
            status =list.get(i);
            ;
            String name = (String)status.get("name");
            String price = ((double)status.get("price"))+"0";
            String amount = "X"+(int)(double)status.get("num")+"";
            String cateId = status.get("cateId")+"";


            Goods_type2 g =new Goods_type2(this);


            //String imgUrl=(String)status.get("img");
            //g.setImageURL(imgUrl);
            g.removeImage();

            //g.setImage(getResources().getDrawable(R.drawable.jin));

            g.setGood(name,price,amount);
            LinearLayout.LayoutParams GoodP =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            LL.addView(g);

        }

    }
}
