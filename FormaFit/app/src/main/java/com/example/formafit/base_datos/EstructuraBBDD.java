package com.example.formafit.base_datos;

public class EstructuraBBDD {

    /* Definimos datos de la tabla*/
    public static final String DATABASE_NAME = "FormaFit";
    public static final int DATABASE_VERSION = 2;

    // Tabla Users
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_ID_USER = "id_user";
    public static final String COLUMN_EMAIL_USER = "email";
    public static final String COLUMN_PASSWORD_USER = "password";
    public static final String COLUMN_USERNAME_USER = "user_name";
    public static final String COLUMN_GENDER_USER = "gender";
    public static final String COLUMN_BIRTH_USER = "birth";
    public static final String COLUMN_HEIGHT_USER = "height";
    public static final String COLUMN_DATE_USER = "date";
    public static final String COLUMN_WEIGHT_USER = "weight";

    // Creamos las tablas
    public static final String SQL_CREATE_TABLE_USERS =
            "CREATE TABLE " + EstructuraBBDD.TABLE_USERS + " (" +
                    EstructuraBBDD.COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EstructuraBBDD.COLUMN_EMAIL_USER + " TEXT UNIQUE," +
                    EstructuraBBDD.COLUMN_PASSWORD_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_USERNAME_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_GENDER_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_BIRTH_USER + " DATE," +
                    EstructuraBBDD.COLUMN_HEIGHT_USER + " INT," +
                    EstructuraBBDD.COLUMN_DATE_USER + " DATE," +
                    EstructuraBBDD.COLUMN_WEIGHT_USER + " INT);" ;

}