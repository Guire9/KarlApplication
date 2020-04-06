package edu.temple.karlapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BookList.BookSelectedInterface {

    FragmentManager fm;

    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twoPane = findViewById(R.id.container2) != null;

        fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.container1, BookList.newInstance(getTestBooks()))
                .commit();

        if (twoPane) {
           bookDetailsFragment = new BookDetailsFragment();
      //     BookDetailsFragment.SavedState();
           fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();
        }
    }

    private ArrayList<HashMap<String, String>> getTestBooks() {
        ArrayList<HashMap<String, String>> books = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> book;
        for (int i = 0; i < 10; i++) {
            book = new HashMap<String, String>();
            book.put("title", "Book" + i);
            book.put("author", "Author" + i);
            books.add(book);
        }
        return books;
    };
    @Override
    public void bookSelected(int index) {
        if (twoPane)
            bookDetailsFragment.displayBook(getTestBooks().get(index));
        else {
            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(getTestBooks().get(index)))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
