package org.openintents.historify.data.providers;

/**
 * Definitions for supported event provider columns.
 */
public final class Events {

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.historify.event";
	public static final String ITEM_CONTENT_TYPE ="vnd.android.cursor.item/vnd.historify.event";
	
	/**
	 * Long. Required, key field.
	 */
	public final static String _ID = "_id";
	
	/**
	 * String. Optional, for avoiding duplicates. Providers decide how to interpret
	 * this field.
	 */
	public final static String EVENT_KEY = "event_key";

	/**
	 * String. Required, short description of the event.
	 */
	public final static String MESSAGE = "message";

	/**
	 * Long (UNIX Time). Required, to support default sorting order.
	 */
	public final static String PUBLISHED_TIME = "published_time";

	/**
	 * LookupKey of the contact associated with the event. Optional. 
	 */
	public final static String CONTACT_KEY = "contact_key";
	
	/**
	 * Enum of {@link #Originator}. Optional, default value is {@link Originator#both} if {@link #CONTACT_KEY} has been set.
	 */
	public final static String ORIGINATOR = "originator";

	/**
	 * Enum used as values of the {@link #ORIGINATOR} field
	 */
	public enum Originator {
		user, contact, both
	}
}