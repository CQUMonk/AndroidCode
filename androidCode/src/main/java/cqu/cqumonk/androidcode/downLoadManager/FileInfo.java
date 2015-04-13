package cqu.cqumonk.androidcode.downLoadManager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CQUMonk on 2015/4/11.
 */
public class FileInfo implements Parcelable{
    private int fileId;
    private String fileName;
    private String url;
    private int finished;
    private int length;

    public FileInfo() {
    }

    public FileInfo(int fileId, String fileName, String url, int finished, int length) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.url = url;
        this.finished = finished;
        this.length = length;
    }

    private FileInfo(Parcel source){
        readFromParcel(source);
    }
    public static final Creator<FileInfo> CREATOR=new Creator<FileInfo>() {
        @Override
        public FileInfo createFromParcel(Parcel source) {
            return new FileInfo(source);
        }

        @Override
        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };

    /**
     * 写入变量和读取变量的顺序应该一致 不然得不到正确的结果
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fileId);
        dest.writeString(fileName);
        dest.writeString(url);
        dest.writeInt(finished);
        dest.writeInt(length);

    }
    public void readFromParcel(Parcel source){
        this.fileId=source.readInt();
        this.fileName=source.readString();
        this.url=source.readString();
        this.finished=source.readInt();
        this.length=source.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", finished=" + finished +
                ", length=" + length +
                '}';
    }


}
