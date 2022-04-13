package me.hongui.learngerman.adapter;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.hongui.learngerman.R;

public class SplitAdapter extends Adapter<Character> {
    private StringBuilder records;
    private OnItemPressListener listener;
    private String origin;

    @Override
    int layout() {
        return R.layout.item_split;
    }

    @Override
    void convert(final ViewHolder holder, Character data, int position) {
        TextView content = holder.view(R.id.tv_item_split);
        //content.setSelected(null!=records.get(holder.getAdapterPosition()));
        holder.setText(R.id.tv_item_split, data.toString());
        //holder.addOnClickListener(R.id.tv_item_split);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Character character = getDatas().get(position);
                records.append(character);
                if (null != listener) {
                    listener.onItemPressed(records.toString(), character, position);
                }
            }
        });
    }

    public SplitAdapter() {
        records = new StringBuilder();
    }

    public void addSource(String source) {
        origin = source;

        records.delete(0, records.length());

        int lenght = source.length();
        final Set<Character> characters = new HashSet<>();
        for (int i = 0; i < lenght; i++) {
            characters.add(source.charAt(i));
        }
        List<Character> datas = new ArrayList<>(characters);
        Collections.shuffle(datas);
        addNewDatas(datas);
    }

    public String getOrigin() {
        return origin;
    }

    public String getInput() {
        return records.toString();
    }

    public void setListener(OnItemPressListener listener) {
        this.listener = listener;
    }

    public void remove(int index) {
        Character character = records.charAt(index);
        records.delete(index, index + 1);
        if (null != listener) {
            listener.onItemPressed(records.toString(), character, index);
        }
    }

    public void remove() {
        remove(records.length() - 1);
    }

    public interface OnItemPressListener {
        void onItemPressed(String total, char c, int pos);
    }
}
