package com.wenjor.topbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.LinkedHashMap;
import java.util.Map;

public class Login extends Activity {
    private TextView uname,password;
    private Button post;
    private Handler handle;
    private HttpClientClass httpclient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = findViewById(R.id.username);
        password = findViewById(R.id.password);
        post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st = "https://nightwing.top/shop/login";
                Map<String,Object>map = new LinkedHashMap<String, Object>();

                map.put("tel","17646547957");
                map.put("password", "B000BQ2HR2");

                try {
                    httpclient = new HttpClientClass(st,"POST","JSON",map,handle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(Login.this,"On building",Toast.LENGTH_SHORT).show();
                httpclient.start();
            }
        });
    }


}
