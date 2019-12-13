package com.computeerror.securenotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyListData> mValues;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<MyListData> myDataset) {
        mValues = myDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflate the custom layout
        View v = inflater.inflate(R.layout.list_item, parent, false);
        // Return a new holder instance
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String title = mValues.get(position).mTitle;
        holder.mTitle.setText(title);
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });


        holder.mDescription.setText(mValues.get(position).mDescription);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(int position, MyListData item) {
        mValues.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle;
        public TextView mDescription;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            mTitle = v.findViewById(R.id.tv_title);
            mDescription = v.findViewById(R.id.tv_description);
        }
    }
}
