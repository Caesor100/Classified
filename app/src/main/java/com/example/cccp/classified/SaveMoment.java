package com.example.cccp.classified;

/**
 * Created by CCCP on 10.07.2017.
 */
public class SaveMoment {
    public static String EditText1; //name
    public static String EditText2; //text
    public static long ID; //id
    public static boolean OR; //вариант (редактировать(false)/создать(true)) и (ввести пин-код(false)/создать пин-код(true))
    public static int MANU_OR = 1; //для onResume в результатах ввода и вывода пароля
    public static String NAME; //name or secret
    public static String TEXT;
    public static String KEY; //пин-код
    public static String POSITION; //позиция в listView
}
