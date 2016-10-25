package com.slashcoding.whatsappstatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends StatusMenu {
	TextView head;
	ListView catLv;
	EditText searchBox;
	ArrayList<HashMap<String, String>> categoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		copyDatabaseNow();
		DatabaseHandler db = new DatabaseHandler(this);
		searchBox = (EditText) findViewById(R.id.searchBox);
		head = (TextView) findViewById(R.id.tvHead);
		head.setText("Whatsapp Status Categories");
		catLv = (ListView) findViewById(R.id.catList);
		categoryList = db.getAllCategoriesmap();
		final ListAdapter adapter = new SimpleAdapter(MainActivity.this,
				categoryList, R.layout.category_item, new String[] {
						DatabaseHandler.KEY_ID, DatabaseHandler.KEY_NAME,
						DatabaseHandler.KEY_COUNT }, new int[] { R.id.cid,
						R.id.cname, R.id.cCount });
		catLv.setAdapter(adapter);
		catLv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String catId = ((TextView) view.findViewById(R.id.cid))
						.getText().toString();
				String catName = ((TextView) view.findViewById(R.id.cname))
						.getText().toString();
				Intent in = new Intent(getApplicationContext(),
						MessageListing.class);

				in.putExtra(DatabaseHandler.KEY_NAME, catName);
				in.putExtra(DatabaseHandler.KEY_ID, catId);

				startActivity(in);
			}
		});
		searchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				((Filterable) adapter).getFilter().filter(s);
			}

		});
	}

	private void copyDatabaseNow() {
		// TODO Auto-generated method stub
		DataBaseHelper myDbHelper = new DataBaseHelper(this);

		try {

			myDbHelper.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}

		try {

			myDbHelper.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		createStatusMenu(item);
		return false;
	}

}
