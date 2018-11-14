package com.wenjor.topbar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
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
    private ImageButton img;
    private String imgPath="";
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
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
        img=findViewById(R.id.imageButton);
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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage1();
            }
        });
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
                if(!imgPath.equals("")){
                    String url = "http://nightwing.top:8080/dishes/img/";
                    FileImageUpload FIU=new FileImageUpload();
                    File file=new File(imgPath);
                    FIU.uploadFile(file,url+ins,tokenid);
                }
                finish();
            }
        });



    }

    private void setImage1() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm = null;

        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

        ContentResolver resolver = getContentResolver();

        if (requestCode == IMAGE_CODE) {

            try {

                Uri originalUri = data.getData(); // 获得图片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                img.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 300, 300));  //使用系统的一个工具类，参数列表为 Bitmap Width,Height  这里使用压缩后显示，否则在华为手机上ImageView 没有显示
                // 显得到bitmap图片
                // imageView.setImageBitmap(bm);

                String[] proj = { MediaStore.Images.Media.DATA };

                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                imgPath=path;
                // tv.setText(path);
            } catch (IOException e) {
                Log.e("TAG-->Error", e.toString());

            }

            finally {
                return;
            }
        }
    }
}
