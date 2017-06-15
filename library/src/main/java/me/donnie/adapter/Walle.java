package me.donnie.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author donnieSky
 * @created_at 2017/5/4.
 * @description
 */
@SuppressWarnings("unchecked")
public class Walle {

    private View headerView;

    private View footerView;

    private View emptyView;

    private View loadMoreView;

    private int headerViewRes;

    private int footerViewRes;

    private int emptyViewRes;

    private int loadMoreViewRes;

    private boolean autoLoadMore = false;

    private WrapperAdapter wrapperAdapter;

    private Walle(Builder builder) {
        wrapperAdapter = new WrapperAdapter(builder.baseAdapter);
        setHeaderView(builder.headerView);
        setFooterView(builder.footerView);
        setEmptyView(builder.emptyView);
        setLoadMoreView(builder.loadMoreView);
        setHeaderViewRes(builder.headerViewRes);
        setFooterViewRes(builder.footerViewRes);
        setEmptyViewRes(builder.emptyViewRes);
        setLoadMoreViewRes(builder.loadMoreViewRes);
        setAutoLoadMore(builder.autoLoadMore);
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        this.autoLoadMore = autoLoadMore;
    }

    public void setHeaderViewRes(@LayoutRes int headerViewRes) {
        if (!(headerViewRes > 0)) {
            return;
        }
        this.headerViewRes = headerViewRes;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void setFooterViewRes(@LayoutRes int footerViewRes) {
        if (!(footerViewRes > 0)) {
            return;
        }
        this.footerViewRes = footerViewRes;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void setEmptyViewRes(int emptyViewRes) {
        if (!(emptyViewRes > 0)) {
            return;
        }
        this.emptyViewRes = emptyViewRes;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void setLoadMoreViewRes(int loadMoreViewRes) {
        if (!(loadMoreViewRes > 0)) {
            return;
        }
        this.loadMoreViewRes = loadMoreViewRes;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void setHeaderView(View headerView) {
        if (headerView == null) {
            return;
        }
        this.headerView = headerView;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void setFooterView(View footerView) {
        if (footerView == null) {
            return;
        }
        this.footerView = footerView;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void setLoadMoreView(View loadMoreView) {
        if (loadMoreView == null) {
            return;
        }
        this.loadMoreView = loadMoreView;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void setEmptyView(View emptyView) {
        if (emptyView == null) {
            return;
        }
        this.emptyView = emptyView;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void removeHeaderView() {
        headerView = null;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void removeFooterView() {
        footerView = null;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void removeEmptyView() {
        emptyView = null;
        wrapperAdapter.notifyDataSetChanged();
    }

    public void removeLoadMoreView() {
        loadMoreView = null;
        wrapperAdapter.notifyDataSetChanged();
    }

    public Walle(MultiItemAdapter baseAdapter) {
        this.wrapperAdapter = new WrapperAdapter(baseAdapter);
    }

    public WrapperAdapter getWrapperAdapter() {
        return wrapperAdapter;
    }

    public void addTo(RecyclerView recyclerView) {
        recyclerView.setAdapter(wrapperAdapter);
    }

    private class WrapperAdapter extends RecyclerView.Adapter<ViewHolder> {

        private static final int VIEW_TYPE_EMPTY = 1;
        private static final int VIEW_TYPE_HEADER = 2;
        private static final int VIEW_TYPE_FOOTER = 3;
        private static final int VIEW_TYPE_LOADMORE = 4;
        private static final int VIEW_TYPE_BASIC_OFFSET = 5;

        private final RecyclerView.Adapter<ViewHolder> baseAdapter;

        public WrapperAdapter(RecyclerView.Adapter<ViewHolder> baseAdapter) {
            assert baseAdapter != null;
            this.baseAdapter = baseAdapter;
        }

        private boolean isEmpty(int position) {
            return (emptyView != null || emptyViewRes > 0) && baseAdapter.getItemCount() == 0;
        }

        private boolean isHeader(int position) {
            return (headerView != null || headerViewRes > 0) && position == 0 && baseAdapter.getItemCount() > 0;
        }

        private boolean isFooter(int position) {
            return (footerView != null || footerViewRes > 0) && position == getItemCount() - 1 && baseAdapter.getItemCount() > 0;
        }

        private boolean isLoadmore(int position) {
            return (loadMoreView != null || loadMoreViewRes > 0) && (position >= baseAdapter.getItemCount() +
                    ((headerView != null || headerViewRes > 0) ? 1 : 0));
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)) {
                return VIEW_TYPE_HEADER;
            } else if (isFooter(position) && !autoLoadMore) {
                return VIEW_TYPE_FOOTER;
            } else if (isEmpty(position)) {
                return VIEW_TYPE_EMPTY;
            } else if (isLoadmore(position) && autoLoadMore) {
                return VIEW_TYPE_LOADMORE;
            }
            return baseAdapter.getItemViewType((headerView != null || headerViewRes > 0) ? position - 1 : position);
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            baseAdapter.setHasStableIds(hasStableIds);
        }

        @Override
        public long getItemId(int position) {
            if (isHeader(position) || isFooter(position) || isEmpty(position) || isLoadmore(position)) {
                return RecyclerView.NO_ID;
            } else {
                return baseAdapter.getItemId((headerView != null || headerViewRes > 0) ? position - 1 : position);
            }
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            baseAdapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            return baseAdapter.onFailedToRecycleView(holder);
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            baseAdapter.onViewAttachedToWindow(holder);
            int viewType = holder.getItemViewType();
            if (viewType == VIEW_TYPE_EMPTY || viewType == VIEW_TYPE_HEADER ||
                    viewType == VIEW_TYPE_FOOTER || viewType == VIEW_TYPE_LOADMORE) {
                setFullSpan(holder);
            }
        }

        protected void setFullSpan(RecyclerView.ViewHolder holder) {
            if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            baseAdapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            baseAdapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
            baseAdapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            baseAdapter.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = (GridLayoutManager) manager;
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type = getItemViewType(position);
                        if (type == VIEW_TYPE_HEADER || type == VIEW_TYPE_FOOTER || type == VIEW_TYPE_LOADMORE) {
                            return gridManager.getSpanCount();
                        }

                        return 1;
                    }
                });
            }
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            baseAdapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_HEADER) {
                if (headerView == null) {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), parent, headerViewRes);
                } else {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), headerView);
                }
            } else if (viewType == VIEW_TYPE_FOOTER) {
                if (footerView == null) {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), parent, footerViewRes);
                } else {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), footerView);
                }
            } else if (viewType == VIEW_TYPE_EMPTY) {
                if (emptyView == null) {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), parent, emptyViewRes);
                } else {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), emptyView);
                }
            } else if (viewType == VIEW_TYPE_LOADMORE) {
                if (loadMoreView == null) {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), parent, loadMoreViewRes);
                } else {
                    return ViewHolder.createBaseViewHolder(parent.getContext(), loadMoreView);
                }
            } else {
                return baseAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!isHeader(position) && !isFooter(position) && !isEmpty(position) && !isLoadmore(position)) {
                baseAdapter.onBindViewHolder(holder, (headerView != null || headerViewRes > 0) ? position - 1 : position);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
            if (!isHeader(position) && !isFooter(position) && !isEmpty(position) && !isLoadmore(position)) {
                baseAdapter.onBindViewHolder(holder, (headerView != null || headerViewRes > 0) ? position - 1 : position, payloads);
            }
        }

        @Override
        public int getItemCount() {
                return baseAdapter.getItemCount()
                        + (((headerView != null || headerViewRes > 0) && baseAdapter.getItemCount() > 0) ? 1 : 0)
                        + (((footerView != null || footerViewRes > 0) && baseAdapter.getItemCount() > 0 && !autoLoadMore) ? 1 : 0)
                        + (((emptyView != null || emptyViewRes > 0) && baseAdapter.getItemCount() == 0) ? 1 : 0)
                        + (((loadMoreView != null || loadMoreViewRes > 0) && baseAdapter.getItemCount() > 0 && autoLoadMore) ? 1 : 0);
        }
    }

    public static final class Builder {
        private View headerView;
        private View footerView;
        private View emptyView;
        private View loadMoreView;
        private int headerViewRes;
        private int footerViewRes;
        private int emptyViewRes;
        private int loadMoreViewRes;
        private boolean autoLoadMore = false;
        private RecyclerView.Adapter<ViewHolder> baseAdapter;

        public Builder() {
        }

        public Builder headerView(View headerView) {
            this.headerView = headerView;
            return this;
        }

        public Builder footerView(View footerView) {
            this.footerView = footerView;
            return this;
        }

        public Builder emptyView(View emptyView) {
            this.emptyView = emptyView;
            return this;
        }

        public Builder loadMoreView(View loadMoreView) {
            this.loadMoreView = loadMoreView;
            return this;
        }

        public Builder headerViewRes(@LayoutRes int headerViewRes) {
            this.headerViewRes = headerViewRes;
            return this;
        }

        public Builder footerViewRes(@LayoutRes int footerViewRes) {
            this.footerViewRes = footerViewRes;
            return this;
        }

        public Builder emptyViewRes(@LayoutRes int emptyViewRes) {
            this.emptyViewRes = emptyViewRes;
            return this;
        }

        public Builder loadMoreViewRes(@LayoutRes int loadMoreViewRes) {
            this.loadMoreViewRes = loadMoreViewRes;
            return this;
        }

        public Builder autoLoadMore(boolean enable) {
            this.autoLoadMore = enable;
            return this;
        }

        public Builder wrapperAdapter(RecyclerView.Adapter<ViewHolder> adapter) {
            baseAdapter = adapter;
            return this;
        }

        public Walle build() {
            return new Walle(this);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
