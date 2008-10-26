/* 
 * Copyright (C) 2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openintents.flashlight;

import org.openintents.distribution.AboutActivity;
import org.openintents.distribution.EulaActivity;
import org.openintents.distribution.Update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;

public class Flashlight extends Activity {
	
	private static final String TAG = "Flashlight";

	private static final int MENU_COLOR = Menu.FIRST + 1;
	private static final int MENU_ABOUT = Menu.FIRST + 2;

    private static final int REQUEST_CODE_PICK_COLOR = 1;
	
	private LinearLayout mBackground;
	

	private PowerManager.WakeLock mWakeLock;
	private boolean mWakeLockLocked = false;
	
	private int mColor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		if (!EulaActivity.checkEula(this)) {
			return;
		}
		Update.check(this);

		// Turn off the title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
        setContentView(R.layout.main);
        
        mColor = 0xffffffff;
        
        mBackground = (LinearLayout) findViewById(R.id.background);
        
        mBackground.setBackgroundColor(mColor);
        

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
				"Flashlight");
    }
    
    

	@Override
	protected void onPause() {
		super.onPause();
		
		wakeUnlock();
	}



	@Override
	protected void onResume() {
		super.onResume();
		
		wakeLock();
	}

	/////////////////////////////////////////////////////
	// Menu
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_COLOR, 0,R.string.color)
		  .setIcon(android.R.drawable.ic_menu_manage).setShortcut('3', 'c');
        
		menu.add(0, MENU_ABOUT, 0, R.string.about)
		  .setIcon(android.R.drawable.ic_menu_info_details) .setShortcut('0', 'a');

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_COLOR:
            pickColor();
            return true;
        
		case MENU_ABOUT:
			showAboutBox();
			return true;

		}
		return super.onOptionsItemSelected(item);

	}

	/////////////////////////////////////////////////////
	// Other functions
	
	private void wakeLock() {
		if (!mWakeLockLocked) {
			Log.d(TAG, "WakeLock: locking");
			mWakeLock.acquire();
			mWakeLockLocked = true;
		}
	}

	private void wakeUnlock() {
		if (mWakeLockLocked) {
			Log.d(TAG, "WakeLock: unlocking");
			mWakeLock.release();
			mWakeLockLocked = false;
		}
	}
	

	private void showAboutBox() {
		startActivity(new Intent(this, AboutActivity.class));
	}
	
	private void pickColor() {
		Intent i = new Intent();
		i.setAction(ColorPickerActivity.INTENT_PICK_COLOR);
		i.putExtra(ColorPickerActivity.EXTRA_COLOR, mColor);
		startActivityForResult(i, REQUEST_CODE_PICK_COLOR);
	}
	
	/////////////////////////////////////////////////////
	// Color changed listener:
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case REQUEST_CODE_PICK_COLOR:
			if (resultCode == RESULT_OK) {
				mColor = data.getIntExtra(ColorPickerActivity.EXTRA_COLOR, mColor);
		        mBackground.setBackgroundColor(mColor);
			}
			break;
		}
	}
	
}