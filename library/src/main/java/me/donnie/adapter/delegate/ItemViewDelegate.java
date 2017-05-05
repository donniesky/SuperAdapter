package me.donnie.adapter.delegate;


import me.donnie.adapter.ViewHolder;

/**
 * @author donnieSky
 * @created_at 2017/4/24.
 * @description
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
