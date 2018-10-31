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
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Goods_manager extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_manager);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(true);
        topbar.setrightIsvisable(true);
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {
                Intent intent = new Intent(Goods_manager.this,LabelManager.class);
                startActivity(intent);
            }
        });

        int N;
        SharedPreferences sp = getSharedPreferences("GoodManager",MODE_PRIVATE);
        String json = sp.getString("alterGoodInf",null);
        Log.d("GoodManagerGGGGGGGGG",json);
        Gson gson = new Gson();Map<String,Object> map =new HashMap<String, Object>();
        String ins = new String();
        ins =gson.fromJson(json,ins.getClass());
        map = gson.fromJson(ins,map.getClass());
        Map<String,Object> status =new HashMap<String, Object>();
        List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
        list = (ArrayList<Map<String,Object>>)map.get("data");

        N= list.size();

        LinearLayout LL = findViewById(R.id.LL);


        for(int i=0;i<N;i++){
            status =list.get(i);
            String name = (String)status.get("name");
//            String price =  status.get("price");
            String price = status.get("price").toString();
            String amount = (String)status.get("amount");
            Goods_type2 g =new Goods_type2(this);
            g.setImage(getResources().getDrawable(R.drawable.jin));
            g.setOnclickEdit(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Goods_manager.this,Goods_edit.class);
                    startActivity(intent);
                }
            });
            g.setGood(name,price,amount);
            LinearLayout.LayoutParams GoodP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            LL.addView(g);

        }

//        Goods_type2 good = findViewById(R.id.goods1);
//
//        good.setOnclickEdit(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Goods_manager.this,Goods_edit.class);
//                startActivity(intent);
//            }
//        });
//        good.setImage(getResources().getDrawable(R.drawable.jin));

    }

}
