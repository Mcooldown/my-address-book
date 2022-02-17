package com.example.myaddressbook_vincenthadinata.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myaddressbook_vincenthadinata.R;
import com.example.myaddressbook_vincenthadinata.adapters.AddressBookAdapter;
import com.example.myaddressbook_vincenthadinata.adapters.EmployeeAdapter;
import com.example.myaddressbook_vincenthadinata.models.Employee;
import com.example.myaddressbook_vincenthadinata.sqlite.AddressBookDatabaseHelper;

import java.util.ArrayList;

public class AddressBook extends Fragment {

    private static AddressBook addressBook = null;
    private AddressBookDatabaseHelper db = null;
    private ArrayList<Employee> employees;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    TextView txtLoading;

    public static AddressBook newInstance() {
        if(addressBook == null){
            addressBook = new AddressBook();
        }
        return addressBook;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address_book, container, false);
        recyclerView = view.findViewById(R.id.address_book_recyclerview);
        txtLoading = view.findViewById(R.id.txt_loading_book);
        db = AddressBookDatabaseHelper.getDbInstance(getContext());
        employees = db.getAllEmployees();

        if(employees.size() > 0){
            adapter = new AddressBookAdapter(getContext(), employees);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            txtLoading.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            txtLoading.setText("No data");
        }

        return view;
    }
}