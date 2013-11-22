package com.lathia.profilemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lathia.profilemanager.db.tables.AbstractTable;

public abstract class AbstractProfileDatabase extends SQLiteOpenHelper
{
	private final static int dbVersion = 2;
	
	public AbstractProfileDatabase(final Context context, final String databaseId)
	{
		super(context, databaseId, null, dbVersion);
	}
	
	protected void reset(final AbstractTable table, final SQLiteDatabase database)
	{
		table.removeContents(database);
		table.dropTable(database);
		table.createTable(database);
	}
	
	public abstract void deleteAll();
	
}
