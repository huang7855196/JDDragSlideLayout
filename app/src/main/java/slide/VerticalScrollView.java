package slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 继续拖动查看图文详情ScrollView（处理滑动）
 */
public class VerticalScrollView extends ScrollView {

    boolean allowDragBottom = true; // 如果是true，则允许拖动至底部的下一页
    float downY = 0;
    boolean needConsumeTouch = true; // 是否需要承包touch事件，needConsumeTouch一旦被定性，则不会更改

    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getRawY();
                needConsumeTouch = true; // 默认情况下，scrollView内部的滚动优先，默认情况下由该ScrollView去消费touch事件

                if (getScrollY() + getMeasuredHeight() >= computeVerticalScrollRange() - 2) {
                    // 允许向上拖动底部的下一页
                    allowDragBottom = true;
                } else {
                    // 不允许向上拖动底部的下一页
                    allowDragBottom = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() + getMeasuredHeight() >= computeVerticalScrollRange() - 2) {
                    // 允许向上拖动底部的下一页
                    allowDragBottom = true;
                } else {
                    // 不允许向上拖动底部的下一页
                    allowDragBottom = false;
                }

                if (!needConsumeTouch) {
                    // 在最顶端且向上拉了，则这个touch事件交给父类去处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                } else if (allowDragBottom) {
                    // needConsumeTouch尚未被定性，此处给其定性
                    // 允许拖动到底部的下一页，而且又向上拖动了，就将touch事件交给父view
                    if (downY - ev.getRawY() > 2) {
                        // flag设置，由父类去消费
                        needConsumeTouch = false;
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
                break;
        }

        // 通知父view是否要处理touch事件
        getParent().requestDisallowInterceptTouchEvent(needConsumeTouch);
        return super.dispatchTouchEvent(ev);
    }
    public void setScrollStateChangedListener(ScrollViewListener listener){
        this.stateChangedListener = listener;
    }
    private ScrollViewListener stateChangedListener;
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(stateChangedListener !=null){
            stateChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
    public interface ScrollViewListener {

        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
