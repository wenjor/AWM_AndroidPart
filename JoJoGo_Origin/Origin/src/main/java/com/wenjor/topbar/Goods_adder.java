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
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Goods_adder extends Activity {
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
        img=findViewById(R.id.imageButton);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(false);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImage1();
                    //img.setImageDrawable();
            }
        });



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

                String st ="http://nightwing.top:8080/shop/dishes/create/1";
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

                if(!imgPath.equals("")){
                    String url = "http://nightwing.top:8080/dishes/img/1";
                    FileImageUpload FIU=new FileImageUpload();
                    File file=new File(imgPath);
                    FIU.uploadFile(file,url,tokenid);
                }

                //finish();
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
