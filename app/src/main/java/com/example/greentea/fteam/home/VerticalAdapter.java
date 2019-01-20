package com.example.greentea.fteam.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greentea.fteam.R;

import java.util.ArrayList;
import java.util.Objects;

public class VerticalAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Objects> list;

    public VerticalAdapter(@NonNull final Context context, @NonNull final ArrayList<Objects> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View defaultView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_list_vertical, parent, false);
        return new ContentViewHolder(defaultView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ContentViewHolder) holder).setData(list.get(position), context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {

        View view;

        ContentViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        void setData(Object item, Context context) {
            // Setting recycler view
            RecyclerView recyclerView = view.findViewById(R.id.horizontal_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            // Generate demo data
            ArrayList<Objects> demoList = new ArrayList<>();
            demoList.add(null);
            demoList.add(null);
            demoList.add(null);
            demoList.add(null);
            demoList.add(null);
            // Setting adapter
            HorizontalAdapter adapter = new HorizontalAdapter(context, demoList);
            recyclerView.swapAdapter(adapter, false);
        }
    }
}
