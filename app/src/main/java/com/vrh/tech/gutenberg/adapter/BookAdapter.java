package com.vrh.tech.gutenberg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vrh.tech.gutenberg.R;
import com.vrh.tech.gutenberg.model.Author;
import com.vrh.tech.gutenberg.model.BookDetail;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    List<BookDetail> mBookList;

    public BookAdapter(List<BookDetail> bookList) {
        mBookList = bookList;
    }


    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookDetail detail = mBookList.get(position);
        holder.title.setText(detail.getTitle());
        StringBuilder sb = new StringBuilder();
        for (Author ath : detail.getAuthors()) {
            sb.append(ath.getName());
        }
        holder.author.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View bookCardView = inflater.inflate(R.layout.book_item, parent, false);
        BookViewHolder mViewHolder = new BookViewHolder(bookCardView);
        return mViewHolder;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView author;
        private TextView title;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.img_book_cover);
            author = itemView.findViewById(R.id.txt_author);
            title = itemView.findViewById(R.id.txt_book_name);
        }
    }
}
