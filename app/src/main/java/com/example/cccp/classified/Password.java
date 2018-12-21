package com.example.cccp.classified;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Password extends AppCompatActivity implements View.OnClickListener{

    private  static final String TAG = "Logs";

    Button btnOk, btnCancel;
    EditText etPassword;
    DB db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        setTitle("Введите пин-код:");

        db = new DB(this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);


        etPassword = (EditText) findViewById(R.id.etPassword);

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        SaveMoment saveMoment = new SaveMoment();
        switch (view.getId()){
            case R.id.btnOk: //принять

                Encoder encoder = new Encoder();

                String key = etPassword.getText().toString();

                if (saveMoment.OR == false){ //false - ввести пи-код

                    if (key.equals(saveMoment.KEY)){ //если пин-код верный
                        db.updImg(R.drawable.locked, saveMoment.ID); //меняем картинку ключа на замок
                        db.updKey(0, saveMoment.ID); //Нулируем ключ, т.к больше не нужен
                        db.updTxt(encoder.decryption(saveMoment.TEXT, 3), saveMoment.ID);
                        db.updName(encoder.decryption(saveMoment.NAME, 3), saveMoment.ID); //Ставим обратно имя
                        saveMoment.MANU_OR = 1; // 1 - всё в порядке
                    }else{
                        saveMoment.MANU_OR = 0; //0 - "Неверный пин-код"
                    }
                }else{ // true - создать пин-код
                    if (key.equals("")){
                        saveMoment.MANU_OR = 2; //2 - "Вы не ввели пин-код"
                    }
                    else {
                        db.updImg(R.drawable.key, saveMoment.ID); //меняем картинку замка на ключ
                        db.updKey(Integer.parseInt(key), saveMoment.ID); //присваиваем пин-код
                        db.updSecret(encoder.encryption(saveMoment.NAME, 3), saveMoment.ID); //скрываем name в secret
                        db.updTxt(encoder.encryption(saveMoment.TEXT, 3), saveMoment.ID); //шифруем text
                        db.updName("ClASSIFIED №" + saveMoment.POSITION, saveMoment.ID); //присваиваем новое значение name - "ClASSIFIED № ?"
                        saveMoment.MANU_OR = 1; //1 - всё в порядке
                    }
                }
                saveMoment.NAME = null; //Нулируем значения, т.к больше не нужны
                saveMoment.TEXT = null;
                cursor.requery(); //обновить
                finish(); //выйти из класса
                break;
            case R.id.btnCancel: //отмена
                finish(); //выйти из класса
                break;
        }
    }
}
