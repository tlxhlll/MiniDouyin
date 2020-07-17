package com.MiniDouyin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.MiniDouyin.PersonDatabase.PersonDao;
import com.MiniDouyin.PersonDatabase.PersonDatabase;
import com.MiniDouyin.PersonDatabase.PersonEntity;

public class LoginActivity extends android.app.Activity {
    private Button LoginIn;
    private EditText Id;
    private EditText Name;
//    private Picture portrait; 以后可能加头像
    private static String id;
    private static String name;
    @Override
    protected  void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.activity_login);

        Id=findViewById(R.id.editText_id);
        Name=findViewById(R.id.editText_name);
        LoginIn=findViewById(R.id.btn_login);

        LoginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=Id.getText().toString();
                name=Name.getText().toString();
                new Thread() {
                    @Override
                    public void run() {
                        PersonEntity personEntity=new PersonEntity(id,name);
                        PersonDao dao= PersonDatabase.inst(getApplicationContext()).personDao();
                        //这里需要改进，对于已有的直接登陆，对于没有的加上
                        dao.addPerson(personEntity);
                    }
                }.start();
                Intent MainIntent=new Intent(LoginActivity.this,MainActivity.class);
                MainIntent.putExtra("id",id);
                MainIntent.putExtra("name",name);
                startActivity(MainIntent);
            }
        });
    }
}
