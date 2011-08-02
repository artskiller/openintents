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

package org.openintents.historify.data.adapters;

import org.openintents.historify.R;
import org.openintents.historify.data.loaders.SourceFilterLoader;
import org.openintents.historify.data.loaders.SourceLoader;
import org.openintents.historify.data.model.Contact;
import org.openintents.historify.data.model.source.EventSource;
import org.openintents.historify.uri.ContentUris;

import android.content.Context;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SourceFiltersAdapter extends SourcesAdapter {

	private Contact mContact;
	private boolean mHasFilters;
	
	private SourceFilterLoader mSourceFilterLoader;
	
	public SourceFiltersAdapter(Context context, ListView listView, Contact contact) {
		super();
		mContact = contact;
		mSourceFilterLoader = new SourceFilterLoader(contact);
		
		//check if contact has previously defined filters
		mHasFilters = mSourceFilterLoader.hasFilters(context);
		
		//If the current contact hasnt got any filters, the default values will be shown.
		//We use a simple SourceLoader for that purpose.
		//If the user modifies a list element, the loader will be changed to the SourceFilterLoader instance.
		init(context, listView, mHasFilters ? mSourceFilterLoader : new SourceLoader(ContentUris.Sources), R.layout.listitem_source_filter);
	}

	@Override
	public int getCount() {
		return super.getCount()-1;
	}

	@Override
	protected void initListItem(View convertView) {
		
	}
	
	@Override
	protected void loadItemToView(View convertView, EventSource item, int position) {
		
		TextView tv = (TextView) convertView.findViewById(R.id.sources_listitem_txtName);
		tv.setText(item.getName());
		
		((CheckedTextView)tv).setChecked(item.isEnabled());
		mCheckedItems.put(position, item.isEnabled());
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.sources_listitem_imgIcon);
		mSourceIconHelper.toImageView(mContext, item,null,iv);
		
	}
	
	@Override
	public void update(EventSource source) {
		
		if(!mHasFilters) {
			mSourceFilterLoader.insertFiltersForContact(mContext, mSources);
			mHasFilters = true;
			mSourceLoader = mSourceFilterLoader;
			load();
		} else {
			super.update(source);	
		}
		
	}
	
}
