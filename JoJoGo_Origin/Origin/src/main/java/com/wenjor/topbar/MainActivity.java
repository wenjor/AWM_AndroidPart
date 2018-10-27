package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {
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

//                      Map<String,Object> map = new HashMap<String,Object>();
//                    map = gson.fromJson((String)msg.obj,map.getClass());
//                    Set<Map.Entry<String,Object>> set =map.entrySet();
//                    for(Map.Entry<String,Object> me :set){
//                        if(me.getValue() instanceof String)
//                            editor.putString(me.getKey(),(String)me.getValue());
//                        else if(me.getValue() instanceof Double)
//                            editor.putFloat(me.getKey(),(float) ((Double) me.getValue()).doubleValue());
//                    }
//                    String tokenid =(String)map.get("data");
//                    System.out.println("DDDDDDDDDDDDDDDD"+tokenid.getClass());
//                    editor.putString("data",tokenid);
//                    editor.putString("name","wenjor");
                    Log.d("TAG", "saved json is "+ json);
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

//                      Map<String,Object> map = new HashMap<String,Object>();
//                    map = gson.fromJson((String)msg.obj,map.getClass());
//                    Set<Map.Entry<String,Object>> set =map.entrySet();
//                    for(Map.Entry<String,Object> me :set){
//                        if(me.getValue() instanceof String)
//                            editor.putString(me.getKey(),(String)me.getValue());
//                        else if(me.getValue() instanceof Double)
//                            editor.putFloat(me.getKey(),(float) ((Double) me.getValue()).doubleValue());
//                    }
//                    String tokenid =(String)map.get("data");
//                    System.out.println("DDDDDDDDDDDDDDDD"+tokenid.getClass());
//                    editor.putString("data",tokenid);
//                    editor.putString("name","wenjor");
                    Log.d("TAG", "saved json is " + json);
                    editor.putString("alterGoodInf", json);
                    editor.commit();
                    break;
                default:
                    break;
                }
        }
    }
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
                Intent intent1 = new Intent(MainActivity.this,OrderManager.class);
                startActivity(intent1);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st = "http://nightwing.top:8080/shop/dishes/1";
                Map<String,Object>map = new LinkedHashMap<String, Object>();
                try{
                    httpclient = new HttpClientClass(st,"GET","JSON",map,handle2);
                }catch (Exception e){
                    e.printStackTrace();
                }
                httpclient.start();
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
                // Toast.makeText(MainActivity.this,"On building",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                String st = "http://nightwing.top:8080/shop/1?id=1";
                Map<String,Object>map = new LinkedHashMap<String, Object>();
                try{
                    httpclient = new HttpClientClass(st,"GET","JSON",map,handle);
                }catch (Exception e){
                    e.printStackTrace();
                }
                httpclient.start();
                Intent intent = new Intent(MainActivity.this,Store_Information.class);
                startActivity(intent);


            }
        });
        topbar.setLeftIsvisable(true);
        topbar.setrightIsvisable(true);
    }


}
