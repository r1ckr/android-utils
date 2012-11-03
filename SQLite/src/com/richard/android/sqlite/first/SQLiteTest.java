package com.richard.android.sqlite.first;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

import com.richard.android.sqlite.first.dao.CommentDAO;
import com.richard.android.sqlite.first.vo.Comment;

public class SQLiteTest extends ListActivity {

	private CommentDAO mCommentDAO;
	private ArrayAdapter<Comment> mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_test);

		mCommentDAO = new CommentDAO(this);

		mCommentDAO.open();

		List<Comment> values = mCommentDAO.getAllComments();

		mAdapter = new ArrayAdapter<Comment>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(mAdapter);
	}

	public void onClick(View view) {

		//ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
		Comment comment = null;

		switch (view.getId()) {
		case R.id.add:
			String[] comments = new String[] { "Bien", "Estupendo",
					"Comentario bueno" };
			int nextInt = new Random().nextInt(3);
			// Creamos un comentario seleccionando aleatoriamente uno de los string anteriores
			comment = mCommentDAO.createComment(comments[nextInt]);
			// Agregamos al adaptador el nuevo comentario
			mAdapter.add(comment);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				comment = mAdapter.getItem(0);
				mCommentDAO.deleteComment(comment);
				mAdapter.remove(comment);
			}
			break;
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		mCommentDAO.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mCommentDAO.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sqlite_test, menu);
		return true;
	}
}
