package cqu.cqumonk.androidcode.downLoadManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CQUMonk on 2015/4/12.
 * 该类用于完成数据库中线程表的创建与更新
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="download.db";
    private static final int DB_VERSION=1;

    //该表用于存放线程信息
    private static final String SQL_CREATE="create table tb_thread_info(_id integer primary key autoincrement," +
            "thread_id integer, url text, start integer, end integer, finished integer)";
    private static final  String SQL_DROP="drop table if exists tb_thread_info";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
