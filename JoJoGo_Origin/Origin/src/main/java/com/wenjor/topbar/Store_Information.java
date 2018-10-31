package com.wenjor.topbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store_Information extends Activity {
    private TextView userId,shopname,addr,createdAt;
    private ImageButton img;
    private Button confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_information);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(true);
        topbar.setrightIsvisable(false);
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {

            }
        });

        SharedPreferences sp = getSharedPreferences("shopInf",MODE_PRIVATE);
        String json = sp.getString("alterShopInf",null);
        Log.d("TagGGGGGGGGGGGGG",json);
        Gson gson=new Gson(); Map<String,Object> map = new HashMap<String,Object>();
        String ins = new String();
        ins =gson.fromJson(json,ins.getClass());
        map = gson.fromJson(ins,map.getClass());
        Map<String,Object> status =new HashMap<String, Object>();
        List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
        list = (ArrayList<Map<String,Object>>)map.get("data");
        status = list.get(0);

        shopname =findViewById(R.id.shopName);
        addr =findViewById(R.id.addr);
        createdAt = findViewById(R.id.createdAt);
        confirm = findViewById(R.id.confirm);
        img = findViewById(R.id.imageButton2);

        shopname.setText((String)status.get("name"));
        addr.setText((String)status.get("address"));
        createdAt.setText((String)status.get("createdAt"));
        img.setImageResource(R.mipmap.activity);



//        if(json!=null){
//            Gson gson  =new Gson();
//            Type type= new TypeToken<List<CoordinateAlterSample>>(){}.getType();
//            List<CoordinateAlterSample> alterSamples = new ArrayList<CoordinateAlterSample>();
//            alterSamples = gson.fromJson(json, Object);
//            for(int i = 0; i < alterSamples.size(); i++)
//            {
//                Log.d("TAG", alterSamples.get(i).getName()+":" + alterSamples.get(i).getX() + "," + alterSamples.get(i).getY());
//            }
//        }


    }
}
