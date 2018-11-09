package com.wenjor.topbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Thread.sleep;

public class Goods_edit extends Activity {

    private EditText name,price,description;
    private Switch supply;
    private Button comfirm;
    private Handler handle= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("Goods Edit Reply:",(String)msg.obj);
            switch (msg.what) {
                case 0:
                    Log.d("Goods Edit Reply:",(String)msg.obj);
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

        SharedPreferences sp = getSharedPreferences("singleGoodInf", Activity.MODE_PRIVATE);
        String json = sp.getString("singleGoodInf",null);
        Gson gson = new Gson();
        Good_inf gi = gson.fromJson(json,Good_inf.class);

        name.setHint(gi.name);
        price.setHint(gi.price);
        description.setHint(gi.description);
        if(gi.status.equals("1.0")){
            supply.setChecked(true);
        }else supply.setChecked(false);

//
//        Good_inf gi_push=new Good_inf();
//        if(name.getText()!=null)gi_push.name=name.getText().toString();
//        else gi_push.name=gi.name;
//        if(price.getText()!=null)gi_push.price=price.getText().toString();
//        else gi_push.price=gi.price;
//        if(description.getText()!=null)gi_push.description=description.getText().toString();
//        else gi_push.description=gi.description;
//
//        if(supply.isSelected())gi_push.status="1";
//        else gi_push.status="0";
//
//        json = gson.toJson(gi_push,Good_inf.class);
//        Log.d("Edit Good : ",json);
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> gi_push = new HashMap<String, Object>();
                if(!name.getText().toString().equals(""))gi_push.put("name",name.getText().toString());
                if(!price.getText().toString().equals("")){

                    gi_push.put("price", price.getText().toString());
                }
                if(!description.getText().toString().equals(""))gi_push.put("description",description.getText().toString());
                if(supply.isSelected())gi_push.put("status","1.0");
                else gi_push.put("status","0");

                int ins = (int)Double.parseDouble(gi.id);
                String st ="http://nightwing.top:8080/dishes/update/"+ins;
                SharedPreferences sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
                String tokenid = sp.getString("data","");
                Log.d("authorization",tokenid);
                Map<String,Object> headers = new LinkedHashMap<String, Object>();
                headers.put("Authorization",tokenid);

                HttpClientClass httpclient = null;
                try {
                    httpclient = new HttpClientClass(st,"POST","JSON",
                            gi_push, handle, headers);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                httpclient.start();
                finish();
            }
        });



    }
}
