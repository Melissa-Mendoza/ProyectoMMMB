package com.example.casa.proyectommmb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Casa on 26/08/2016.
 */
public class DBHelperMMMB extends SQLiteOpenHelper {
    public static final String DB_NOMBRE = "CNE_MMMB.db";
    public static final String TABLA_NOMBRE = " VOTANTES_MMMB";
    public static final String COL_1 = "Id";
    public static final String COL_2 = "Nombre";
    public static final String COL_3 = "Apellido";
    public static final String COL_4 = "RecintoElectolar";
    public static final String COL_5 = "AnoNacimiento";

    public DBHelperMMMB(Context context) {
        super(context, DB_NOMBRE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create tabla %s(ID INTEGER PRIMARY KEY AUTOINCREMENT,%S TEXT,%S TEXT,%S TEXT,%s INTEGER)", TABLA_NOMBRE,COL_2,COL_3,COL_4,COL_5));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLA_NOMBRE));
        onCreate(db);
    }

    public boolean insertar(String nombre, String apellido, String recintoElectoral, int anoNacimiento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nombre);
        contentValues.put(COL_3, apellido);
        contentValues.put(COL_4, recintoElectoral);
        contentValues.put(COL_5, anoNacimiento);
        long resultado = db.insert(TABLA_NOMBRE, null, contentValues);
        if (resultado == -1)
            return false;
        else
            return true;
    }

    public Cursor verTodos(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery(String.format("Select * from %s", TABLA_NOMBRE), null);
        return res;
    }
}
