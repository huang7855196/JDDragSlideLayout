package fun.hxy.com.jddragslidelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:RecycleView的适配器
 * Created by huangxy on 2016/7/20.
 */
public abstract class MyRecycleAdapter<T> extends RecyclerView.Adapter<MyRecycleViewHolder> {
	private List<View> mHeaderViews = new ArrayList<>(); //头视图
	private List<View> mFooterViews = new ArrayList<>();   //尾视图

	private List<Integer> mHeaderViewTypes = new ArrayList<>();//头布局的type
	private List<Integer> mFooterViewTypes = new ArrayList<>();//脚布局的type

	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;

	public MyRecycleAdapter(Context context, List<T> mDatas) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
	}

	/**
	 * 添加头视图
	 * @param headerView
	 */
	public void addHeaderView(View headerView) {
		mHeaderViews.add(headerView);
	}

	/**
	 * 添加脚视图
	 * @param footerView 尾视图
	 */
	public void addFooterView(View footerView) {
		mFooterViews.add(footerView);
	}
	//设置view的类型，头布局跟脚布局
	@Override
	public int getItemViewType(int position) {
		if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) {
			//用position作为HeaderView 的   ViewType标记
			//记录每个ViewType标记
			mHeaderViewTypes.add(position * 100000);
			return position * 100000;
		}

		if (mFooterViews.size() > 0 && position > getAdvanceCount() - 1 + mHeaderViews.size()) {
			//用position作为FooterView 的   ViewType标记
			//记录每个ViewType标记
			mFooterViewTypes.add(position * 100000);
			return position * 100000;
		}

		if (mHeaderViews.size() > 0) {
			return getAdvanceViewType(position - mHeaderViews.size());
		}
		return getAdvanceViewType(position);
	}

	/**
	 * Item页布局类型个数，默认为1
	 * @param position
	 */
	public int getAdvanceViewType(int position) {
		return 1;
	}

	private int getAdvanceCount() {
		if (mDatas != null) {
			return mDatas.size();
		}
		return 0;
	}
	//绑定数据
	private void onBindAdvanceViewHolder(MyRecycleViewHolder holder, int position) {
		getItemView(holder,position,mDatas.get(position));
	}

	/**
	 * 设置每个页面显示的内容
	 *
	 * @param holder itemHolder
	 * @param item   每一Item显示的数据
	 */
	public abstract void getItemView(MyRecycleViewHolder holder,int position,T item);


	/**
	 * 创建ViewHolder
	 *
	 * @param parent   RecycleView对象
	 * @param viewType viee类型
	 * @return Holder对象
	 */
	private MyRecycleViewHolder onCreateAdvanceViewHolder(ViewGroup parent, int viewType) {
		View v = mInflater.inflate(getItemResource(),parent,false);
		return new MyRecycleViewHolder(mContext,v, this);
	}
	//设置布局文件
	public abstract int getItemResource();

	/**
	 * 创建ViewHolder
	 */
	@Override
	public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (mHeaderViewTypes.contains(viewType)) {
			return new HeaderHolder(mHeaderViews.get(viewType / 100000));
		}

		if (mFooterViewTypes.contains(viewType)) {
			int index = viewType / 100000 - getAdvanceCount() - mHeaderViews.size();
			return new FooterHolder(mFooterViews.get(index));
		}
		return onCreateAdvanceViewHolder(parent, viewType);
	}
	public T getItemForPosition(int position){
		return mDatas.get(position);
	}

	@Override
	public void onBindViewHolder(MyRecycleViewHolder holder, int position) {
		if (mFooterViews.size() > 0 && (position > getAdvanceCount() - 1 + mHeaderViews.size())) {
			return;
		}

		if (mHeaderViews.size() > 0) {
			if (position < mHeaderViews.size()) {
				return;
			}
			onBindAdvanceViewHolder(holder, position - mHeaderViews.size());
			return;
		}
		onBindAdvanceViewHolder(holder, position);
	}

	class HeaderHolder extends MyRecycleViewHolder {

		public HeaderHolder(View itemView) {
			super(mContext,itemView);
		}
	}

	class FooterHolder extends MyRecycleViewHolder {

		public FooterHolder(View itemView) {
			super(mContext,itemView);
		}
	}

	@Override
	public int getItemCount() {
		if (mHeaderViews.size() > 0 && mFooterViews.size() > 0) {
			return getAdvanceCount() + mHeaderViews.size() + mFooterViews.size();
		}
		if (mHeaderViews.size() > 0) {
			return getAdvanceCount() + mHeaderViews.size();
		}
		if (mFooterViews.size() > 0) {
			return getAdvanceCount() + mFooterViews.size();
		}
		return getAdvanceCount();
	}
	//设置数据
	public void setItems(List<T> mDatas) {
		if (!mDatas.isEmpty()) {
			this.mDatas = mDatas;
			notifyDataSetChanged();
		}
	}
	//添加数据
	public void addItems(List<T> mDatas) {
		if (!mDatas.isEmpty()) {
			this.mDatas.addAll(mDatas);
			notifyDataSetChanged();
		}
	}
	//添加数据
	public void cleanItems() {
		if (!mDatas.isEmpty()) {
			this.mDatas.clear();
			notifyDataSetChanged();
		}
	}

	public int getHeaderViewsCount() {
		return mHeaderViews.size();
	}

	public int getFooterViewsCount() {
		return mFooterViews.size();
	}

	protected OnItemClickListener listener;


	public OnItemClickListener getItemClickListener() {
		return listener;
	}

	/**
	 * 设置item点击事件监听器
	 * @param listener 监听器对象
	 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	/**
	 * item点击事件回调
	 */
	public interface OnItemClickListener {
		void onItemClickListener(View view, int position);
	}
}
