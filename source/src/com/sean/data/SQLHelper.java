package com.sean.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

	private String[] tabelas;
	private String[] create;
	private String[] tabelasRemovidas;
	private final String sufixoTabelaTemp = "_temp";

	public SQLHelper(Context context, String name, int version, String[] tabelas, String[] create, String[] tabelasRemovidas) {
		super(context, name, null, version);

		this.tabelas = tabelas; //vetor de tabelas
		this.create = create; //vetor de comando CREATE TABLE tabela(campos)
		this.tabelasRemovidas = tabelasRemovidas; //vetor com as tabelas que foram removidas do banco
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//apaga as tabelas que forma removidas do banco
		for(String tabelaRemovida : tabelasRemovidas){
			apagaTabela(db, tabelaRemovida);
		}
		
		//crias as tabelas
		for(int i = 0; i < create.length; i++){
			db.execSQL(create[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		recriaBanco(db, oldVersion, newVersion);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		recriaBanco(db, oldVersion, newVersion);
	}

	// recria o banco com os dados existentes
	private void recriaBanco(SQLiteDatabase db, int oldVersion, int newVersion){
		for(String tabela : tabelas){
			if(tabelaExists(db, tabela)){
				criaTabelaTemp(db, tabela);
			}
			apagaTabela(db, tabela);
		}

		//cria uma nova base de dados
		onCreate(db);

		for(String tabela : tabelas){
			if(tabelaExists(db, tabela)){
				try{
					repopula(db, tabela);
				}
				catch(SQLiteException e){
					Log.d("Excecao ao repopupar banco: ", e.getMessage());
					apagaRegistrosTabela(db, tabela);
					Log.d("Registros removidos: ", String.format("Tabela %s", tabela));
				}
				apagaTabelaTemp(db, tabela);
			}
		}
	}
	
	private void apagaRegistrosTabela(SQLiteDatabase db, String tabela){
		db.execSQL(String.format("TRUNCATE TABLE IF EXISTS %s", tabela));
	}

	// apaga uma tabela
	private void apagaTabela(SQLiteDatabase db, String tabela){
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", tabela));
	}

	// apaga a tabela temp
	private void apagaTabelaTemp(SQLiteDatabase db, String tabela){
		apagaTabela(db, tabela + sufixoTabelaTemp);
	}

	// cria a tabela temp, adicionao o sufixo sufixoTabelaTemp ao nome da tabela
	private void criaTabelaTemp(SQLiteDatabase db, String tabela){
		String tabelaTemp = String.format("%s%s", tabela, sufixoTabelaTemp);
		String queryCriaTabelaTemp = String.format("CREATE TABLE %s AS SELECT * FROM %s", tabelaTemp, tabela);
		db.execSQL(queryCriaTabelaTemp);
	}

	//pega os indices das colunas
	private int[] getIndicesColunas(Cursor cursorSelect){
		String[] nomeColunas = cursorSelect.getColumnNames();
		int[] indices = new int[nomeColunas.length];
		for(int i = 0; i < indices.length; i++){
			indices[i] = cursorSelect.getColumnIndex(nomeColunas[i]);
		}
		return indices;
	}

	//cria uma String com o comando Insert
	private String createInsertString(int[] indices, String tabela, Cursor cursorDadosTabelaTemp, Cursor cursorTabelaNova){
		StringBuilder colunasTemp = new StringBuilder(); // (col1, col2, col...)
		StringBuilder values = new StringBuilder(); // (?, ?, ?...)

		for(int indice : indices){
			String coluna = cursorDadosTabelaTemp.getColumnName(indice);

			// Verifica se a coluna de temp existe na da nova estrutura da tabela
			if(cursorTabelaNova.getColumnIndex(coluna) != -1){
				colunasTemp.append(coluna + ",");
				values.append("?,");
			}
		}

		// Apaga a ultima virgula
		colunasTemp.deleteCharAt(colunasTemp.length()-1);
		values.deleteCharAt(values.length()-1);

		String queryInsert = String.format("INSERT INTO %s(%s) values(%s)", tabela, colunasTemp, values);
		return queryInsert;
	}

	//retorna os valores do cursor
	private String[] getValoresCursor(int[] indices, Cursor cursorDadosTabelaTemp, Cursor cursorTabelaNova){
		List<String> valoresTemp = new ArrayList<String>(); // valor1, valor2, valor...

		for(int indice : indices){
			String valor = cursorDadosTabelaTemp.getString(indice); //pega tudo como String
			String coluna = cursorDadosTabelaTemp.getColumnName(indice);

			// Verifica se a coluna de temp existe na da nova estrutura da tabela
			if(cursorTabelaNova.getColumnIndex(coluna) != -1){
				valoresTemp.add(valor);
			}
		}

		return valoresTemp.toArray(valoresTemp.toArray(new String[valoresTemp.size()]));
	}

	//repopula a tabela usando os dados da tabela temporaria
	private void repopula(SQLiteDatabase db, String tabela) throws SQLiteException{
		Cursor cursorDadosTabelaTemp = db.rawQuery(String.format("SELECT * FROM %s%s", tabela, sufixoTabelaTemp), null);
		Cursor cursorTabelaNova = db.rawQuery(String.format("SELECT * FROM %s", tabela), null);

		int[] indices = getIndicesColunas(cursorDadosTabelaTemp);

		// itera pelos valores da tabela e adiciona ao ao List
		if(cursorDadosTabelaTemp.moveToFirst()){

			// cria a string para insert, algo como: INSERT INTO tabela(col1, col2) values(?, ?)
			String queryInsert = createInsertString(indices, tabela, cursorDadosTabelaTemp, cursorTabelaNova);

			do{
				// pega os valores das colunas
				String[] valoresTemp = getValoresCursor(indices, cursorDadosTabelaTemp, cursorTabelaNova);

				// Insere os dados da tabela temp na nova tabela
				db.execSQL(queryInsert, valoresTemp);

			}while(cursorDadosTabelaTemp.moveToNext());
		}
	}

	//verifica se a tabela existe
	private boolean tabelaExists(SQLiteDatabase db, String tabela){
		boolean tabelaExiste = false;
		try{
			@SuppressWarnings("unused")
			Cursor cursor = db.query(tabela, null, null, null, null, null, null);
			tabelaExiste = true;
		}
		catch (SQLiteException e){
			if (e.getMessage().toString().contains("no such table")){
				tabelaExiste = false;
			}
		}
		return tabelaExiste;
	}

}
