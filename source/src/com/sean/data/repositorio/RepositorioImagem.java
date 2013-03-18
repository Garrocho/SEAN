package com.sean.data.repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sean.classe.Imagem;
import com.sean.classe.Imagem.Imagens;
import com.sean.data.DatabaseHelper;

public class RepositorioImagem implements IRepositorio<Imagem> {
	private static final String NOME_TABELA = "imagem";

	private SQLiteDatabase db;

	public RepositorioImagem(Context context) {
		//pega o banco de dados
		db = DatabaseHelper.getInstance(context).getDb();
	}

	private ContentValues createContentValues(Imagem imagem){
		ContentValues valores = new ContentValues();
		valores.put(Imagens.END_IMAGEM, imagem.getEndImagem());
		return valores;
	}

	public long insert(Imagem cidade){
		ContentValues valores = createContentValues(cidade);

		return db.insert(NOME_TABELA, "", valores);
	}
	
	public int deleteTudo(){
		return db.delete(NOME_TABELA, "1", null);
	}

	public int delete(String endImagem){
		return db.delete(NOME_TABELA, String.format("%s=?", Imagens.END_IMAGEM),  new String[]{endImagem});
	}

	public int update(Imagem imagem){
		ContentValues valores = createContentValues(imagem);
		return db.update(NOME_TABELA, valores, String.format("%s=?", Imagens.END_IMAGEM), new String[]{imagem.getEndImagem()});
	}

	public Cursor getCursor(){
		try{
			return db.query(NOME_TABELA, Imagens.COLUNAS, null, null, null, null, null);
		}catch(SQLException e){
			return null;
		}
	}
	
	private Map<String, Integer> getIndicesImagens(Cursor cursor){
		Map<String, Integer> indices = new HashMap<String, Integer>();
		
		int indiceEndImagem = cursor.getColumnIndex(Imagens.END_IMAGEM);
		indices.put(Imagens.END_IMAGEM, indiceEndImagem);

		return indices;
	}
	
	private Imagem createImagem(Cursor cursor){
		Map<String, Integer> indices = getIndicesImagens(cursor);
		
		Imagem imagem = new Imagem();
		imagem.setEndImagem(cursor.getString(indices.get(Imagens.END_IMAGEM)));
		
		return imagem;
	}

	public List<Imagem> listarImagens(){
		Cursor cursor = getCursor();
		List<Imagem> cidades = new ArrayList<Imagem>();

		if(cursor.moveToFirst()){
			do{
				Imagem cidade = createImagem(cursor);
				cidades.add(cidade);

			}while(cursor.moveToNext());
		}

		return cidades;
	}

	public void fecharDB(){

		if(db != null)
			db.close();
	}
}
