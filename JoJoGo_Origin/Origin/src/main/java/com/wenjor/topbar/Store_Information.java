package com.wenjor.topbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class Store_Information extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_information);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(false);
    }
}
