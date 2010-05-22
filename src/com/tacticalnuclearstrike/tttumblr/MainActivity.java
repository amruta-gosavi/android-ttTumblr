package com.tacticalnuclearstrike.tttumblr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tacticalnuclearstrike.tttumblr.activites.AccountActivity;
import com.tacticalnuclearstrike.tttumblr.activites.Dashboard;
import com.tacticalnuclearstrike.tttumblr.activites.PostConversationActivity;
import com.tacticalnuclearstrike.tttumblr.activites.PostLinkActivity;
import com.tacticalnuclearstrike.tttumblr.activites.PostQuoteActivity;
import com.tacticalnuclearstrike.tttumblr.activites.PostTextActivity;
import com.tacticalnuclearstrike.tttumblr.activites.SettingsActivity;
import com.tacticalnuclearstrike.tttumblr.activites.UploadImageActivity;
import com.tacticalnuclearstrike.tttumblr.activites.UploadVideoActivity;

public class MainActivity extends Activity {

	final int MENU_ACCOUNT = 1;
	final int MENU_ABOUT = 2;
	final int MENU_SETTINGS = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupButtons();

		CheckIsUserNameAndPasswordCorrect();
	}

	private void setupButtons() {
		findViewById(R.id.postTextBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent startPostText = new Intent(MainActivity.this,
								PostTextActivity.class);
						startActivity(startPostText);
					}
				});

		findViewById(R.id.postImageBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								UploadImageActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.postVideoBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								UploadVideoActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.postQuoteBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								PostQuoteActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.postLinkBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								PostLinkActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.postConversationBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								PostConversationActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.dashboardBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(MainActivity.this,
								Dashboard.class));
					}
				});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, MENU_ACCOUNT, 0, "Account");
		menu.add(0, MENU_ABOUT, 0, "About");
		menu.add(0, MENU_SETTINGS, 0, "Settings");
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ACCOUNT:
			startActivityForResult(new Intent(MainActivity.this,
					AccountActivity.class), 0);
			return true;
		case MENU_ABOUT:
			createAboutDialog();
			return true;
		case MENU_SETTINGS:
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		CheckIsUserNameAndPasswordCorrect();
	}

	private void createAboutDialog() {
		PackageManager pm = getPackageManager();
		String version = "r0";
		try {
			PackageInfo pi = pm.getPackageInfo(
					"com.tacticalnuclearstrike.tttumblr", 0);
			version = pi.versionName;
		} catch (NameNotFoundException e) {

		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder
				.setMessage(
						"ttTumblr "+ version +"\n\nIf you find any errors please contact me so that I can fix them.\n\nKnown issues: selecting an image from gallery multiple times causes a crash.")
				.setCancelable(true).setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void CheckIsUserNameAndPasswordCorrect() {
		TextView infoView = (TextView) findViewById(R.id.labelAuthStatus);

		TumblrApi api = new TumblrApi(this);
		if (!api.isUserNameAndPasswordStored()) {
			infoView
					.setText("Please press menu and select Account to enter email and password.");
			infoView.setVisibility(View.VISIBLE);
		} else {
			infoView.setVisibility(View.GONE);
		}
	}
}