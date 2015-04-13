package cqu.cqumonk.androidcode.downLoadManager;

import java.util.List;

/**
 * Created by CQUMonk on 2015/4/12.
 * 数据库访问接口，线程有关信息的数据访问操作
 */
public interface ThreadDAO {
    /**
     * 插入线程信息
     * @param threadInfo
     */
    public void InsertThread(ThreadInfo threadInfo);

    /**
     * 根据线程id和url删除线程信息
     * @param thread_id
     * @param url
     */
    public void deleteThread(int thread_id,String url);

    /**
     * 更新线程进度
     * @param thread_id
     * @param url
     * @param finished
     */
    public void updateThreadProgress(int thread_id,String url,int finished);

    /**
     * 查询线程信息，一个下载任务可能由多个线程来执行
     * @param url
     * @return
     */
    public List<ThreadInfo> queryThreads(String url);

    /**
     * 判断线程信息是否存在
     * @param thread_id
     * @param url
     * @return
     */
    public boolean isExists(int thread_id,String url);
}
