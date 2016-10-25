package com.slashcoding.whatsappstatus;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleMessage extends StatusMenu {
	TextView msg, head;
	ImageView copy, share;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_message);
		head = (TextView) findViewById(R.id.tvHead);
		msg = (TextView) findViewById(R.id.singleMsg);
		copy = (ImageView) findViewById(R.id.iVCopy);
		share = (ImageView) findViewById(R.id.iVShare);
		Intent inp = getIntent();
		head.setText(inp.getStringExtra(DatabaseHandler.KEY_NAME) + " Status");
		final String statusmsg = inp
				.getStringExtra(DatabaseHandler.KEY_MESSAGE);
		msg.setText(statusmsg);
		copy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("simple text", statusmsg);
				clipboard.setPrimaryClip(clip);
				Toast.makeText(getApplicationContext(),
						"Text Copied to Clipboard", Toast.LENGTH_LONG).show();
			}

		});

		copy.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"Click to Copy this Status", Toast.LENGTH_LONG).show();
				return false;
			}

		});

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharer = new Intent(android.content.Intent.ACTION_SEND);
				sharer.setType("text/plain");
				sharer.putExtra(android.content.Intent.EXTRA_TEXT, statusmsg);
				startActivity(Intent.createChooser(sharer, "Share Via"));
			}

		});

		share.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"Click to Share this Status Update", Toast.LENGTH_LONG)
						.show();
				return false;
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
