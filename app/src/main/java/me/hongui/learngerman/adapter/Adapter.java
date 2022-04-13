package me.hongui.learngerman.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

abstract public class Adapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<T> datas = new ArrayList<>();
    private OnItemClickListener<T> listener;
    private SparseArray<OnItemClickListener<T>> childListeners=new SparseArray<>();

    abstract int layout();

    abstract void convert(ViewHolder holder,T data,int position);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout(),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final T t = datas.get(position);
        for(int i=0;i<childListeners.size();i++){
            int key = childListeners.keyAt(i);
            final OnItemClickListener<T> clickListener = childListeners.valueAt(i);
            View view = holder.view(key);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(v,t,position);
                }
            });
        }
        if(null!=listener){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,t,position);
                }
            });
        }
        convert(holder,t,position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public void addData(T data){
        if(null==data){
            return;
        }
        int count=getItemCount();
        getDatas().add(data);
        notifyItemInserted(count);
    }

    public void addDatas(List<T> datas) {
        addDatas(getItemCount(),datas);
    }

    public void addDatas(int postion,List<T> datas){
        if(null==datas||datas.isEmpty()){
            return;
        }
        int size=datas.size();
        this.datas.addAll(postion,datas);
        notifyItemRangeInserted(postion,size);
    }

    public void addNewDatas(List<T> datas){
        int count = getItemCount();
        remove(0,count);
        addDatas(datas);
    }

    public void remove(int position,int count){
        int origin=count;
        int size=getDatas().size();
        Iterator<T> iterator = getDatas().iterator();
        for(int i=0;i<size;i++){
            iterator.next();
            if(position==i){
                iterator.remove();
                count--;
                break;
            }
        }
        while (count>0){
            iterator.next();
            iterator.remove();
            count--;
        }
        notifyItemRangeRemoved(position, origin);
    }

    public OnItemClickListener<T> getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public OnItemClickListener<T> getChildListener(@IdRes int id) {
        return childListeners.get(id);
    }

    public void setChildListener(@IdRes int id, OnItemClickListener<T> childListener) {
        if(null==childListeners){
            childListeners = new SparseArray<>();
        }
        childListeners.put(id,childListener);
    }

    public interface OnItemClickListener<D>{
        void onClick(View view,D t,int position);
    }
}
