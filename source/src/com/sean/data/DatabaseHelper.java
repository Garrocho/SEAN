package com.sean.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper {
	private SQLHelper sqlHelper;
	private SQLiteDatabase db;

	private static final String NOME_BANCO = "sean";
	private static final int VERSAO = 0;
	private static DatabaseHelper banco;

	private static final String[] DATABASE_TABLES = {
		"imagens",
	};

	private static final String[] DATABASE_TABELAS_REMOVIDAS = {

	};

	private static final String[] DATABASE_CREATE = new String[]{
	"CREATE TABLE IF NOT EXISTS imagens (" +
		"email VARCHAR(50) PRIMARY KEY, " +
	");"
	};

	public DatabaseHelper(Context context) {

		// cria o SQLHelper
		sqlHelper = new SQLHelper(context, NOME_BANCO, VERSAO, DATABASE_TABLES, DATABASE_CREATE, DATABASE_TABELAS_REMOVIDAS);

		//abre o banco de dados para escrita e leitura
		db = sqlHelper.getWritableDatabase();
	}

	public static DatabaseHelper getInstance(Context context){
		if(banco == null || !banco.db.isOpen()){
			banco = new DatabaseHelper(context);
		}
		return banco;
	}

	public void fechar(){
		if(db != null && db.isOpen()){
			db.close();
		}
		if(sqlHelper != null){
			sqlHelper.close();
		}
	}

	public SQLiteDatabase getDb() {
		return db;
	}
}