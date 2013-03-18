package com.sean.data.repositorio;

import android.database.Cursor;


public interface IRepositorio<T> {
	//private ContentValues createContentValues(T object);
	public long insert(T object);
	public int deleteTudo();
	public int update(T object);
	public Cursor getCursor();
	//private Map<String, Integer> getIndices(T object);
	//private T create(Cursor cursor);
	//public List<T> listar();
	public void fecharDB();
}
