package com.love.lixinxin.ico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @ViewInject(R.id.btn1)
    private Button btn1;
    @ViewInject(R.id.btn2)
    private Button btn2;
    @ViewInject(R.id.btn3)
    private Button btn3;

    @ViewInject(R.id.text1)
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        btn1.setText("注入成功1");
        btn2.setText("注入成功2");
        btn3.setText("注入成功3");

        textView1.setText("注入成功------------");

    }

    @OnClick(R.id.btn1)
    public void showText(View view) {

        textView1.setText("onClick");
        Toast.makeText(this, "你好 框架", Toast.LENGTH_LONG).show();

    }

}
