package com.example.cccp.classified;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    DB db;
    Cursor cursor;
    EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        db = new DB(this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);

        SaveMoment saveMoment = new SaveMoment();

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);

        if (saveMoment.OR == false) {
            et1.setText(saveMoment.NAME); //name
            et2.setText(saveMoment.TEXT); //text
        }

        ImageView ibnt1 = (ImageView) findViewById(R.id.ibtn1); //обработчик нажатия кнопки - галочка
        ibnt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMoment saveMoment = new SaveMoment();
                if (saveMoment.OR == true) { //true - создать пункт listView
                    db.addRec(et1.getText().toString(), R.drawable.locked, et2.getText().toString()); //Создаем пункт listView
                } else { //false - редактировать
                    db.updName(et1.getText().toString(), saveMoment.ID); //меняем name
                    db.updTxt(et2.getText().toString(), saveMoment.ID); //меняем text
                    saveMoment.ID = 0;
                    saveMoment.NAME = null; //Нулируем значения, т.к больше не нужны
                    saveMoment.TEXT = null;
                }
                cursor.requery(); //обновить
                saveMoment.OR = false;
                Back();
            }
        });
    }
    public void Back(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

