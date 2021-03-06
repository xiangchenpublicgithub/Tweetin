package io.github.mthli.Tweetin.Database.Mention;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MentionHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MENTION_6.db";
    private static final int DATABASE_VERSION = 6;

    public MentionHelper(Context context) {
        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(MentionRecord.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        /* Do nothing */
    }
}
