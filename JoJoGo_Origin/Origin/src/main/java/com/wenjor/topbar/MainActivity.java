package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Thread.sleep;

public class MainActivity extends Activity {
    private String ShopId;
    private Button bt1,bt2,bt3;
    private HttpClientClass httpclient;
    private Handler handle= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences sp = getSharedPreferences("shopInf",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson=new Gson();
                    String json = gson.toJson(msg.obj);

                    Log.d("TAG", "MainHandle saved json is "+ json);
                    editor.putString("alterShopInf",json);
                    editor.commit();
                    break;
                default:
                    break;
            }
        }
    };
    private Handler handle2= new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences sp = getSharedPreferences("GoodManager", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(msg.obj);

                    Log.d("TAG", "MainHandle2 saved json is " + json);
                    editor.putString("alterGoodInf", json);
                    editor.commit();
                    break;
                default:
                    break;
                }
        }
    };
    private Handler handle3= new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences sp = getSharedPreferences("LabelManager", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(msg.obj);
                    Log.d("TAG", "MainHandle3 saved json is " + json);
                    editor.putString("LabelManager", json);
                    editor.commit();
                    break;
                default:
                    break;
            }
        }
    };

    private Handler handle4= new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences sp = getSharedPreferences("OrderManager", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(msg.obj);
                    Log.d("TAG", "MainHandle4 saved json is " + json);
                    editor.putString("OrderManager", json);
                    editor.commit();
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bt1 = findViewById(R.id.button1);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);
        Topbar topbar = findViewById(R.id.topbar);

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得店铺id
                SharedPreferences sp = getSharedPreferences("ShopId",Activity.MODE_PRIVATE);
                String json = sp.getString("ShopId",null);
                Log.d("ShopInfGGGGGGGGGGGGG",json);
                Gson gson=new Gson(); Map<String,Object> map = new HashMap<String,Object>();
                String ins = new String();
                ins =gson.fromJson(json,ins.getClass());
                map = gson.fromJson(ins,map.getClass());
                ShopId =(new Double((double)map.get("data"))).intValue()+"";

                //获取订单
                String st = "http://nightwing.top:8080/shop/getneworders/"+ShopId;//Log.d("ShopWebGGGGGGGGGGGGG",st);
                sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
                String tokenid = sp.getString("data","");
                Log.d("authorization",tokenid);
                Map<String,Object> headers = new LinkedHashMap<String, Object>();
                headers.put("Authorization",tokenid);
                try{
                    httpclient = new HttpClientClass(st,
                            "GET",
                            "JSON",
                            null,
                            handle4, headers);
                }catch (Exception e){
                    e.printStackTrace();
                }
                httpclient.start();

                Intent intent1 = new Intent(MainActivity.this,OrderManager.class);
                startActivity(intent1);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得店铺id
                SharedPreferences sp = getSharedPreferences("ShopId",Activity.MODE_PRIVATE);
                String json = sp.getString("ShopId",null);
                Log.d("ShopInfGGGGGGGGGGGGG",json);
                Gson gson=new Gson(); Map<String,Object> map = new HashMap<String,Object>();
                String ins = new String();
                ins =gson.fromJson(json,ins.getClass());
                map = gson.fromJson(ins,map.getClass());
                ShopId =(new Double((double)map.get("data"))).intValue()+"";

             //获取商品
                String st = "http://nightwing.top:8080/shop/dishes/"+ShopId;//Log.d("ShopWebGGGGGGGGGGGGG",st);
                sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
                String tokenid = sp.getString("data","");
                Log.d("authorization",tokenid);
                Map<String,Object> headers = new LinkedHashMap<String, Object>();
                headers.put("Authorization",tokenid);
                try{
                    httpclient = new HttpClientClass(st,
                                        "GET",
                                        "JSON",
                                        null,
                                        handle2, headers);
                }catch (Exception e){
                    e.printStackTrace();
                }
                httpclient.start();

                //获取标签
                st = "http://nightwing.top:8080/shop/cate/"+ShopId;//Log.d("ShopWebGGGGGGGGGGGGG",st);
                try{
                    httpclient = new HttpClientClass(st,
                            "GET",
                            "JSON",
                            null,
                            handle3, headers);
                }catch (Exception e){
                    e.printStackTrace();
                }
                httpclient.start();

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this,Goods_manager.class);
                startActivity(intent);
            }
        });
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
//                String st = "https://nightwing.top/customer/login";
//                Map<String,Object>map = new LinkedHashMap<String, Object>();
//
//                map.put("tel","17646547957");
//                map.put("password", "B000BQ2HR2");
//
//                httpclient = new HttpClientClass(st,"POST","JSON",map,handle);
//                Toast.makeText(MainActivity.this,"On building",Toast.LENGTH_SHORT).show();
//                httpclient.start();
               Intent intent = new Intent(MainActivity.this,Login.class);
               startActivity(intent);


            }

            @Override
            public void rightClick() {
                //获得店铺id
                SharedPreferences sp = getSharedPreferences("ShopId",Activity.MODE_PRIVATE);
                String json = sp.getString("ShopId",null);
                Log.d("ShopInfGGGGGGGGGGGGG",json);
                Gson gson=new Gson(); Map<String,Object> map = new HashMap<String,Object>();
                String ins = new String();
                ins =gson.fromJson(json,ins.getClass());
                map = gson.fromJson(ins,map.getClass());
                ShopId =(new Double((double)map.get("data"))).intValue()+"";
                // Toast.makeText(MainActivity.this,"On building",Toast.LENGTH_SHORT).show();


                String st = "http://nightwing.top:8080/shop/1?id="+ShopId;
                map = new LinkedHashMap<String, Object>();
                try{
                    httpclient = new HttpClientClass(st,"GET","JSON",map,handle, null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                httpclient.start();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this,Store_Information.class);
                startActivity(intent);


            }
        });
        topbar.setLeftIsvisable(true);
        topbar.setrightIsvisable(true);






















        
    }


}
