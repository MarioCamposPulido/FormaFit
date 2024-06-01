package com.example.formafit.base_datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.formafit.activities.MainActivity;
import com.example.formafit.java.Desafio;
import com.example.formafit.java.EntradaPeso;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Clase BaseDatosHelper, toda la funcionalidad de la base de datos
 */
public class BaseDatosHelper extends SQLiteOpenHelper {

    public BaseDatosHelper(Context context) {
        super(context, EstructuraBBDD.DATABASE_NAME, null, EstructuraBBDD.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_USERSANDWEIGHT);
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_CHALLENGES);
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
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_USERSANDWEIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_CHALLENGES);
        onCreate(db);
    }

    /**
     * Comprueba si existe el Usuario en BBDD por su email
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? ",
                new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        //this.close();

        return exists;
    }

    public int getEdadUser(String fechaNacimientoStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parsear la fecha de nacimiento
            Date fechaNacimiento = sdf.parse(fechaNacimientoStr);

            // Obtener la fecha actual
            Calendar fechaActual = Calendar.getInstance();

            // Crear un calendario con la fecha de nacimiento
            Calendar nacimiento = Calendar.getInstance();
            nacimiento.setTime(fechaNacimiento);

            // Calcular la edad
            int edad = fechaActual.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);

            // Comprobar si el cumpleaños ya ha pasado este año
            if (fechaActual.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--;
            }

            return edad;
        } catch (ParseException e) {
            // Manejar la excepción si el formato de la fecha es incorrecto
            e.printStackTrace();
            return -1; // o alguna otra indicación de error
        }
    }

    public void insertNewUserRegistro(String email, String pasw, String userName,
                                      String gender, String birth, int height,
                                      String date, int weight) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT, email);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USERSANDWEIGHT, pasw);
        values.put(EstructuraBBDD.COLUMN_USERNAME_USERSANDWEIGHT, userName);
        values.put(EstructuraBBDD.COLUMN_GENDER_USERSANDWEIGHT, gender);
        values.put(EstructuraBBDD.COLUMN_BIRTH_USERSANDWEIGHT, birth);
        values.put(EstructuraBBDD.COLUMN_HEIGHT_USERSANDWEIGHT, height);
        values.put(EstructuraBBDD.COLUMN_DATE_USERSANDWEIGHT, date);
        values.put(EstructuraBBDD.COLUMN_WEIGHT_USERSANDWEIGHT, weight);

        // Insertar nueva fila indicando nombre de la tabla
        long newRowId = this.getWritableDatabase().insert(
                EstructuraBBDD.TABLE_USERSANDWEIGHT, null, values);
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
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? AND " +
                        EstructuraBBDD.COLUMN_PASSWORD_USERSANDWEIGHT + "=? ",
                new String[]{emailParam, passwordParam});

        boolean exists = cursor.getCount() >= 1;
        cursor.close();
        //this.close();


        return exists;
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public LinkedList<EntradaPeso> getAllEntradasPeso(String email){
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT date, weight, description, img FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=?",
                new String[]{email});

        // Crear un array para almacenar los resultados
        LinkedList<EntradaPeso> entradasPeso = new LinkedList<>();

        // Extraer los datos del cursor
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                int weight = cursor.getInt(cursor.getColumnIndexOrThrow("weight"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                if (cursor.getBlob(cursor.getColumnIndexOrThrow("img")) != null){
                    Bitmap imagen = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow("img")));
                    entradasPeso.add(new EntradaPeso(date, getAltura(email), weight, getEdadUser(email), description, imagen, getGenero(MainActivity.email)));
                }else {
                    entradasPeso.add(new EntradaPeso(date, getAltura(email), weight, getEdadUser(email), description, null, getGenero(MainActivity.email)));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        //this.close();

        return entradasPeso;
    }

    public LinkedList<Desafio> getAllDesafiosUser(String email){
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT title, description, img, is_checked FROM " + EstructuraBBDD.TABLE_CHALLENGES +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE + "=?",
                new String[]{email});

        // Crear un array para almacenar los resultados
        LinkedList<Desafio> desafios = new LinkedList<>();

        // Extraer los datos del cursor
        int index = 0;
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int is_checked = cursor.getInt(cursor.getColumnIndexOrThrow("is_checked"));
                if (cursor.getBlob(cursor.getColumnIndexOrThrow("img")) != null){
                    Bitmap imagen = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow("img")));
                    desafios.add(new Desafio(title, description, imagen, is_checked));
                }else {
                    desafios.add(new Desafio(title, description, null, is_checked));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        //this.close();

        return desafios;
    }

    public int getAltura(String email) {
        int altura = -1; // Valor predeterminado si no se encuentra ninguna altura

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT height FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            altura = cursor.getInt(cursor.getColumnIndexOrThrow("height"));
        }

        cursor.close();

        return altura;
    }

    public String getFechaNacimiento(String email) {
        String fechaNacimiento = ""; // Valor predeterminado si no se encuentra ninguna fecha de nacimiento

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT birth FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("birth"));
        }

        cursor.close();

        return fechaNacimiento;
    }

    public String getGenero(String email) {
        String genero = ""; // Valor predeterminado si no se encuentra ningún genero

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT gender FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            genero = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
        }

        cursor.close();

        return genero;
    }

    public String getPassword(String email) {
        String password = "";

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT password FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        }

        cursor.close();

        return password;
    }

    public String getUserName(String email) {
        String userName = "";

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT user_name FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
        }

        cursor.close();

        return userName;
    }

    public String getBirth(String email) {
        String birth = "";

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT birth FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            birth = cursor.getString(cursor.getColumnIndexOrThrow("birth"));
        }

        cursor.close();

        return birth;
    }

    public void insertNewEntradaPeso(String email, String date, String description, Bitmap img, int weight) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT, email);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USERSANDWEIGHT, getPassword(email));
        values.put(EstructuraBBDD.COLUMN_USERNAME_USERSANDWEIGHT, getUserName(email));
        values.put(EstructuraBBDD.COLUMN_GENDER_USERSANDWEIGHT, getGenero(email));
        values.put(EstructuraBBDD.COLUMN_BIRTH_USERSANDWEIGHT, getBirth(email));
        values.put(EstructuraBBDD.COLUMN_HEIGHT_USERSANDWEIGHT, getAltura(email));
        values.put(EstructuraBBDD.COLUMN_DATE_USERSANDWEIGHT, date);
        values.put(EstructuraBBDD.COLUMN_DESCRIPTION_USERSANDWEIGHT, description);
        if (!Objects.isNull(img)){
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            img.compress(Bitmap.CompressFormat.PNG, 0 , baos);
            byte[] blob = baos.toByteArray();
            values.put(EstructuraBBDD.COLUMN_IMG_USERSANDWEIGHT, blob);
        }
        values.put(EstructuraBBDD.COLUMN_WEIGHT_USERSANDWEIGHT, weight);

        try {
            // Insertar nueva fila indicando nombre de la tabla
            long newRowId = this.getWritableDatabase().insert(
                    EstructuraBBDD.TABLE_USERSANDWEIGHT, null, values);
            //this.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertNewDesafio(Bitmap img, String title, String description) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE, MainActivity.email);
        values.put(EstructuraBBDD.COLUMN_TITLE_CHALLENGE, title);
        values.put(EstructuraBBDD.COLUMN_DESCRIPTION_CHALLENGE, description);
        if (!Objects.isNull(img)){
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            img.compress(Bitmap.CompressFormat.PNG, 0 , baos);
            byte[] blob = baos.toByteArray();
            values.put(EstructuraBBDD.COLUMN_IMG_CHALLENGE, blob);
        }
        values.put(EstructuraBBDD.COLUMN_IS_CHECKED_CHALLENGE, 0);

        try {
            // Insertar nueva fila indicando nombre de la tabla
            long newRowId = this.getWritableDatabase().insert(
                    EstructuraBBDD.TABLE_CHALLENGES, null, values);
            //this.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int deleteAllEntradasPesoUserExceptLast(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int deletedRows = 0;
        try {
            String selection = EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + " = ? AND " +
                    EstructuraBBDD.COLUMN_ID_USERSANDWEIGHT + " NOT IN (SELECT " + EstructuraBBDD.COLUMN_ID_USERSANDWEIGHT +
                    " FROM " + EstructuraBBDD.TABLE_USERSANDWEIGHT +
                    " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + " = ? " +
                    " ORDER BY " + EstructuraBBDD.COLUMN_ID_USERSANDWEIGHT + " DESC LIMIT 1)";
            String[] selectionArgs = { email, email };
            deletedRows = db.delete(EstructuraBBDD.TABLE_USERSANDWEIGHT, selection, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return deletedRows;
    }

        public boolean upgradeEditarUser(String user_name, String email, String password, String birth, String gender, int height) {

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_USERNAME_USERSANDWEIGHT, user_name);
        values.put(EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT, email);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USERSANDWEIGHT, password);
        values.put(EstructuraBBDD.COLUMN_BIRTH_USERSANDWEIGHT, birth);
        values.put(EstructuraBBDD.COLUMN_GENDER_USERSANDWEIGHT, gender);
        values.put(EstructuraBBDD.COLUMN_HEIGHT_USERSANDWEIGHT, height);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERSANDWEIGHT,
                values,
                selection,
                new String[]{MainActivity.email});

        return count > 0;

    }

}





