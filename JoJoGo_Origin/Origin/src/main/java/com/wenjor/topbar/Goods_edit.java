package com.wenjor.topbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class Goods_edit extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_edit);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(false);
    }
}
