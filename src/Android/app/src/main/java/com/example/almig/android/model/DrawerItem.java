package com.example.almig.android.model;

import android.graphics.drawable.Drawable;

public class DrawerItem {
	/* Commented tags are expected in future updates.
	 */
	public static final int DRAWER_ITEM_TAG_HOME = 1;
	public static final int DRAWER_ITEM_TAG_DASHBOARD = 2;
	public static final int DRAWER_ITEM_TAG_PATHFINDING = 3;
	public static final int DRAWER_ITEM_TAG_SEARCH = 4;
	public static final int DRAWER_ITEM_TAG_SOCIAL = 5;
	public static final int DRAWER_ITEM_TAG_STAMP_AUTH = 6;
	
	public DrawerItem(Drawable icon, int title, int tag) {
		this.icon = icon;
		this.title = title;
		this.tag = tag;
	}

	private Drawable icon;
	private int title;
	private int tag;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}
