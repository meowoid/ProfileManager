package com.lathia.profilemanager.db.tables;

import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractTable
{
	protected final String tableName;
	
	public AbstractTable(final String tableName)
	{
		this.tableName = tableName;
	}
	
	public abstract void createTable(final SQLiteDatabase database);
	
	public void dropTable(final SQLiteDatabase database)
	{
		database.execSQL("DROP TABLE IF EXISTS " + tableName);
	}
	
	public void upgradeTable(final SQLiteDatabase database)
	{
		dropTable(database);
		createTable(database);
	}
}
