package cqu.cqumonk.androidcode.downLoadManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CQUMonk on 2015/4/12.
 * 数据访问接口的实现
 */
public class ThreadDAOImpl implements ThreadDAO {
    private DBHelper mDBhelper=null;

    public ThreadDAOImpl(Context context) {
        this.mDBhelper = new DBHelper(context);
    }

    @Override
    public void InsertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db=mDBhelper.getWritableDatabase();
        db.execSQL(
                "insert into tb_thread_info(thread_id, url, start, end, finished) values(?,?,?,?,?)",
                new Object[]{threadInfo.getThreadId(),
                        threadInfo.getUrl(),
                        threadInfo.getStart(),
                        threadInfo.getEnd(),
                        threadInfo.getFinished()}
        );
        db.close();

    }

    @Override
    public void deleteThread(int thread_id, String url) {

        SQLiteDatabase db=mDBhelper.getWritableDatabase();
        db.execSQL("delete from tb_thread_info where thread_id= ? and url= ?",
                new Object[]{thread_id,url}
        );
        db.close();
    }

    @Override
    public void updateThreadProgress(int thread_id, String url, int finished) {

        SQLiteDatabase db=mDBhelper.getWritableDatabase();
        db.execSQL("update tb_thread_info set finished= ? where thread_id= ? and url= ?",
                new Object[]{finished,thread_id,url}
        );
        db.close();
    }

    @Override
    public List<ThreadInfo> queryThreads(String url) {
        SQLiteDatabase db=mDBhelper.getReadableDatabase();
        Cursor cursor=null;
        List<ThreadInfo> threads=new ArrayList<ThreadInfo>();

        cursor=db.rawQuery("select * from tb_thread_info where url = ? ",
                new String[]{url}
        );
        if (cursor!=null){

            while (cursor.moveToNext()){
                ThreadInfo threadInfo = new ThreadInfo();
                threadInfo.setThreadId(cursor.getInt(1));
                threadInfo.setUrl(cursor.getString(2));
                threadInfo.setStart(cursor.getInt(3));
                threadInfo.setEnd(cursor.getInt(4));
                threadInfo.setFinished(cursor.getInt(5));
                threads.add(threadInfo);
            }
        }
        cursor.close();
        db.close();
        return threads;
    }

    @Override
    public boolean isExists(int thread_id, String url) {
        boolean exits=false;
        SQLiteDatabase db=mDBhelper.getReadableDatabase();
        Cursor cursor=null;

        cursor=db.rawQuery("select * from tb_thread_info where thread_id= ? and url = ? ",
                new String[]{thread_id+"",url}
        );
        if (cursor!=null){
            exits=cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return exits;
    }
}
