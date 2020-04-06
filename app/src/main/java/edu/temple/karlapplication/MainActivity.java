package edu.temple.karlapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BookList.BookSelectedInterface {

    EditText search;
    Button button;
    String s;
    FragmentManager fm;

    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;
    static int detailsIndex;       //Index of the details fragment selected


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        search= findViewById(R.id.search);
        s = search.getText().toString();
        fm = getSupportFragmentManager();

        //when searchButton is clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s= search.getText().toString();
                fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_list_container,BookList.newInstance(getTestBooks(),s))
                        .commit();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        });
        // Fragments in Portrait mode
        if (findViewById(R.id.layout_portrait)!=null){

            //Transaction sets fragment list to entire book list
            fm.beginTransaction()
                    .replace(R.id.fragment_list_container, BookList.newInstance(getTestBooks(),s))
                    .commit();

            twoPane = findViewById(R.id.fragment_details_container) != null;

            if (twoPane) {
                bookDetailsFragment = new BookDetailsFragment();
                //     BookDetailsFragment.SavedState();
                fm.beginTransaction()
                        .replace(R.id.fragment_details_container, bookDetailsFragment)
                        .commit();
            }
        }
        // Fragments in landscape mode
        else if(findViewById(R.id.layout_landscape)!=null){
            fm.beginTransaction()
                    .replace(R.id.fragment_list_container, BookList.newInstance(getTestBooks()," "))
                    .commit();
            fm.beginTransaction()
                    .replace(R.id.fragment_details_container, BookDetailsFragment.newInstance(getTestBooks().get(detailsIndex)))
                    .addToBackStack(null)
                    .commit();

        }
    }
    private ArrayList<Book> getTestBooks() {
        ArrayList<Book> books = new ArrayList<Book>();
        Book book;
        for (int i = 0; i < 10; i++) {
            book = new Book("Author"+i,"title");
            books.add(book);
        }
        return books;
    }
    @Override
    public void bookSelected(int index) {
        detailsIndex = index;
        if (findViewById(R.id.layout_landscape) !=null){
            fm.beginTransaction()
                    .replace(R.id.fragment_details_container, BookDetailsFragment.newInstance(getTestBooks().get(index)))
                    .addToBackStack(null)
                    .commit();
    }else if(findViewById(R.id.layout_landscape) ==null){
            fm.beginTransaction()
                    .replace(R.id.fragment_list_container, BookDetailsFragment.newInstance(getTestBooks().get(index)))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
