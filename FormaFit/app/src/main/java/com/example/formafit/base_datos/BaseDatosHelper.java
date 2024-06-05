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
import com.example.formafit.java.Usuario;

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
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_USER);
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_CHALLENGES);
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_WEIGHTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Código para actualizar la estructura de la base de datos
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_CHALLENGES);
        db.execSQL("DROP TABLE IF EXISTS " + EstructuraBBDD.TABLE_WEIGHTS);
        onCreate(db);
    }

    // Verifica si el email ya está en la BBDD
    public boolean checkEmail(String email) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? LIMIT 1",
                new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        //this.close();

        return exists;
    }

    // Mediante un String que se asemeja a una feecha, calcula sus años
    public int getEdadNumber(String fechaNacimientoStr) {
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
            return -1;
        }
    }

    // Inserta un nuevo Usuario
    public void insertNewUserRegistro(String email, String pasw, String userName,
                                      String gender, String birth, int height) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER, email);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USER, pasw);
        values.put(EstructuraBBDD.COLUMN_USERNAME_USER, userName);
        values.put(EstructuraBBDD.COLUMN_GENDER_USER, gender);
        values.put(EstructuraBBDD.COLUMN_BIRTH_USER, birth);
        values.put(EstructuraBBDD.COLUMN_HEIGHT_USER, height);

        // Insertar nueva fila indicando nombre de la tabla
        long newRowId = this.getWritableDatabase().insert(
                EstructuraBBDD.TABLE_USERS, null, values);
        //this.close();

    }

    // Inserta el nuevo peso desde el registro
    public void insertNewWeightRegistro(String email, int weight) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT, email);
        values.put(EstructuraBBDD.COLUMN_DATE_WEIGHT, MainActivity.getFechaActual());
        values.put(EstructuraBBDD.COLUMN_WEIGHT_WEIGHT, weight);

        // Insertar nueva fila indicando nombre de la tabla
        long newRowId = this.getWritableDatabase().insert(
                EstructuraBBDD.TABLE_WEIGHTS, null, values);
        //this.close();

    }

    // Verifica el Login
    public boolean checkUserLogin(String emailParam, String passwordParam) {

        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? AND " +
                        EstructuraBBDD.COLUMN_PASSWORD_USER + "=? ",
                new String[]{emailParam, passwordParam});

        boolean exists = cursor.getCount() >= 1;
        cursor.close();
        //this.close();


        return exists;
    }

    // Transforma un Array de bytes en un Bitmap
    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    // Obtiene todas las Entradas de Peso
    public LinkedList<EntradaPeso> getAllEntradasPeso(String email) {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_WEIGHTS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT + "=?",
                new String[]{email});

        // Crear un array para almacenar los resultados
        LinkedList<EntradaPeso> entradasPeso = new LinkedList<>();

        // Extraer los datos del cursor
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                int weight = cursor.getInt(cursor.getColumnIndexOrThrow("weight"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                if (cursor.getBlob(cursor.getColumnIndexOrThrow("img")) != null) {
                    Bitmap imagen = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow("img")));
                    entradasPeso.add(new EntradaPeso(date, getAltura(email), weight, getEdadNumber(email), description, imagen, getGenero(MainActivity.email)));
                } else {
                    entradasPeso.add(new EntradaPeso(date, getAltura(email), weight, getEdadNumber(email), description, null, getGenero(MainActivity.email)));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        //this.close();

        return entradasPeso;
    }

    // Obtiene todos los datos del Usuario
    public Usuario getAllDataUser(String emailParam) {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=?",
                new String[]{emailParam});

        Usuario usuario = null;

        // Extraer los datos del cursor
        if (cursor.moveToFirst()) {
            String nombre_usuario = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
            String nacimientoFecha = cursor.getString(cursor.getColumnIndexOrThrow("birth"));
            int height = cursor.getInt(cursor.getColumnIndexOrThrow("height"));

            usuario = new Usuario(email, nombre_usuario, password, gender, nacimientoFecha, String.valueOf(height));
        }

        cursor.close();
        //this.close();

        return usuario;
    }

    // Obtiene todos los desafíos actuales
    public LinkedList<Desafio> getAllDesafiosActualesUser(String email) {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_CHALLENGES +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE + "=? AND "
                        + EstructuraBBDD.COLUMN_IS_CHECKED_CHALLENGE + "=0",
                new String[]{email});

        // Crear un array para almacenar los resultados
        LinkedList<Desafio> desafios = new LinkedList<>();

        // Extraer los datos del cursor
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int is_checked = cursor.getInt(cursor.getColumnIndexOrThrow("is_checked"));
                if (cursor.getBlob(cursor.getColumnIndexOrThrow("img")) != null) {
                    Bitmap imagen = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow("img")));
                    desafios.add(new Desafio(id, title, description, imagen, is_checked));
                } else {
                    desafios.add(new Desafio(id, title, description, null, is_checked));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        //this.close();

        return desafios;
    }

    // Obtiene todos los desafíos completados
    public LinkedList<Desafio> getAllDesafiosCompletadosUser(String email) {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + EstructuraBBDD.TABLE_CHALLENGES +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE + "=? AND "
                        + EstructuraBBDD.COLUMN_IS_CHECKED_CHALLENGE + "=1",
                new String[]{email});

        // Crear un array para almacenar los resultados
        LinkedList<Desafio> desafios = new LinkedList<>();

        // Extraer los datos del cursor
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int is_checked = cursor.getInt(cursor.getColumnIndexOrThrow("is_checked"));
                if (cursor.getBlob(cursor.getColumnIndexOrThrow("img")) != null) {
                    Bitmap imagen = byteArrayToBitmap(cursor.getBlob(cursor.getColumnIndexOrThrow("img")));
                    desafios.add(new Desafio(id, title, description, imagen, is_checked));
                } else {
                    desafios.add(new Desafio(id, title, description, null, is_checked));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        //this.close();

        return desafios;
    }

    // Obtiene el objetivo por el email del usuario
    public int getObjetivoByUser(String email) {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT goal FROM " + EstructuraBBDD.TABLE_USERS +
                        " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? LIMIT 1",
                new String[]{email});

        // Extraer los datos del cursor
        int goal = 0;
        if (cursor.moveToFirst()) {
            goal = cursor.getInt(cursor.getColumnIndexOrThrow("goal"));
        }

        cursor.close();
        //this.close();

        return goal;
    }

    public int getAltura(String email) {
        int altura = -1; // Valor predeterminado si no se encuentra ninguna altura

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT height FROM " + EstructuraBBDD.TABLE_USERS +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            altura = cursor.getInt(cursor.getColumnIndexOrThrow("height"));
        }

        cursor.close();

        return altura;
    }

    public String getGenero(String email) {
        String genero = ""; // Valor predeterminado si no se encuentra ningún género

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT gender FROM " + EstructuraBBDD.TABLE_USERS +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            genero = cursor.getString(cursor.getColumnIndexOrThrow("gender"));
        }

        cursor.close();

        return genero;
    }

    public String getUserName(String email) {
        String userName = "";

        // Consulta SQL para obtener la altura limitada a un solo registro
        String query = "SELECT user_name FROM " + EstructuraBBDD.TABLE_USERS +
                " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER + "=? " +
                " LIMIT 1";

        Cursor cursor = this.getReadableDatabase().rawQuery(query, new String[]{email});

        // Verificar si se encontró algún resultado
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndexOrThrow("user_name"));
        }

        cursor.close();

        return userName;
    }

    // Inserta en la BBDD una nueva Entrada de Peso
    public void insertNewEntradaPeso(String email, String date, String description, Bitmap img, int weight) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT, email);
        values.put(EstructuraBBDD.COLUMN_DATE_WEIGHT, date);
        values.put(EstructuraBBDD.COLUMN_DESCRIPTION_WEIGHT, description);
        if (!Objects.isNull(img)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            img.compress(Bitmap.CompressFormat.PNG, 0, baos);
            byte[] blob = baos.toByteArray();
            values.put(EstructuraBBDD.COLUMN_IMG_WEIGHT, blob);
        }
        values.put(EstructuraBBDD.COLUMN_WEIGHT_WEIGHT, weight);

        try {
            // Insertar nueva fila indicando nombre de la tabla
            long newRowId = this.getWritableDatabase().insert(
                    EstructuraBBDD.TABLE_WEIGHTS, null, values);
            //this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inserta un nuevo desafío
    public void insertNewDesafio(Bitmap img, String title, String description) {

        // Creamos mapa de valores con los nombres de las tablas
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE, MainActivity.email);
        values.put(EstructuraBBDD.COLUMN_TITLE_CHALLENGE, title);
        values.put(EstructuraBBDD.COLUMN_DESCRIPTION_CHALLENGE, description);
        if (!Objects.isNull(img)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            img.compress(Bitmap.CompressFormat.PNG, 0, baos);
            byte[] blob = baos.toByteArray();
            values.put(EstructuraBBDD.COLUMN_IMG_CHALLENGE, blob);
        }
        values.put(EstructuraBBDD.COLUMN_IS_CHECKED_CHALLENGE, 0);

        try {
            // Insertar nueva fila indicando nombre de la tabla
            long newRowId = this.getWritableDatabase().insert(
                    EstructuraBBDD.TABLE_CHALLENGES, null, values);
            //this.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borra todas las Entradas de Peso menos la última introducida
    public int deleteAllEntradasPesoUserExceptLast(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int deletedRows;
        try {
            String selection = EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT + " = ? AND " +
                    EstructuraBBDD.COLUMN_ID_WEIGHT + " NOT IN (" +
                    "SELECT " + EstructuraBBDD.COLUMN_ID_WEIGHT +
                    " FROM " + EstructuraBBDD.TABLE_WEIGHTS +
                    " WHERE " + EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT + " = ? " +
                    "ORDER BY " + EstructuraBBDD.COLUMN_ID_WEIGHT + " DESC LIMIT 1)";
            String[] selectionArgs = {email, email};
            deletedRows = db.delete(EstructuraBBDD.TABLE_WEIGHTS, selection, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return deletedRows;
    }

    // Borra todos los datos de un Usuario, incluidos sus desafíos y entradas de peso
    public int deleteAllDataUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int deletedRows;
        try {
            String selection = EstructuraBBDD.COLUMN_EMAIL_USER + " = ?";
            String selection2 = EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE + " = ?";
            String selection3 = EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT + " = ?";
            String[] selectionArgs = {email};
            deletedRows = db.delete(EstructuraBBDD.TABLE_USERS, selection, selectionArgs) +
                    db.delete(EstructuraBBDD.TABLE_CHALLENGES, selection2, selectionArgs) +
                    db.delete(EstructuraBBDD.TABLE_WEIGHTS, selection3, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
        return deletedRows;
    }

    // Edita los datos del Usuario
    public boolean upgradeEditarUser(String user_name, String email, String password, String birth, String gender, int height) {

        upgradeChallengesByEmail(email);
        upgradeWeightsByEmail(email);

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_USERNAME_USER, user_name);
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER, email);
        values.put(EstructuraBBDD.COLUMN_PASSWORD_USER, password);
        values.put(EstructuraBBDD.COLUMN_BIRTH_USER, birth);
        values.put(EstructuraBBDD.COLUMN_GENDER_USER, gender);
        values.put(EstructuraBBDD.COLUMN_HEIGHT_USER, height);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERS,
                values,
                selection,
                new String[]{MainActivity.email});

        MainActivity.email = email;
        return count > 0;
    }

    public boolean upgradeChallengesByEmail(String email) {

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE, email);

        // Nombre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_CHALLENGES,
                values,
                selection,
                new String[]{MainActivity.email});

        return count > 0;
    }

    public void upgradeWeightsByEmail(String email) {

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT, email);

        // Nombre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_WEIGHTS,
                values,
                selection,
                new String[]{MainActivity.email});

    }

    public void upgradeCambiarDesafiosIs_Checked(int id_desafio, int is_checked) {

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_IS_CHECKED_CHALLENGE, is_checked);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_ID_CHALLENGE + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_CHALLENGES,
                values,
                selection,
                new String[]{String.valueOf(id_desafio)});

    }

    public void upgradeCambiarObjetivo(String email, int objetivoPeso) {

        // Nuevo valor para la columna
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.COLUMN_GOAL_USER, objetivoPeso);

        // NOmbre columna a actualizar
        String selection = EstructuraBBDD.COLUMN_EMAIL_USER + " LIKE ?";

        int count = this.getWritableDatabase().update(
                EstructuraBBDD.TABLE_USERS,
                values,
                selection,
                new String[]{String.valueOf(email)});

    }

}





