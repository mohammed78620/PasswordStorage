package com.example.passwordstorage;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {
    DatabaseHelper myDb;
    Button btnmainActivity;
    EditText textViewContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        myDb = new DatabaseHelper(this);
        btnmainActivity = (Button) findViewById(R.id.button_MainActvity);
        textViewContent = (EditText) findViewById(R.id.editTextContent);
        btnmainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        viewText();



    }
    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void viewText(){
        Cursor res = myDb.getAllData();
        if(res.getCount()==0){
            Toast.makeText(Activity2.this,"no data",Toast.LENGTH_LONG).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Id : " + res.getString(0)+ "\n");
            buffer.append("Name : " + res.getString(1)+ "\n");
            buffer.append("Surname : " + res.getString(2)+ "\n\n");

        }
        String s = buffer.toString();
        textViewContent.setText(s);
    }
}
