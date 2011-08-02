/* 
 * Copyright (C) 2011 OpenIntents.org
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

package org.openintents.historify.data.aggregation;

import org.openintents.historify.data.model.source.EventSource;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class MergedContentObserver extends ContentObserver{

	private EventSource mSource;
	private Uri mUriToNotify;
	private Context mContext;
	
	public MergedContentObserver(Context context, EventSource source, Uri uriToNotify) {
		super(new Handler());
		mContext = context;
		mSource = source;
		mUriToNotify = uriToNotify;
	}

	@Override
	public boolean deliverSelfNotifications() {
		return true;
	}
	
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Log.v(EventAggregator.N,"Contents of "+mSource.getName()+" have been changed.");
		mContext.getContentResolver().notifyChange(mUriToNotify, null);
		
	}
}
