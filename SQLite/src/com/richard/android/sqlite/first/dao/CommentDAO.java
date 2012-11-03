package com.richard.android.sqlite.first.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.richard.android.sqlite.first.sqlite.MySQLiteHelper;
import com.richard.android.sqlite.first.vo.Comment;

public class CommentDAO {

	// Database fields
	private SQLiteDatabase mDatabase;
	private MySQLiteHelper mDbHelper;
	private String[] mAllColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_COMMENT };

	// En el constructor instanciamos un nuevo MySQLiteHelper
	public CommentDAO(Context context) {
		mDbHelper = new MySQLiteHelper(context);
	}

	// Metodo para abrir la DB en modo lectura y escritura
	public void open() throws SQLException {
		mDatabase = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}
	/**
	 * Metodo para insertar un comment en la DB
	 * 
	 * @param comment cadena con el comentario queremos crear
	 * @return newComment cadena con el comentario que se ha almacenado en la DB
	 * */
	public Comment createComment(String comment) {
		// El content values almacena valores que luego puede leer el ContentResolver
		ContentValues values = new ContentValues();
		// Aquí lo que hacemos es indicar que en la columna comment irá el valor que nos pasan por parámetro
		values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
		// El metodo insert devuelve el id con el que se ha creado este row en la DB
		long insertId = mDatabase.insert(MySQLiteHelper.TABLE_COMMENTS, null,
				values);
		// Ahora hacemos una query buscando el id con el que se ha creado
		Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_COMMENTS,
				mAllColumns, MySQLiteHelper.COLUMN_ID + "=" + insertId,
				null, null, null, null);
		// Movemos el cursor a la primera posición para poder leer desde el inicio
		cursor.moveToFirst();
		
		// Creamos un nuevo cometario cerramos el cursor y devolvemos el comentario
		Comment newComment = cursorToComment(cursor);
		cursor.close();
		return newComment;
	}
	/**
	 * Metodo que borra de la base de datos un comentario
	 * 
	 * @param comment objeto Comment que queremos borrar
	 * */
	public void deleteComment(Comment comment) {
		long id = comment.getId();
		System.out.println("Will delete Comment with id: " + id);
		
		// Indicamos que queremos borrar de la tabla TABLE_COMMENTS la linea con el id que nos pasan por parámetro
		int rowsDeleted = mDatabase.delete(MySQLiteHelper.TABLE_COMMENTS,
				MySQLiteHelper.COLUMN_ID + " = " + id, null);
		
		System.out.println(rowsDeleted + "rows affected");
	}
	/**
	 * Devuelve todos los comentarios almacenados en la base de datos
	 * 
	 * @return commentList lista con todos los comentarios de la base de datos
	 * */
	public List<Comment> getAllComments() {

		List<Comment> commentList = new ArrayList<Comment>();
		
		// Almacenamos en un cursor todas las lineas y columnas de la tabla TABLE_COMMENTS
		Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_COMMENTS,
				mAllColumns, null, null, null, null, null);
		
		// Movemos el cursor al inicio de la tabla
		cursor.moveToFirst();
		
		// Mientras el cursor no llegue al final de la lista continuamos
		while (!cursor.isAfterLast()) {
			Comment comment = cursorToComment(cursor);
			// Insertamos el comentario en la lista
			commentList.add(comment);
			// Movemos el cursor a la siguiente linea
			cursor.moveToNext();
		}
		// Cerramos el cursor y devolvemos la lista de comentarios
		cursor.close();
		return commentList;
	}
	/**
	 * Devuelve un objeto Comment a partir de un cursor
	 * 
	 * @param cursor cursor que apunta a un row de la tabla comment
	 * @return comment objeto comentario resultante
	 * */
	private Comment cursorToComment(Cursor cursor) {
		Comment comment = new Comment();
		comment.setId(cursor.getLong(0));
		comment.setComment(cursor.getString(1));
		return comment;
	}

}
