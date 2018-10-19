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
        Order_m order_m1 = findViewById(R.id.order_m1);
        Goods goods1 = findViewById(R.id.goods1);

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

        order_m1.setTurn("2");

        goods1.setBt1Isvisable(false);
    }
}
