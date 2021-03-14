package com.vrh.tech.gutenberg.network;

import com.vrh.tech.gutenberg.model.BookList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataServices {
    @GET("/books")
    Call<BookList> getBooks();

}
