package edu.temple.karlapplication;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class BookList extends Fragment {

    private static final String BOOK_LIST_KEY = "booklist";
    private static final String Search_KEY = "";
    private ArrayList<Book> books;
    private String Mysearch;

    BookSelectedInterface parentActivity;

    public BookList() {}

    public static BookList newInstance(ArrayList<Book> books, String search) {
        BookList fragment = new BookList();
        Bundle args = new Bundle();
        args.putSerializable(BOOK_LIST_KEY, books);
        args.putString(Search_KEY,search);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BookSelectedInterface) {
            parentActivity = (BookSelectedInterface) context;
        } else {
            throw new RuntimeException("Please implement the required interface(s)");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = (ArrayList) getArguments().getSerializable(BOOK_LIST_KEY);
            Mysearch= getArguments().getString(Search_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_book_list, container, false);

        BooksAdapter customAdapter = new BooksAdapter(getContext(), books);
     //   customAdapter.getFilter().filter(Mysearch);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.bookSelected(position);
            }
        });

        return listView;
    }

    interface BookSelectedInterface {
        void bookSelected(int index);
    }
}
