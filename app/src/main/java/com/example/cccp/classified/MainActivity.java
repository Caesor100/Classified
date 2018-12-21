package com.example.cccp.classified;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private  static final String TAG = "Logs";

    ListView lvMain;
    DB db;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);

        String[] from = new String[] { "img", "name"};

        int[] to = new int[] { R.id.ivImg, R.id.tvText};

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(scAdapter);

        registerForContextMenu(lvMain);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) { //обработчик нажатия пунка listView
                if (cursor.getString(1).equals(String.valueOf(R.drawable.key))) { //если картинка с ключом, т.е заблокировано
                    Toast.makeText(MainActivity.this, "Заблокировано", Toast.LENGTH_SHORT).show();
                }else {
                    SaveMoment saveMoment = new SaveMoment();
                    saveMoment.NAME = cursor.getString(3); //et1 = name
                    saveMoment.TEXT = cursor.getString(2); //et2 = text
                    saveMoment.ID = id;
                    saveMoment.OR = false; //вариант false - редактировать

                    transition();
                }
            }
        });
    }

    public void transition(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    public void onButtonClick(View view) {
        SaveMoment saveMoment = new SaveMoment();
        switch(view.getId()){
            case R.id.ibtn1: //кнопка добавить
                saveMoment.OR = true; //вариант true - добавить
                Intent intent = new Intent(this, Main2Activity.class); //переход в класс Main2Activity
                startActivity(intent);
                finish();
                break;
            case R.id.ivImg:
                int position = lvMain.getPositionForView((View) view.getParent()); //Данная позиция ListView
                long id = lvMain.getItemIdAtPosition(position); //id

                saveMoment.ID = id; //сохраняем id

                if (cursor.getString(1).equals(String.valueOf(R.drawable.key))){ //если картинка с ключом
                    saveMoment.OR = false; //вариант false - ввести пин-код
                    saveMoment.KEY = cursor.getString(4); //сохраняем пин-код
                    saveMoment.NAME = cursor.getString(5); //сохраняем name хранящийся в secret
                    saveMoment.TEXT = cursor.getString(2); //сохраняем text;
                }else{
                    saveMoment.OR = true; //вариант true - создать пин-код
                    saveMoment.NAME = cursor.getString(3); // сохраняем name
                    saveMoment.TEXT = cursor.getString(2); //сохраняем text;
                    saveMoment.POSITION = String.valueOf(position + 1); //сохраняем позицию
                }

                intent = new Intent(this, Password.class); //вызываем диалог класса Password
                startActivity(intent);

                break;

        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, R.string.delete_record);
    }

    public boolean onContextItemSelected(MenuItem item) { //удалить запись
        if (item.getItemId() == 1) {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            System.out.println(acmi.id);
            db.delRec(acmi.id);
            cursor.requery(); //обновить
            return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_item1:
                db.del(); //удалить всё
                System.out.println(cursor.getCount());
                cursor.requery();
                Toast.makeText(MainActivity.this, "Удалено всё", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        SaveMoment saveMoment = new SaveMoment();
        if (saveMoment.MANU_OR == 0){
            Toast.makeText(MainActivity.this, "Неверный пин-код", Toast.LENGTH_SHORT).show();
        }
        if (saveMoment.MANU_OR == 2){
            Toast.makeText(MainActivity.this, "Вы не ввели пин-код", Toast.LENGTH_SHORT).show();
        }
        saveMoment.MANU_OR = 1;
        cursor.requery();
    }
}
