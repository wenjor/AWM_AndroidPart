package com.wenjor.topbar;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
                    editor.putString("name","wenjor");
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
                String st = "http://nightwing.top:8080/shop/login";
                Map<String,Object>map = new LinkedHashMap<String, Object>();
                map.put("tel","15725365670");
                map.put("password", "B0001FGAO4");
                try {
                    httpclient = new HttpClientClass(st,"POST","JSON",map,handle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(Login.this,"On building",Toast.LENGTH_SHORT).show();
                httpclient.start();
                //handle.handleMessage();
                SharedPreferences sp= getSharedPreferences("token",
                        Activity.MODE_PRIVATE);
                String data =sp.getString("data", "");
                String name = sp.getString("name","");
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA"+name);
                Toast.makeText(Login.this, "读取数据如下："+"\n"+"data：" + data ,Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
