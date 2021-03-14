package com.vrh.tech.gutenberg.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vrh.tech.gutenberg.BooksActivity;
import com.vrh.tech.gutenberg.R;
import com.vrh.tech.gutenberg.model.BookDetail;
import com.vrh.tech.gutenberg.model.CardModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CardModel> mCards;
    private HashMap<String, List<BookDetail>> mBookMap;

    public CategoryAdapter(List<CardModel> cards, HashMap<String, List<BookDetail>> map) {
        mCards = cards;
        mBookMap = map;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CardModel model = mCards.get(position);
        holder.icon.setImageResource(model.getIcon());
        holder.name.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View categoryCardView = inflater.inflate(R.layout.category_item, parent, false);
        CategoryViewHolder viewHolder = new CategoryViewHolder(categoryCardView);
        return viewHolder;
    }

    private void onCardClicked(int position, View view) {
        CardModel model = mCards.get(position);
        List<BookDetail> booklist = mBookMap.get(model.getName());
        Intent intent = new Intent(view.getContext(), BooksActivity.class);
        intent.putExtra("Activity", model.getName());
        try {
            if (mBookMap.containsKey(model.getName())) {
                ArrayList<BookDetail> mDetailList = (ArrayList<BookDetail>) mBookMap.get(model.getName());
                intent.putExtra("list", (Serializable) mDetailList);
            }
        } catch (Exception e) {
            Log.d("VV", "" + e.getMessage());
        }
        view.getContext().startActivity(intent);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView icon;
        public TextView name;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            icon = itemView.findViewById(R.id.ic_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();
            onCardClicked(pos, v);
        }
    }
}
