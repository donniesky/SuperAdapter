package me.donnie.adapter;

import java.util.List;

import me.donnie.adapter.delegate.ItemViewDelegate;


/**
 * @author donnieSky
 * @created_at 2017/4/24.
 * @description
 */

public abstract class SuperAdapter<T> extends MultiItemAdapter<T, BaseViewHolder> {

    protected int mLayoutResId;

    public SuperAdapter(final int layoutResId, List<T> datas) {
        super(datas);
        this.mLayoutResId = layoutResId;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return mLayoutResId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position) {
                SuperAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(BaseViewHolder holder, T t, int position);
}
