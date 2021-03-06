package com.mark0420.mk_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mark on 2017/8/4.
 * 邮箱：mark14528648@yahoo.co.jp
 * {@link }
 */

public abstract class FooterRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    private static final int FOOTER = Integer.MAX_VALUE;


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1)
            return FOOTER;
        return getItemViewTypeExcludeFooter(position);
    }

    public abstract int getItemViewTypeExcludeFooter(int position);

    @Override
    public FooterRecyclerAdapter addItems(List items) {
        T objects = beans.get(beans.size() - 1);
        beans.clear();
        beans.addAll(items);
        beans.add(objects);
        notifyDataSetChanged();
        return this;

    }

    @Override
    public FooterRecyclerAdapter addItem(T item) {
        T objects = beans.get(beans.size() - 1);
        beans.clear();
        beans.add(item);
        beans.add(objects);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public FooterRecyclerAdapter insertItems(int p, List<T> items) {
        if (p >getItemCount())
            throw new IndexOutOfBoundsException(
                    "Index: " + p + ", Size: " + (beans.size() - 1));
        if (p >getItemCount()-1)
            throw new IndexOutOfBoundsException(
                    "cant insert footer item!");
        beans.addAll(p, items);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 插入单条数据 ,
     *
     * @param item
     */
    @Override
    public FooterRecyclerAdapter insertItem(int p, T item) {
        if (p > getItemCount())
            throw new IndexOutOfBoundsException(
                    "Index: " + p + ", Size: " + (beans.size() - 1));
        if (p > getItemCount()-1)
            throw new IndexOutOfBoundsException(
                    "cant insert footer item!");
        beans.add(p, item);
        return this;
    }

    @Override
    public FooterRecyclerAdapter removeItems(List<T> items) {
        beans.removeAll(items);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public FooterRecyclerAdapter removeItem(int p) {
        if (p >getItemCount())
            throw new IndexOutOfBoundsException(
                    "Index: " + p + ", Size: " + (beans.size() - 1));

        if (p >getItemCount()-1)
            throw new IndexOutOfBoundsException(
                    "cant remove footer item!");
        beans.remove(p);
        return this;
    }

    @Override
    public FooterRecyclerAdapter replaceItems(List<T> items) {
        beans.removeAll(items);
        beans.addAll(items);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public FooterRecyclerAdapter replaceItem(int p, T bean) {
        if (p > getItemCount())
            throw new IndexOutOfBoundsException(
                    "Index: " + p + ", Size: " + (beans.size() - 1));
        if (p >getItemCount()-1)
            throw new IndexOutOfBoundsException(
                    "cant replace footer item!");
        beans.remove(p);
        beans.add(p, bean);
//        notifyItemInserted(p);
//        notifyItemChanged(p);
        return this;
    }

    @Override
    public void clear() {
        T objects = beans.get(beans.size() - 1);
        beans.clear();
        beans.add(objects);
    }

    @Override
    public T getItem(int p) {
        if (p > getItemCount())
            throw new IndexOutOfBoundsException(
                    "Index: " + p + ", Size: " + (beans.size() - 1));
        if (p > getItemCount()-1)
            throw new IndexOutOfBoundsException(
                    "cant get footer item!");
        return beans.get(p);
    }

    @Override
    public List<T> getItems() {
        List<T> b = new ArrayList<>();
        for (int i = 0; i < getItemsSize(); i++) {

            b.add(beans.get(i));
        }

        return b;

    }
    @Override
    public int getItemsSize() {
        return beans.size()-1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == FOOTER) {

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            View footer = LayoutInflater.from(parent.getContext()).inflate(getFooterLayout(), parent, false);
            footer.setLayoutParams(lp);
            return new FooterHolder(footer);
        } else {

            return onCreateViewHolderExcludeFooter(parent, viewType);
        }

    }

    public abstract int getFooterLayout();


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position == getItemCount()-1) {
            FooterHolder h = (FooterHolder) holder;
            setOnFooterView(h.v);
        }else{
            onBindViewHolderExcludeFooter(holder,position);

        }
    }

    protected   void setOnFooterView(View v){ };


    public  abstract  void onBindViewHolderExcludeFooter(RecyclerView.ViewHolder holder, int position) ;

    public abstract RecyclerView.ViewHolder onCreateViewHolderExcludeFooter(ViewGroup parent, int viewType);

    /**
     * 自己处理 button 抢占点击事件
     */

    class FooterHolder extends BaseRecyclerHolder {
        View v;
        public FooterHolder(View v) {
            super(v,false,false);
            this.v=v;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (null != onFooterClickListener) {
                onFooterClickListener.onFooterClick();
            }
        }
    }



    public FooterRecyclerAdapter setOnFooterListener(OnFooterClickListener onFooterClickListener) {
        this.onFooterClickListener = onFooterClickListener;
        return this;
    }

    private OnFooterClickListener onFooterClickListener;

    public interface OnFooterClickListener {
        void onFooterClick();
    }
}
