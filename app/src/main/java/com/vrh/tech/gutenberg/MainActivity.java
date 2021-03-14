package com.vrh.tech.gutenberg;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vrh.tech.gutenberg.adapter.CategoryAdapter;
import com.vrh.tech.gutenberg.model.BookDetail;
import com.vrh.tech.gutenberg.model.BookList;
import com.vrh.tech.gutenberg.model.CardModel;
import com.vrh.tech.gutenberg.network.DataServices;
import com.vrh.tech.gutenberg.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public CategoryAdapter adapter;
    HashMap<String, List<BookDetail>> mBookMap = new HashMap<>();
    private RecyclerView recyclerView;
    private List<CardModel> list = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        getBookData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new CategoryAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        createCardList();
    }

    private void getBookData() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        DataServices service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);
        Call<BookList> call = service.getBooks();
        call.enqueue(new Callback<BookList>() {
            @Override
            public void onResponse(Call<BookList> call, Response<BookList> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<BookList> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("vv", t.getMessage()+"\n"+ t.getCause() );
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(BookList body) {
        String[] name = {"Fiction", "Drama", "Humor", "Politics", "Philosophy", "History", "Adventure"};
        for (BookDetail detail : body.getResults()) {
            for (String category : detail.getSubjects()) {
                for (String nm : name)
                    if (category.contains(nm))
                        manageMap(nm, detail);
            }
        }
    }

    private void manageMap(String key, BookDetail detail) {
        List<BookDetail> list;
        if (mBookMap.containsKey(key)) {
            list = mBookMap.get(key);
        } else {
            list = new ArrayList<>();
        }
        list.add(detail);
        Log.d("Vv", "added " + key);
        mBookMap.put(key, list);
    }

    private void createCardList() {
        int[] drawable = {R.drawable.ic_fiction, R.drawable.ic_drama, R.drawable.ic_humour,
                R.drawable.ic_politics, R.drawable.ic_philosophy, R.drawable.ic_history, R.drawable.ic_adventure};
        String[] name = {"Fiction", "Drama", "Humor", "Politics", "Philosophy", "History", "Adventure"};
        for (int i = 0; i < name.length; i++) {
            CardModel m = new CardModel();
            m.setIcon(drawable[i]);
            m.setName(name[i]);
            list.add(m);
        }
        adapter.notifyDataSetChanged();
    }
}