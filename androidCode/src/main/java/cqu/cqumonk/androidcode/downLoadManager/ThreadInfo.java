package cqu.cqumonk.androidcode.downLoadManager;

import java.io.Serializable;

/**
 * Created by CQUMonk on 2015/4/11.
 */
public class ThreadInfo implements Serializable {
    private int threadId;
    private String url;
    //下载的开始位置，结束位置，和完成进度
    private int start;
    private int end;
    private int finished;

    public ThreadInfo() {
    }

    public ThreadInfo(int threadId, String url, int start, int end, int finished) {

        this.threadId = threadId;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "threadId=" + threadId +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
