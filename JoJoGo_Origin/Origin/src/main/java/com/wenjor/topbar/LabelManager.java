package com.wenjor.topbar;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LabelManager extends Activity {
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("deleteLabel:",(String)msg.obj);
            switch (msg.what) {
                case 0:
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.label_manager);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(false);
        LinearLayout LL=findViewById(R.id.LL);

        SharedPreferences sp = getSharedPreferences("LabelManager",MODE_PRIVATE);
        String json = sp.getString("LabelManager",null);
        Log.d("GoodManagerGGGGGGGGG",json);
        Gson gson = new Gson();Map<String,Object> map =new HashMap<String, Object>();
        String ins = new String();
        ins =gson.fromJson(json,ins.getClass());
        map = gson.fromJson(ins,map.getClass());
        Map<String,Object> status =new HashMap<String, Object>();
        List<Map<String,Object>> list2= new ArrayList<Map<String,Object>>();
        list2 = (ArrayList<Map<String,Object>>)map.get("data");
        int N_L = list2.size();
        for(int i=0;i<N_L;i++) {
            status = list2.get(i);
            String labelName = (String) status.get("name");
            int labelId = (int)(double)status.get("id");
            Label label = new Label(this);
            label.setTv(labelName);
            label.delete(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String st = "http://nightwing.top:8080/shop/cate/delete/"+labelId;//Log.d("ShopWebGGGGGGGGGGGGG",st);
                    SharedPreferences sp = getSharedPreferences("token",Activity.MODE_PRIVATE);
                    String tokenid = sp.getString("data","");
                    Log.d("authorization",tokenid);
                    Map<String,Object> headers = new LinkedHashMap<String, Object>();
                    headers.put("Authorization",tokenid);

                    HttpClientClass httpclient = null;
                    try {
                        httpclient = new HttpClientClass(st,
                                "GET",
                                "JSON",
                                null,
                                handler, headers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    httpclient.start();

                }
            });
            LL.addView(label);
        }
    }
}
