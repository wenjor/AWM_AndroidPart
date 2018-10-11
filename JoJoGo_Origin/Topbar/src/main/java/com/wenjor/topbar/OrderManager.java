package com.wenjor.topbar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class OrderManager extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_manager);
        Topbar topbar = findViewById(R.id.topbar);
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {

            }
        });
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(false);
    }
}
