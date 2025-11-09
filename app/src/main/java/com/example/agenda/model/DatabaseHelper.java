package com.example.agenda.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "contatos.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // criacção do banco
        db.execSQL("CREATE TABLE IF NOT EXISTS contato (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nome TEXT NOT NULL, "+
                "email TEXT, "+
                "telefone TEXT NOT NULL, "+
                "foto TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //  migration - alteração de versão do banco de dados
        if (oldVersion < 2){
            db.execSQL("DROP TABLE IF EXISTS contato;");
            onCreate(db);
        }
    }
}
