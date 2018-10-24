package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class Goods_manager extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_manager);
        Topbar topbar =findViewById(R.id.topbar);
        topbar.setLeftIsvisable(false);
        topbar.setrightIsvisable(true);
        topbar.setOnTopbarClickListener(new Topbar.topbarClickListener() {
            @Override
            public void leftClick() {

            }

            @Override
            public void rightClick() {
                Intent intent = new Intent(Goods_manager.this,LabelManager.class);
                startActivity(intent);
            }
        });
        Goods_type2 good = findViewById(R.id.goods1);

        good.setOnclickEdit(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Goods_manager.this,Goods_edit.class);
                startActivity(intent);
            }
        });
        good.setImage(getResources().getDrawable(R.drawable.jin));

    }
}
