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
import android.widget.EditText;
import android.widget.Switch;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Goods_adder extends Activity {
    private EditText name,price,description;
    private Switch supply;
    private Button comfirm;
    private Handler handle= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("Goods adder Reply:",(String)msg.obj);
            switch (msg.what) {
                case 0:
                    //Log.d("Goods Edit Reply:",(String)msg.obj);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_edit);
        name=findViewById(R.id.editText100);
        price=findViewById(R.id.price);
        description=findViewById(R.id.description);
        supply=findViewById(R.id.switch1);
        comfirm=findViewById(R.id.button);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(false);


        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> gi_push = new HashMap<String, Object>();
                if(!name.getText().toString().equals(""))gi_push.put("name",name.getText().toString());
                if(!price.getText().toString().equals("")){
                    double aaa= Double.parseDouble(price.getText().toString())*100;
                    gi_push.put("price",aaa);
                }
                if(!description.getText().toString().equals(""))gi_push.put("description",description.getText().toString());
                if(supply.isSelected())gi_push.put("status",1);
                else gi_push.put("status",0);
                gi_push.put("cateId",1);

                String st ="http://nightwing.top:8080/dishes/create/1";
                SharedPreferences sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
                String tokenid = sp.getString("data","");
                Log.d("authorization",tokenid);
                Map<String,Object> headers = new LinkedHashMap<String, Object>();
                headers.put("Authorization",tokenid);
                headers.put("Content-Type","application/json");

                HttpClientClass httpclient = null;
                try {
                    httpclient = new HttpClientClass(st,"POST","JSON",
                            gi_push, handle, headers);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                httpclient.start();
                //finish();
            }
        });



    }
}
