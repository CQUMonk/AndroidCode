package cqu.cqumonk.androidcode.model;

public class MyItem {

	private int imageID;
	private String title;
	private String content;

	public MyItem() {
	}

	public MyItem(int imageID, String title, String content) {
		super();
		this.imageID = imageID;
		this.title = title;
		this.content = content;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
