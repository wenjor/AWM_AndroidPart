package com.wenjor.topbar;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Login extends Activity {

    private TextView uname,password;
    private Button post,forget,regist;
    private Handler handle= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                   // Map<String,Object>map = (Map<String,Object>)msg.obj;
                    Gson gson=new Gson(); Map<String,Object> map = new HashMap<String,Object>();
                    map = gson.fromJson((String)msg.obj,map.getClass());
                    String tokenid =(String)map.get("data");
                   // String a=(String)(msg.obj);String b[] = a.split("\"msg\":\"success\",\"code\":0,\"data\":\"",2);String c[] = b[1].split("\\}",2);
                    System.out.println("DDDDDDDDDDDDDDDD"+tokenid);
                    editor.putString("data",tokenid);
                    editor.putString("name","wenjor2");
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
                    SharedPreferences sp = getSharedPreferences("ShopId", Activity.MODE_PRIVATE);
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
                    Log.d("TAG", "LoginHandle2 saved json is " + json);
                    editor.putString("ShopId", json);
                    editor.commit();
                    break;
                default:
                    break;
            }
        }
    };
    private HttpClientClass httpclient;
    private String a,b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = findViewById(R.id.username);
        password = findViewById(R.id.password);
        post = findViewById(R.id.post);
        forget = findViewById(R.id.forget);
        regist = findViewById(R.id.forget2);


        a= uname.getText().toString();
        b=password.getText().toString();


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得token
                String st = "http://nightwing.top:8080/shop/login";
                Map<String,Object>map = new LinkedHashMap<String, Object>();
                map.put("tel","15725365670");
                map.put("password", "B0001FGAO4");
                try {
                    httpclient = new HttpClientClass(st,"POST","JSON",map,
                            handle, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(Login.this,"On building",Toast.LENGTH_SHORT).show();
                httpclient.start();
                //handle.handleMessage();

                //获得StoreId
                 st = "http://nightwing.top:8080/shop/getshopid";//Log.d("ShopWebGGGGGGGGGGGGG",st);
                SharedPreferences  sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
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
//                try {
//                    sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                synchronized (tokenid) {
                    httpclient.start();
                }

                 sp= getSharedPreferences("token",
                        Activity.MODE_PRIVATE);
                String data =sp.getString("data", "");
                String name = sp.getString("name","");
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA"+name);
                Toast.makeText(Login.this, "读取数据如下："+"\n"+"data：" + data ,
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
