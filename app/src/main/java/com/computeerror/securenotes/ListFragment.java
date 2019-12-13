package com.computeerror.securenotes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<MyListData> myListData;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_list_fragment, parent, false);
    }

    private void logNotifyUser(String s) {
        Log.d("Main", s);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        Toolbar toolbar = view.findViewById(R.id.toolbar);


        myListData = MyListData.generateList(25);
        logNotifyUser("MyListData size = " + myListData.size());
        logNotifyUser("ListFragment OnCreate");

//            mAdapter.notifyDataSetChanged();
        mRecyclerView = view.findViewById(R.id.recycler_view);
        logNotifyUser("recyclerView = " + mRecyclerView);
        mRecyclerView.setHasFixedSize(false);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MyApplication.getAppContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // define an adapter
        mAdapter = new MyAdapter(myListData);
        mRecyclerView.setAdapter(mAdapter);
    }
}
