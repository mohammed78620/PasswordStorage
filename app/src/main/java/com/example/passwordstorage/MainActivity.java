package com.example.passwordstorage;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName,editSurname,editMarks,editID;
    Button btnAddData;
    Button btnviewAll;
    Button btnUpdate;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        editName = (EditText)findViewById(R.id.editText_name);
        editSurname = (EditText)findViewById(R.id.editText2_surname);
        editMarks = (EditText)findViewById(R.id.editText3_Marks);
        editID = (EditText)findViewById(R.id.editText4_ID);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button2_viewAll);
        btnUpdate = (Button) findViewById(R.id.button3_update);
        btnDelete = (Button) findViewById(R.id.button4_Delete);
        AddDta();
        viewAll();
        update();
        deleteData();


}
public void AddDta(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = myDb.insertData(editName.getText().toString(),
                        editSurname.getText().toString(),
                        editMarks.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
}
public void viewAll(){
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this,"no data",Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id : " + res.getString(0)+ "\n");
                    buffer.append("Name : " + res.getString(1)+ "\n");
                    buffer.append("Surname : " + res.getString(2)+ "\n");
                    buffer.append("Marks : " + res.getString(3)+ "\n\n");
                }
                showMessage("Data",buffer.toString());

            }
        });
}
public void update(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean isUpdate =  myDb.updateData(editID.getText().toString(),
                        editName.getText().toString(),
                        editSurname.getText().toString(),editMarks.getText().toString());
               if(isUpdate){
                   Toast.makeText(MainActivity.this,"Data has been updated",Toast.LENGTH_LONG).show();
               }else {
                   Toast.makeText(MainActivity.this,"Data has not been",Toast.LENGTH_LONG).show();
               }
            }
        });
}
public void deleteData(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDb.deleteDta(editID.getText().toString());
                if(deletedRows>0){
                    Toast.makeText(MainActivity.this,"Data has been deleted",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"Data has not been deleted",Toast.LENGTH_LONG).show();
                }

            }
        });
}

public void showMessage(String title,String message){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.show();
}

}
