package com.wenjor.intentchangetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FActivity extends Activity {
    private Button bt1,bt2;private TextView tx;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        bt1 = findViewById(R.id.button);
        bt2 = findViewById(R.id.button2);
        tx = findViewById(R.id.DataReback);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FActivity.this,SActivity.class);
                startActivity(intent1);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FActivity.this,SActivity.class);
                startActivityForResult(intent1,1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            String content = data.getStringExtra("data");
            tx.setText(content);
        }
    }
}
