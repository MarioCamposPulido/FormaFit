package com.example.formafit.base_datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.LinkedList;

/**
 * Clase BaseDatosHelper, toda la funcionalidad de la base de datos
 */
public class BaseDatosHelper extends SQLiteOpenHelper {

    public BaseDatosHelper(Context context) {
        super(context, EstructuraBBDD.DATABASE_NAME, null, EstructuraBBDD.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_USERS);
    }

    /**
     * Cuando se realiza alguna actualizacion de la BBDD
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Código para actualizar la estructura de la base de datos
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_USERS);
        onCreate(db);
    }

    /**
     * Comprueba si existe el Usuario en BBDD por su email
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? ",
                new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        //this.close();

        return exists;
    }

    public void insertNewUserRegistro(String email, String pasw, String userName,
                                      String gender, String birth, int height,
                                      String date, int weight) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER, email);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USER, pasw);
        values.put(EstructuraBBDD.COLUMN_USERNAME_USER, userName);
        values.put(EstructuraBBDD.COLUMN_GENDER_USER, gender);
        values.put(EstructuraBBDD.COLUMN_BIRTH_USER, birth);
        values.put(EstructuraBBDD.COLUMN_HEIGHT_USER, height);
        values.put(EstructuraBBDD.COLUMN_DATE_USER, date);
        values.put(EstructuraBBDD.COLUMN_WEIGHT_USER, weight);

        // Insertar nueva fila indicando nombre de la tabla
        long newRowId = this.getWritableDatabase().insert(
                EstructuraBBDD.TABLE_USERS, null, values);
        //this.close();

    }

    /**
     * Comprueba si el Usuario existe por su email y contraseña
     * @param emailParam
     * @param passwordParam
     * @return
     */
    public boolean checkUserLogin(String emailParam, String passwordParam) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? AND " +
                        EstructuraBBDD.COLUMN_PASSWORD_USER + "=? ",
                new String[]{emailParam, passwordParam});

        boolean exists = cursor.getCount() == 1;
        cursor.close();
        //this.close();


        return exists;
    }


}





