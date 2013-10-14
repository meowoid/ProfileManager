package com.lathia.profilemanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lathia.profilemanager.db.tables.distribution.FrequencyTable;

public abstract class AbstractProfileDatabase extends SQLiteOpenHelper
{
	private final static int dbVersion = 1;
	
	public AbstractProfileDatabase(final Context context, final String databaseId)
	{
		super(context, databaseId, null, dbVersion);
	}
	
	protected void reset(final FrequencyTable table, final SQLiteDatabase database)
	{
		table.dropTable(database);
		table.createTable(database);
	}
	
	public abstract void deleteAll();
	
}
