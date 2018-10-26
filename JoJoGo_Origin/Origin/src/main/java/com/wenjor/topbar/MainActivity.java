package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private Button bt1,bt2,bt3;
    private HttpClientClass httpclient;
    private Handler handle= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    SharedPreferences sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    // Map<String,Object>map = (Map<String,Object>)msg.obj;
                    String a=(String)(msg.obj);String b[] = a.split("\"msg\":\"success\",\"code\":0,\"data\":\"",2);String c[] = b[1].split("\\}",2);
                    System.out.println("DDDDDDDDDDDDDDDD"+c[0]);
                    editor.putString("data",c[0]);
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
                Intent intent1 = new Intent(MainActivity.this,OrderManager.class);
                startActivity(intent1);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(MainActivity.this,Store_Information.class);
                startActivity(intent);


            }
        });
        topbar.setLeftIsvisable(true);
        topbar.setrightIsvisable(true);
    }


}
