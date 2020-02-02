package com.example.passwordstorage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editID;
    Button btnAddData;
    Button btnviewAll;
    Button btnUpdate;
    Button btnDelete;
    Button btnActiivity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText) findViewById(R.id.editText_login);
        editSurname = (EditText) findViewById(R.id.editText2_password);
        editID = (EditText) findViewById(R.id.editText4_ID);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button2_viewAll);
        btnUpdate = (Button) findViewById(R.id.button3_update);
        btnDelete = (Button) findViewById(R.id.button4_Delete);
        btnActiivity2 = (Button) findViewById(R.id.button_MainActvity);
        AddDta();
        viewAll();
        update();
        deleteData();
        btnActiivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });


    }

    public void AddDta() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(encrypt(editName.getText().toString(),7),
                        encrypt(editSurname.getText().toString(),7));
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id : " + res.getString(0) + "\n");
                    buffer.append("Login : " + decrypt(res.getString(1),7) + "\n");
                    buffer.append("Password : " + decrypt(res.getString(2),7) + "\n\n");

                }
                showMessage("Data", buffer.toString());

            }
        });
    }

    public void update() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(editID.getText().toString(),
                        editName.getText().toString(),
                        editSurname.getText().toString());
                if (isUpdate) {
                    Toast.makeText(MainActivity.this, "Data has been updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data has not been", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteDta(editID.getText().toString());
                if (deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Data has been deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data has not been deleted", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public String encrypt(String message, int key) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<message.length();i++){
            char letter = message.charAt(i);
            int ascii = (int)letter;
            ascii +=7;
            letter = (char)ascii;
            builder.append(letter);
        }
        return builder.toString();
    }
    public String decrypt(String message,int key){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<message.length();i++){
            char letter = message.charAt(i);
            int ascii = (int)letter;
            ascii -=7;
            letter = (char)ascii;
            builder.append(letter);
        }
        return builder.toString();
    }
}