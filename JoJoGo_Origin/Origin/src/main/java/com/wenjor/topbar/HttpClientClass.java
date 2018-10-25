package com.wenjor.topbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class HttpClientClass extends Thread {
    private String requestStyle;
    private String dataStyle;
    private String url;
    String result = null;
    private Handler handle;
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    URL realUrl;

    /**
     * @author gw00093437 dhj 2017.1.10
     * @param url
     *            接口地址
     * @param requestStyle
     *            请求类型GET\POST
     * @param dataStyle
     *            数据提交方式FORM\JSON
     * @param map
     *            数据源供转化解析
     * @param handle
     * 返回数据的存储
     */
    public HttpClientClass(String url, String requestStyle, String dataStyle,
                           Map map,Handler handle) throws Exception {
        this.requestStyle = requestStyle;
        this.dataStyle = dataStyle;
        this.url = url;
        try {
            this.realUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if("https".equalsIgnoreCase(realUrl.getProtocol())){
            com.wenjor.topbar.SslUtils.ignoreSsl();}
        this.map = map;
        this.handle=handle;
    }


    @Override
    public void run() {
// 创建httpclient对象
        HttpClient httpclient = new DefaultHttpClient();
// 首先判断请求类型
        if (requestStyle.equals("GET")) {
// GET方式
            String param="";
            Iterator it = map.keySet().iterator();
            String key;
            String value;
            HttpGet get;
            while (it.hasNext()) {
                key = it.next().toString();
                value = (String) map.get(key);
                if(param==""){
                    param=key+"="+value;
                }else{
                    param=param+"&"+key+"="+value;
                }

            }
            BufferedReader in = null;
            String content=null;
            if(param==""){
                get = new HttpGet(url);
            }else{
                get = new HttpGet(url+"?"+param);
            }

            try {
                HttpResponse response=httpclient.execute(get);
                in = new BufferedReader(new InputStreamReader(response.getEntity()  
                                           .getContent()));  
           StringBuffer sb = new StringBuffer("");  
           String line = "";  
           String NL = System.getProperty("line.separator");  
           while ((line = in.readLine()) != null) {  
               sb.append(line + NL);  
           }  
           in.close();  
           content = sb.toString();
                Message msg = new Message();
                    msg.obj= content;
                    handle.sendMessage(msg);
            } catch (ClientProtocolException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
// POST方式
            HttpPost post = new HttpPost(url);
// 判断数据提交类型
            if (dataStyle.equals("JSON")) {
// json数据类型进行提交
                JSONObject jsonParam = new JSONObject();
                Iterator it = map.keySet().iterator();
                String key;
                String value;
                while (it.hasNext()) {
                    key = it.next().toString();
                    value = (String) map.get(key);
                    try {
                        jsonParam.put(key, value);
                    } catch (JSONException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                StringEntity jsonentity = null;
                try {
                    jsonentity = new StringEntity(jsonParam.toString(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
// TODO Auto-generated catch block
                    e1.printStackTrace();System.out.println("TTTTTTTTTTTTTTTTTTTT");
                }// 解决中文乱码问题
                jsonentity.setContentEncoding("UTF-8");
                jsonentity.setContentType("application/json");
                post.setEntity(jsonentity);
                HttpResponse response = null;
                try {
                    response = httpclient.execute(post);
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                }
                //System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                //if(response!=null)
                    if (response.getStatusLine().getStatusCode() == 200) {
// 第五步：从相应对象当中取出数据，放到entity当中
                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    HttpEntity entity = response.getEntity();
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(
                                new InputStreamReader(entity.getContent()));
                    } catch (IllegalStateException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        result = reader.readLine();
                        System.out.println("Here is result"+result);
                        Message msg = new Message();
                        msg.obj= result;
                        System.out.println("Here is Message"+msg.obj);
                        handle.sendMessage(msg);
                    } catch (IOException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                    }
                    Log.d("dhj", "POST_JSON:" + result);
                }


            } else {
// form表单形式的数据进行提交 解析map数据
                int size = map.size();
// 利用KeySet 迭代
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Iterator it = map.keySet().iterator();
                String key;
                String value;
                while (it.hasNext()) {
                    key = it.next().toString();
                    value = (String) map.get(key);
                    params.add(new BasicNameValuePair(key, value));
                }
                HttpEntity requestEntity = null;
                try {
                    requestEntity = new UrlEncodedFormEntity(params);
                } catch (UnsupportedEncodingException e1) {
// TODO Auto-generated catch block
                    e1.printStackTrace();
                } // 将请求体放置到请求对象中
                post.setEntity(requestEntity); // 执行请求对象 form表单的形式/
                HttpResponse response = null;
                try {
                    response = httpclient.execute(post);
                } catch (IOException e) {
// TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (response.getStatusLine().getStatusCode() == 200) {
// 第五步：从相应对象当中取出数据，放到entity当中
                    HttpEntity entity = response.getEntity();
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(
                                new InputStreamReader(entity.getContent()));
                    } catch (IllegalStateException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        result = reader.readLine();
 Message msg = new Message();
                        msg.obj= result;
                        handle.sendMessage(msg);
                    } catch (IOException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.d("dhj", "POST_Form:" + result);
                }
            }


        }
        super.run();
    }
}
