package com.wenjor.topbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Goods_manager extends Activity {
    private RadioGroup radioGroup,radioGroup2;
    private FloatingActionButton FB;



    public static Drawable getDrawable(String urlpath){
        Drawable drawable = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            drawable = Drawable.createFromStream(in, "background.jpg");
        } catch (IOException e) {
            e.printStackTrace();
            //Log.d("DrawableProblem",urlpath);
        }
        return drawable;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        FB = findViewById(R.id.floatingActionButton);

        FB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Goods_manager.this,Goods_adder.class);
                startActivity(intent);
                finish();
            }
        });

        int N;
        //获得商品
        SharedPreferences sp = getSharedPreferences("GoodManager",MODE_PRIVATE);
        String json = sp.getString("alterGoodInf",null);
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
        Good_inf gi =new Good_inf();
        gi.name="JOJO";

        Log.d("JOJO's here",gson.toJson(gi,gi.getClass()));

        N= list.size();
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        LinearLayout LL = findViewById(R.id.LL);


        for(int i=0;i<N;i++){
            status =list.get(i);
            Good_inf GioGio= new Good_inf();
            String name = (String)status.get("name"); GioGio.name=name;
            String price = ((double)status.get("price")/100)+"0";GioGio.price=price;
            String amount = status.get("amount")+"";GioGio.status=status.get("status")+"";
            String cateId = (String)status.get("cateId").toString();GioGio.cateId = cateId;
            GioGio.description=status.get("description").toString();
            GioGio.id=status.get("id").toString();

            Goods_type2 g =new Goods_type2(this);


            String imgUrl=(String)status.get("img");
            g.setImageURL(imgUrl);

            //g.setImage(getResources().getDrawable(R.drawable.jin));
            g.setOnclickEdit(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    SharedPreferences sp = getSharedPreferences("LabelManager", Activity.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    Gson gson = new Gson();
//                    String json = gson.toJson(msg.obj);
////                      Map<String,Object> map = new HashMap<String,Object>();
////                    map = gson.fromJson((String)msg.obj,map.getClass());
////                    Set<Map.Entry<String,Object>> set =map.entrySet();
////                    for(Map.Entry<String,Object> me :set){
////                        if(me.getValue() instanceof String)
////                            editor.putString(me.getKey(),(String)me.getValue());
////                        else if(me.getValue() instanceof Double)
////                            editor.putFloat(me.getKey(),(float) ((Double) me.getValue()).doubleValue());
////                    }
////                    String tokenid =(String)map.get("data");
////                    System.out.println("DDDDDDDDDDDDDDDD"+tokenid.getClass());
////                    editor.putString("data",tokenid);
////                    editor.putString("name","wenjor");
//                    Log.d("TAG", "A singleGood saved json is " + json);
//                    editor.putString("LabelManager", json);
//                    editor.commit();
                    SharedPreferences spi = getSharedPreferences("singleGoodInf",MODE_PRIVATE);
                    SharedPreferences.Editor editor = spi.edit();
                    Gson gson_inf=new Gson();
                    String json = gson_inf.toJson(GioGio,GioGio.getClass());
                    Log.d("singleGoodInf",json);
                    editor.putString("singleGoodInf",json);
                    editor.commit();

                    Intent intent = new Intent(Goods_manager.this,Goods_edit.class);
                    startActivity(intent);
                    finish();
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
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
                        Good_inf GioGio= new Good_inf();
                        String name = (String)status.get("name"); GioGio.name=name;
                        String price = ((double)status.get("price")/100)+"0";GioGio.price=price;
                        String amount = status.get("amount")+"";GioGio.status=status.get("status")+"";
                        String cateId = (String)status.get("cateId").toString();GioGio.cateId = cateId;
                        GioGio.description=status.get("description").toString();
                        GioGio.id=status.get("id").toString();

                        Goods_type2 g =new Goods_type2(Goods_manager.this);
                        g.setImage(getResources().getDrawable(R.drawable.jin));
                        g.setOnclickEdit(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences spi = getSharedPreferences("singleGoodInf",MODE_PRIVATE);
                                SharedPreferences.Editor editor = spi.edit();
                                Gson gson_inf=new Gson();
                                String json = gson_inf.toJson(GioGio,GioGio.getClass());
                                Log.d("singleGoodInf",json);
                                editor.putString("singleGoodInf",json);
                                editor.commit();
                                Intent intent = new Intent(Goods_manager.this,Goods_edit.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        g.setGood(name,price,amount);
                        LinearLayout.LayoutParams GoodP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        if(cateId.equals(labelId)) {
                            LL.addView(g);
//                            try {
//                                sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
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
