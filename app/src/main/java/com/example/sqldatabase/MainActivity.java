package com.example.sqldatabase;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
DatabaseHelper myDB;
EditText editTextId,editName,editEmail,editCC;
Button buttonAdd,buttonGetData,buttonUpdate,buttonDelete,buttonViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    myDB=new DatabaseHelper(this);

    editTextId=findViewById(R.id.editText_id);
    editName=findViewById(R.id.editText_name);
    editEmail=findViewById(R.id.editText_email);
    editCC=findViewById(R.id.editText_CC);

buttonAdd=findViewById(R.id.button_add);
buttonUpdate=findViewById(R.id.button_update);
buttonDelete=findViewById(R.id.button_delete);
buttonGetData=findViewById(R.id.button_view);
buttonViewAll=findViewById(R.id.button_viewAll);

AddData();
GetData();
viewAll();
updateData();
deleteData();
    }
    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=myDB.insert_data(editName.getText().toString(),editEmail.getText().toString(),editCC.getText().toString());
                        if(isInserted){
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();

                        }

                //Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void GetData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String id=editTextId.getText().toString();
            if(id.equals(String.valueOf("")))
            {
                editTextId.setError("Enter ID");

            }
            else
            {
                Cursor cursor=myDB.getData(id);
                String data=null;
                //if cursor can move to next ,if something exists next

                if(cursor.moveToNext()){

                    data="ID:"+cursor.getString(0)+"\n"+"Name:"+cursor.getString(1)+"\n"
                            +"Email:"+cursor.getString(2)+"\n"+
                            "Course Count:"+cursor.getString(3)+"\n";

                }

                showMessage("Data",data);
            }
            }
        });
    }

    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=myDB.getAllData();
                //small test
                if(cursor.getCount()==0)
                {
                    showMessage("Error","Nothing Found in DB");
                    return;
                }
                StringBuffer buffer=new StringBuffer();

                while (cursor.moveToNext()){
                    buffer.append("ID:"+cursor.getString(0)+"\n");
                    buffer.append("Name:"+cursor.getString(1)+"\n");
                    buffer.append("Email:"+cursor.getString(2)+"\n");
                    buffer.append("Course Count:"+cursor.getString(3)+"\n\n");
                }
                showMessage("All Data",buffer.toString());
            }
        });
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=myDB.update_data(editTextId.getText().toString(),
                        editName.getText().toString(),editEmail.getText().toString(),
                        editCC.getText().toString());

                if (isUpdate){
                    Toast.makeText(MainActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"OOPS!",Toast.LENGTH_SHORT).show();

                }
            }
            });
        }

    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedData= myDB.deleteData(editTextId.getText().toString());

                //check if id is deleted id yes then integer value >1
                if(deletedData>0){
                Toast.makeText(MainActivity.this,"Delete Success!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Delete Unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }






}
