package fun.hxy.com.jddragslidelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 功能描述:
 * Created by huangxy on 2016/7/20.
 */
public class MyRecycleViewHolder extends RecyclerView.ViewHolder {
	//所有的子控件
	private SparseArray<View> mViews;
	//itemview
	private View mConvertView;
	private MyRecycleAdapter adapter;
	private Context context;

	//初始化的设置
	protected MyRecycleViewHolder(Context context, View itemView) {
		super(itemView);
		this.context = context;
		this.mViews = new SparseArray<>();
		mConvertView = itemView;
	}

	public <T> MyRecycleViewHolder(Context context, View itemView, final MyRecycleAdapter<T> adapter) {
		super(itemView);
		this.context = context;
		this.mViews = new SparseArray<>();
		mConvertView = itemView;
		if (adapter.getItemClickListener() != null) {
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					adapter.getItemClickListener().onItemClickListener(v,getAdapterPosition() - adapter.getHeaderViewsCount());
				}
			});
		}
		this.adapter = adapter;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 *
	 * @param viewId 组件id
	 * @return 当前组件
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		try {
			return (T) view;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 为TextView设置字符串
	 *
	 * @param viewId 组件ID
	 * @param text   显示的文字
	 */
	public void setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
	}



	/**
	 * 设置图片
	 *
	 * @param resId
	 * @param path
	 */
	public void setImage(int resId, String path) {
		ImageView view = getView(resId);
		if (view!=null) {

		}
	}
	/**
	 * 设置图片
	 *
	 * @param resId
	 * @param path
	 */
	public void setImageCrop(int resId, String path) {
		ImageView view = getView(resId);
		if (view!=null) {

		}
	}



	/**
	 * 设置资源图片
	 *
	 * @param resId
	 * @param drawableId
	 */
	public void setImage(int resId, Context context, int drawableId) {
		ImageView tv = getView(resId);
		if (tv != null) {
			tv.setImageDrawable(context.getResources().getDrawable(drawableId));
		}
	}

}
