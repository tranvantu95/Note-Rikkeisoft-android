package com.ccs.app.note.custom.adapter.base;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccs.app.note.model.item.BaseItem;

public abstract class ListAdapter<Item, VH extends ListAdapter.ViewHolder<Item, ?>>
        extends PagedListAdapter<Item, VH> {

    protected static <Item extends BaseItem> DiffUtil.ItemCallback<Item> createDiffCallback(Class<Item> clazz) {
        return new DiffUtil.ItemCallback<Item>() {
            @Override
            public boolean areItemsTheSame(Item oldItem, Item newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(Item oldItem, Item newItem) {
                return oldItem.equals(newItem);
            }
        };
    }

//    private List<Item> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public ListAdapter(@NonNull OnItemClickListener onItemClickListener, @NonNull DiffUtil.ItemCallback<Item> diffCallback) {
        super(diffCallback);
        this.onItemClickListener = onItemClickListener;
    }

//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public Item getItem(int index) {
//        return items.get(index);
//    }
//
//    public void addItem(Item item) {
//        items.add(item);
//    }
//
//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutId(viewType), parent, false);
        return getViewHolder(view, viewType);
    }

    @LayoutRes
    protected abstract int getItemLayoutId(int viewType);

    @NonNull
    protected abstract VH getViewHolder(View view, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Item item = getItem(position);
        holder.setItem(item, position);
    }

    public static abstract class ViewHolder<Item, RA extends ListAdapter<Item, ?>>
            extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RA adapter;

        protected Item item;

        public ViewHolder(RA adapter, View itemView) {
            super(itemView);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        public void setItem(@Nullable Item item, int position) {
            this.item = item;
            if(item != null) updateItem(item, position);
            else clearItem(position);
        }

        protected abstract void updateItem(@NonNull Item item, int position);

        protected abstract void clearItem(int position);

        @Override
        public void onClick(View view) {
            adapter.getOnItemClickListener().onItemClick(this, view, getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, View itemView, int position);
    }
}
