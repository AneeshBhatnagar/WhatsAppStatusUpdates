package com.slashcoding.whatsappstatus;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
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

public class MessageListing extends StatusMenu {
	TextView head;
	ListView msgLv;
	String catName, catId;
	EditText searchB;
	ArrayList<HashMap<String, String>> messageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_listing);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		DatabaseHandler db = new DatabaseHandler(this);
		head = (TextView) findViewById(R.id.tvHead);
		msgLv = (ListView) findViewById(R.id.msgList);
		searchB = (EditText) findViewById(R.id.searchMsg);
		Intent inp = getIntent();
		catName = inp.getStringExtra(DatabaseHandler.KEY_NAME);
		catId = inp.getStringExtra(DatabaseHandler.KEY_ID);
		head.setText(catName + " Status");
		messageList = db.getCategoryMessages(Integer.parseInt(catId));
		final ListAdapter adapter = new SimpleAdapter(MessageListing.this,
				messageList, R.layout.msg_item, new String[] {
						DatabaseHandler.KEY_ID, DatabaseHandler.KEY_MESSAGE },
				new int[] { R.id.mid, R.id.mess });
		msgLv.setAdapter(adapter);
		msgLv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String msg = ((TextView) view.findViewById(R.id.mess))
						.getText().toString();
				Intent in = new Intent(getApplicationContext(),
						SingleMessage.class);

				in.putExtra(DatabaseHandler.KEY_NAME, catName);
				in.putExtra(DatabaseHandler.KEY_MESSAGE, msg);

				startActivity(in);
			}
		});
		searchB.addTextChangedListener(new TextWatcher() {

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		createStatusMenu(item);
		return false;
	}
}
