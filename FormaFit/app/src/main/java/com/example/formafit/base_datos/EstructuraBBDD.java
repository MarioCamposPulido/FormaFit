package com.example.formafit.base_datos;

public class EstructuraBBDD {

    /* Definimos datos de la tabla*/
    public static final String DATABASE_NAME = "FormaFit";
    public static final int DATABASE_VERSION = 4;

    // Tabla Users
    public static final String TABLE_USERSANDWEIGHT = "UsersAndWeight";
    public static final String COLUMN_ID_USERSANDWEIGHT = "id";
    public static final String COLUMN_EMAIL_USERSANDWEIGHT = "email";
    public static final String COLUMN_PASSWORD_USERSANDWEIGHT = "password";
    public static final String COLUMN_USERNAME_USERSANDWEIGHT = "user_name";
    public static final String COLUMN_GENDER_USERSANDWEIGHT = "gender";
    public static final String COLUMN_BIRTH_USERSANDWEIGHT = "birth";
    public static final String COLUMN_HEIGHT_USERSANDWEIGHT = "height";
    public static final String COLUMN_DATE_USERSANDWEIGHT = "date";
    public static final String COLUMN_DESCRIPTION_USERSANDWEIGHT = "description";
    public static final String COLUMN_IMG_USERSANDWEIGHT = "img";
    public static final String COLUMN_WEIGHT_USERSANDWEIGHT = "weight";

    // Tabla Challenges
    public static final String TABLE_CHALLENGES = "Challenges";
    public static final String COLUMN_ID_CHALLENGE = "id";
    public static final String COLUMN_EMAIL_USER_CHALLENGE = "email_user";
    public static final String COLUMN_TITLE_CHALLENGE = "title";
    public static final String COLUMN_DESCRIPTION_CHALLENGE = "description";
    public static final String COLUMN_IMG_CHALLENGE = "img";
    public static final String COLUMN_IS_CHECKED_CHALLENGE = "is_checked";



    // Creamos las tablas
    public static final String SQL_CREATE_TABLE_USERSANDWEIGHT =
            "CREATE TABLE " + EstructuraBBDD.TABLE_USERSANDWEIGHT + " (" +
                    EstructuraBBDD.COLUMN_ID_USERSANDWEIGHT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EstructuraBBDD.COLUMN_EMAIL_USERSANDWEIGHT + " TEXT," +
                    EstructuraBBDD.COLUMN_PASSWORD_USERSANDWEIGHT + " TEXT," +
                    EstructuraBBDD.COLUMN_USERNAME_USERSANDWEIGHT + " TEXT," +
                    EstructuraBBDD.COLUMN_GENDER_USERSANDWEIGHT + " TEXT," +
                    EstructuraBBDD.COLUMN_BIRTH_USERSANDWEIGHT + " DATE," +
                    EstructuraBBDD.COLUMN_HEIGHT_USERSANDWEIGHT + " INT," +
                    EstructuraBBDD.COLUMN_DATE_USERSANDWEIGHT + " DATE," +
                    EstructuraBBDD.COLUMN_DESCRIPTION_USERSANDWEIGHT + " TEXT," +
                    EstructuraBBDD.COLUMN_IMG_USERSANDWEIGHT + " BLOB," +
                    EstructuraBBDD.COLUMN_WEIGHT_USERSANDWEIGHT + " INT);" ;

    public static final String SQL_CREATE_TABLE_CHALLENGES =
            "CREATE TABLE " + EstructuraBBDD.TABLE_CHALLENGES + " (" +
                    EstructuraBBDD.COLUMN_ID_CHALLENGE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EstructuraBBDD.COLUMN_EMAIL_USER_CHALLENGE + " TEXT," +
                    EstructuraBBDD.COLUMN_TITLE_CHALLENGE + " TEXT," +
                    EstructuraBBDD.COLUMN_DESCRIPTION_CHALLENGE + " TEXT," +
                    EstructuraBBDD.COLUMN_IMG_CHALLENGE + " BLOB," +
                    EstructuraBBDD.COLUMN_IS_CHECKED_CHALLENGE + " INTEGER);";

}