package me.hongui.learngerman.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import me.hongui.learngerman.R;
import me.hongui.learngerman.bean.Record;
import me.hongui.learngerman.bean.RecordState;

public class MeAdapter extends Adapter<Record> {
    private int layout;
    List<Record> recordList;
    OnStateClickListener listener;
    RecordState state = new RecordState();

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            layout = R.layout.item_me_header;
        } else {
            layout = R.layout.item_me;
        }
        return layout;
    }

    @Override
    int layout() {
        return layout;
    }

    @Override
    void convert(ViewHolder holder, Record data, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (0 == position) {
            holder.setText(R.id.tv_item_me_month, state.getTitle());
            ImageView left = holder.view(R.id.iv_item_me_left);
            ImageView right = holder.view(R.id.iv_item_me_right);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null == listener) {
                        return;
                    }
                    listener.onStateClick(v, false);
                }
            });

            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null == listener) {
                        return;
                    }
                    listener.onStateClick(v, true);
                }
            });
            left.setEnabled(state.isCanPrev());
            right.setEnabled(state.isCanNext());
            return;
        }
        final Record item = recordList.get(position - 1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == getListener()) {
                    return;
                }
                getListener().onClick(v, item, position - 1);
            }
        });
        FlexboxLayoutManager.LayoutParams params = new FlexboxLayoutManager.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.setFlexBasisPercent(0.142704F);
        params.setAlignSelf(AlignItems.CENTER);
        params.setFlexGrow(1);
        TextView content = holder.view(R.id.tv_item_me_date);
        ConstraintLayout container = holder.view(R.id.fl_item_me_container);
        container.setLayoutParams(params);
        content.setText(item.getDay() + "");
        content.setSelected(item.isJoin());
        content.setEnabled(item.isEnable());
    }

    @Override
    public int getItemCount() {
        if (null == recordList) {
            return 1;
        }
        return recordList.size() + 1;
    }

    public void update(List<Record> records) {
        if (null == recordList) {
            recordList = new ArrayList<>();
        }
        recordList.clear();
        recordList.addAll(records);
        notifyDataSetChanged();
    }

    public void updateHead(String title, boolean canLeft, boolean canRight) {
        state.setCanPrev(canLeft);
        state.setCanNext(canRight);
        state.setTitle(title);
        notifyItemChanged(0);
    }

    public void canPrev(boolean canPrev) {
        updateHead(state.getTitle(), canPrev, state.isCanNext());
    }

    public void canNext(boolean canNext) {
        updateHead(state.getTitle(), state.isCanPrev(), canNext);
    }

    public void setTitle(String title) {
        updateHead(title, state.isCanPrev(), state.isCanNext());
    }

    public OnStateClickListener getStateListener() {
        return listener;
    }

    public void setStateListener(OnStateClickListener listener) {
        this.listener = listener;
    }

    public interface OnStateClickListener {
        void onStateClick(View v, boolean isNext);
    }
}
