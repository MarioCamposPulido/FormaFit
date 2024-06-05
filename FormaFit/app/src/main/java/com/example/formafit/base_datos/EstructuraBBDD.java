package com.example.formafit.base_datos;

public class EstructuraBBDD {

    /* Definimos datos de la tabla*/
    public static final String DATABASE_NAME = "FormaFit";
    public static final int DATABASE_VERSION = 6;

    // Tabla Users
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_EMAIL_USER = "email";
    public static final String COLUMN_PASSWORD_USER = "password";
    public static final String COLUMN_USERNAME_USER = "user_name";
    public static final String COLUMN_GENDER_USER = "gender";
    public static final String COLUMN_BIRTH_USER = "birth";
    public static final String COLUMN_HEIGHT_USER = "height";
    public static final String COLUMN_GOAL_USER = "goal";

    // Tabla Weight
    public static final String TABLE_WEIGHTS = "Weights";
    public static final String COLUMN_ID_WEIGHT = "id";
    public static final String COLUMN_EMAIL_USER_WEIGHT = "email_user";
    public static final String COLUMN_DATE_WEIGHT = "date";
    public static final String COLUMN_DESCRIPTION_WEIGHT = "description";
    public static final String COLUMN_IMG_WEIGHT = "img";
    public static final String COLUMN_WEIGHT_WEIGHT = "weight";


    // Tabla Challenges
    public static final String TABLE_CHALLENGES = "Challenges";
    public static final String COLUMN_ID_CHALLENGE = "id";
    public static final String COLUMN_EMAIL_USER_CHALLENGE = "email_user";
    public static final String COLUMN_TITLE_CHALLENGE = "title";
    public static final String COLUMN_DESCRIPTION_CHALLENGE = "description";
    public static final String COLUMN_IMG_CHALLENGE = "img";
    public static final String COLUMN_IS_CHECKED_CHALLENGE = "is_checked";



    // Creamos las tablas
    public static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + EstructuraBBDD.TABLE_USERS + " (" +
                    EstructuraBBDD.COLUMN_EMAIL_USER + " TEXT PRIMARY KEY," +
                    EstructuraBBDD.COLUMN_PASSWORD_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_USERNAME_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_GENDER_USER + " TEXT," +
                    EstructuraBBDD.COLUMN_BIRTH_USER + " DATE," +
                    EstructuraBBDD.COLUMN_HEIGHT_USER + " INT," +
                    EstructuraBBDD.COLUMN_GOAL_USER + " INTEGER);" ;

    public static final String SQL_CREATE_TABLE_WEIGHTS =
            "CREATE TABLE " + EstructuraBBDD.TABLE_WEIGHTS + " (" +
                    EstructuraBBDD.COLUMN_ID_WEIGHT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EstructuraBBDD.COLUMN_EMAIL_USER_WEIGHT + " TEXT," +
                    EstructuraBBDD.COLUMN_DATE_WEIGHT + " DATE," +
                    EstructuraBBDD.COLUMN_DESCRIPTION_WEIGHT + " TEXT," +
                    EstructuraBBDD.COLUMN_IMG_WEIGHT + " BLOB," +
                    EstructuraBBDD.COLUMN_WEIGHT_WEIGHT + " INTEGER);" ;

    public static final String SQL_CREATE_TABLE_CHALLENGES =
            "CREATE TABLE " + EstructuraBBDD.TABLE_CHALLENGES + " (" +
                    EstructuraBBDD.COLUMN_ID_CHALLENGE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE + " TEXT," +
                    EstructuraBBDD.COLUMN_TITLE_CHALLENGE + " TEXT," +
                    EstructuraBBDD.COLUMN_DESCRIPTION_CHALLENGE + " TEXT," +
                    EstructuraBBDD.COLUMN_IMG_CHALLENGE + " BLOB," +
                    EstructuraBBDD.COLUMN_IS_CHECKED_CHALLENGE + " INTEGER);";

}