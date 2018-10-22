package com.wenjor.topbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OrderManager extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_manager);
        Topbar topbar = findViewById(R.id.topbar);
        Order_m order_m1 = findViewById(R.id.order_m1);
        RadioGroup judge = (RadioGroup)findViewById(R.id.radioGroup);
        //Goods goods1 = findViewById(R.id.goods1);

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
        order_m1.setOnclickDetail(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(OrderManager.this,OrderDetails.class);
                startActivity(intent2);
//                Toast.makeText(OrderManager.this,"On building",Toast.LENGTH_SHORT).show();
            }
        });

        judge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = (RadioButton)findViewById(checkedId);
                Toast.makeText(getApplicationContext(),"here is"+btn.getText(),Toast.LENGTH_LONG).show();
            }
        });
        //goods1.setBt1Isvisable(false);
    }
}
