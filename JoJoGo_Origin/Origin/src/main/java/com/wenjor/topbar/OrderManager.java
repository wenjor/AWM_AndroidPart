package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderManager extends Activity {
    private Handler handle3 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
//                    SharedPreferences sp = getSharedPreferences("Order", Activity.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(msg.obj);
                    Log.d("TAG", "OrderManagerHandle3 saved json is " + json);
//                    editor.putString("LabelManager", json);
//                    editor.commit();
                    break;
                default:
                    break;
            }
        }
    };

    private Handler handle4 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences sp = getSharedPreferences("OrderGoods", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(msg.obj);
                    Log.d("TAG", "OrderManagerHandle4 saved json is " + json);
                    editor.putString("OrderGoods", json);
                    editor.commit();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HttpClientClass httpclient = null;
        setContentView(R.layout.order_manager);
        Topbar topbar = findViewById(R.id.topbar);
        //Order_m order_m1 = findViewById(R.id.order_m1);
        RadioGroup judge = (RadioGroup) findViewById(R.id.radioGroup);
        //Goods goods1 = findViewById(R.id.goods1);

        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {

            }
        });
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(false);

        int N;
        //获得订单
        SharedPreferences sp = getSharedPreferences("OrderManager", MODE_PRIVATE);
        String json = sp.getString("OrderManager", null);
        if (json == null) {
            return;
        }
        Log.d("OrderManagerGGGGGGGGG", json);
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        String ins = new String();
        ins = gson.fromJson(json, ins.getClass());
        map = gson.fromJson(ins, map.getClass());
        Map<String, Object> status = new HashMap<String, Object>();
        final List<Map<String, Object>> list = //new ArrayList<Map<String,Object>>();
                (ArrayList<Map<String, Object>>) map.get("data");

        N = list.size();
        LinearLayout LL = findViewById(R.id.LL);
        for (int i = 0; i < N; i++) {
            status = list.get(i);
            Order_m order_m = new Order_m(this);
            order_m.setTurn("" + status.get("id"));
            int intId = (int)(double)status.get("id");
            String id = intId+"";
            order_m.setText("" + status.get("userId"), "",
                    "" + status.get("address"),
                    (String) status.get("deliveryDate") + status.get("deliveryTime"),
                    "" + status.get("id"), "" + status.get("price"));

            sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
            String tokenid = sp.getString("data","");
            Log.d("authorization",tokenid);
            Map<String,Object> headers = new LinkedHashMap<String, Object>();
            headers.put("Authorization",tokenid);

            String st = "http://nightwing.top:8080/order/" + id + "/accept";
            HttpClientClass httpclient = null;
            try {
                httpclient = new HttpClientClass(st,
                            "GET",
                            "JSON",
                            null,
                            handle3, headers);
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpClientClass finalHttpclient = httpclient;
            order_m.setOnclickConfirm(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finalHttpclient.start();
                    order_m.finishButtonVisible();
                }
            });



            st = "http://nightwing.top:8080/order/" + id + "/complete";
            HttpClientClass httpclient2=null;
            try {
                httpclient2 = new HttpClientClass(st,
                        "GET",
                        "JSON",
                        null,
                        handle3, headers);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpClientClass finalHttpclient2 = httpclient2;
            order_m.setOnclickFinish(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHttpclient2.start();
                    order_m.allButtonInvisible();
                }
            });

            ///order/details/{order_id}
            st = "http://nightwing.top:8080/order/details/" + id ;
            HttpClientClass httpclient3=null;
            try {
                httpclient3 = new HttpClientClass(st,
                        "GET",
                        "JSON",
                        null,
                        handle4, headers);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpClientClass finalHttpclient3 = httpclient3;
            order_m.setOnclickDetail(v -> {
                finalHttpclient3.start();
                Intent intent = new Intent(OrderManager.this,OrderDetails.class);
                startActivity(intent);

            });

                    LL.addView(order_m);

                }



//        order_m1.setTurn("2");
//        order_m1.setOnclickDetail(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(OrderManager.this,OrderDetails.class);
//                startActivity(intent2);
////                Toast.makeText(OrderManager.this,"On building",Toast.LENGTH_SHORT).show();
//            }
//            });

            judge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton btn = (RadioButton) findViewById(checkedId);
                    Toast.makeText(getApplicationContext(), "here is" + btn.getText(), Toast.LENGTH_LONG).show();
                }
            });
            //goods1.setBt1Isvisable(false);
        }
    }

