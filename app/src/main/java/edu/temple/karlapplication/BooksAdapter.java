package edu.temple.karlapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Book> booksList;
    ArrayList<Book> mStringFilterList;
    ValueFilter valueFilter;

    public BooksAdapter(Context context, ArrayList books) {
        this.context = context;
        this.booksList = books;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int position) {
        return booksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView titleTextView, authorTextView;

        if (!(convertView instanceof LinearLayout)) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_adapter, parent, false);
        }

        titleTextView = convertView.findViewById(R.id.titleTextView);
        authorTextView = convertView.findViewById(R.id.authorTextView);

        titleTextView.setText(booksList.get(position).getTitle());
        authorTextView.setText(booksList.get(position).getAuthor());

        return convertView;
    }
    @Override
    public Filter getFilter() {
        if(valueFilter==null){
            valueFilter=new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Book> filterList = new ArrayList<Book>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getTitle().toUpperCase())
                            .contains(constraint.toString().toUpperCase()) ||
                            (mStringFilterList.get(i).getAuthor().toUpperCase())
                                    .contains(constraint.toString().toUpperCase())) {

                        Book bean = new Book(mStringFilterList.get(i)
                                .getAuthor(), mStringFilterList.get(i)
                                .getTitle());
                        filterList.add(bean);
                    }
                }


                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            booksList = (ArrayList<Book>) results.values;
            notifyDataSetChanged();
        }

    }

}
