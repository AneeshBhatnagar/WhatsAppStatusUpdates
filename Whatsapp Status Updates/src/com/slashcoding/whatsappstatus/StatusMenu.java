package com.slashcoding.whatsappstatus;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public class StatusMenu extends Activity {

	String emailid[] = { "admin@slashcoding.com" };
	Intent send;

	public void createStatusMenu(MenuItem item) {
		send = new Intent(android.content.Intent.ACTION_SEND);
		send.putExtra(android.content.Intent.EXTRA_EMAIL, emailid);
		send.setType("plain/text");
		switch (item.getItemId()) {
		case R.id.reportBug:
			send.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Reporting a Bug in Whatsapp Status Android Application");
			break;
		case R.id.email:
			send.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Contacting Regarding WhatsApp Status Updates Application");
			break;
		}
		startActivity(Intent.createChooser(send, "Send Email"));
	}
}
