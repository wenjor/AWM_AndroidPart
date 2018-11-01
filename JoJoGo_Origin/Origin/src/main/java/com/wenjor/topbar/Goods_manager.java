package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Goods_manager extends Activity {
    private RadioGroup radioGroup,radioGroup2;

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
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);

        int N;
        //获得商品
        SharedPreferences sp = getSharedPreferences("GoodManager",MODE_PRIVATE);
        String json = sp.getString("alterGoodInf",null);
        Log.d("GoodManagerGGGGGGGGG",json);
        Gson gson = new Gson();Map<String,Object> map =new HashMap<String, Object>();
        String ins = new String();
        ins =gson.fromJson(json,ins.getClass());
        map = gson.fromJson(ins,map.getClass());
        Map<String,Object> status =new HashMap<String, Object>();
        final List<Map<String,Object>> list= //new ArrayList<Map<String,Object>>();
                         (ArrayList<Map<String,Object>>)map.get("data");
        N= list.size();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        LinearLayout LL = findViewById(R.id.LL);


        for(int i=0;i<N;i++){
            status =list.get(i);
            String name = (String)status.get("name");
//            String price =  status.get("price");
            String price = status.get("price").toString();
            String amount = (String)status.get("amount");
            String cateId = (String)status.get("cateId").toString();
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
        //获得标签
        sp = getSharedPreferences("LabelManager",MODE_PRIVATE);
        json = sp.getString("LabelManager",null);
        Log.d("GoodManagerGGGGGGGGG",json);
         gson = new Gson();map =new HashMap<String, Object>();
         ins = new String();
        ins =gson.fromJson(json,ins.getClass());
        map = gson.fromJson(ins,map.getClass());
        status =new HashMap<String, Object>();
        List<Map<String,Object>> list2= new ArrayList<Map<String,Object>>();
        list2 = (ArrayList<Map<String,Object>>)map.get("data");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int N_L = list2.size();
        for(int i=0;i<N_L;i++){
            status = list2.get(i);
            String labelName = (String)status.get("name");
            String labelId = status.get("id").toString();
            RadioButton radioButton = new RadioButton(this);
            RadioGroup.LayoutParams RP = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
            //RP.setMargins(10, 10, 10, 10);
            radioButton.setLayoutParams(RP);
            radioButton.setText(labelName);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextColor(getResources().getColorStateList(R.color.radiobutton_textcolor));

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LL.removeAllViews();
                    for(int i=0;i<N;i++){
                        Map<String,Object> status =list.get(i);
                        String name = (String)status.get("name");
//            String price =  status.get("price");
                        String price = status.get("price").toString();
                        String amount = (String)status.get("amount");
                        String cateId = (String)status.get("cateId").toString();
                        Goods_type2 g =new Goods_type2(Goods_manager.this);
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
                        if(cateId.equals(labelId)) {
                            LL.addView(g);
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
            radioGroup2.addView(radioButton);
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
