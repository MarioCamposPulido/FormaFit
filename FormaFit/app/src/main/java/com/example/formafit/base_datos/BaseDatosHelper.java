package com.example.formafit.base_datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
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
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_WEIGHT);
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
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_WEIGHT);
        onCreate(db);
    }

    /**
     * Transforma un Array de bytes a bitmap
     * @param bytes Array de bytes
     * @return Bitmap
     */
    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
        }
        return null;
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

    public boolean checkUserLogin(String emailParam, String passwordParam) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? AND " +
                        EstructuraBBDD.COLUMN_PASSWORD_USER + "=? ",
                new String[]{emailParam, passwordParam});

        boolean exists = cursor.getCount() == 1;
        if (cursor.moveToFirst()) {
            // Acceso a los datos de la fila actual

        }
        cursor.close();
        //this.close();


        return exists;
    }


}





