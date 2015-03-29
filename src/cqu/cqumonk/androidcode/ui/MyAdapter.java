package cqu.cqumonk.androidcode.ui;

import java.util.List;



import cqu.cqumonk.androidcode.R;
import cqu.cqumonk.androidcode.model.MyItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 此adapter为主界面的listview提供数据适配
 * item中包括图片，标题，和内容
 * @author cqumonk
 *
 */
public class MyAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<MyItem> myItems;

	public MyAdapter(Context context,List<MyItem> myItems) {
		mInflater=LayoutInflater.from(context);
		this.myItems=myItems;
	}

	@Override
	public int getCount() {
		if(myItems==null)
		{
			return 0;
		}
		return myItems.size();
	}

	@Override
	public Object getItem(int position) {
		if(myItems!=null){
			return myItems.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyItem item=this.myItems.get(position);
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=this.mInflater.inflate(R.layout.item_mylv, parent,false);
			holder=new ViewHolder();
			holder.imgView=(ImageView) convertView.findViewById(R.id.item_img);
			holder.titleTextView=(TextView) convertView.findViewById(R.id.item_tittle);
			holder.contentTextView=(TextView) convertView.findViewById(R.id.item_content);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		if (item!=null) {
			holder.imgView.setImageResource(item.getImageID());
			holder.titleTextView.setText(item.getTitle());
			holder.contentTextView.setText(item.getContent());
		}
		
		return convertView;
	}
	
	private class ViewHolder{
		private ImageView imgView;
		private TextView titleTextView;
		private TextView contentTextView;
	}

}
