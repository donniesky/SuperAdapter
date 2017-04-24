package me.donnie.superadapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import me.donnie.superadapter.delegate.ItemViewDelegate;
import me.donnie.superadapter.delegate.ItemViewDelegateManager;

/**
 * @author donnieSky
 * @created_at 2017/4/24.
 * @description
 */

public abstract class MultiItemAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {

    private static final String TAG = MultiItemAdapter.class.getSimpleName();

    public static final int ALPHAIN = 0x00000001;

    public static final int SCALEIN = 0x00000002;

    public static final int SLIDEIN_BOTTOM = 0x00000003;

    public static final int SLIDEIN_LEFT = 0x00000004;

    public static final int SLIDEIN_RIGHT = 0x00000005;

    public static final int HEADER_VIEW = 0x00000111;
    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    public static final int EMPTY_VIEW = 0x00000555;

    private OnItemClickListener mOnItemClickListener;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {}

    protected ItemViewDelegateManager mItemViewDelegateManager;

    protected Context mContext;
    @LayoutRes
    protected int mLayoutResId;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;

    public void addData(List<T> datas) {
        this.mDatas = datas == null ? new ArrayList<T>() : datas;
        mDatas.addAll(datas);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public T getItem(@IntRange(from = 0) int position) {
        return mDatas.get(position);
    }

    public MultiItemAdapter(List<T> datas) {
        this.mDatas = datas == null ? new ArrayList<T>() : datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        this.mInflater = LayoutInflater.from(mContext);
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        K holder =  (K)K.createBaseViewHolder(mContext, parent, itemViewDelegate.getItemViewLayoutId());
        bindViewClickListener(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        convert(holder, mDatas.get(holder.getLayoutPosition()));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void convert(K holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public MultiItemAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    private void bindViewClickListener(final BaseViewHolder holder) {
        if (holder == null) {
            return;
        }
        View convertView = holder.getConvertView();
        if (convertView == null) {
            return;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getOnItemClickListener() != null && holder != null) {
                    getOnItemClickListener().onItemClick(MultiItemAdapter.this, v, holder.getLayoutPosition());
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (getOnItemLongClickListener() != null && holder != null) {
                    return getOnItemLongClickListener().onItemLongClick(MultiItemAdapter.this, v, holder.getLayoutPosition());
                }
                return false;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(MultiItemAdapter adapter, View view, int position);
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(MultiItemAdapter adapter, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(MultiItemAdapter adapter, View view, int position);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(MultiItemAdapter adapter, View view, int position);
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public OnItemChildClickListener getOnItemChildClickListener() {
        return mOnItemChildClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return mOnItemChildLongClickListener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }
}
