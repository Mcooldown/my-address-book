package com.example.myaddressbook_vincenthadinata.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myaddressbook_vincenthadinata.R;
import com.example.myaddressbook_vincenthadinata.adapters.EmployeeAdapter;
import com.example.myaddressbook_vincenthadinata.models.Employee;
import com.example.myaddressbook_vincenthadinata.models.GetResponseModel;
import com.example.myaddressbook_vincenthadinata.retrofit.GetEmployeeClient;
import com.example.myaddressbook_vincenthadinata.retrofit.GetEmployeeInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchEmployee extends Fragment {

    public static SearchEmployee fragment = null;
    GetEmployeeInterface getEmployeeInterface = GetEmployeeClient.getEmployeeClientInstance().create(GetEmployeeInterface.class);
    TextView txtName, txtNIM, txtLoading;
    ArrayList<Employee> employees, searchedEmployees;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    EditText txtSearchValue;
    Button btnSearch;

    public static SearchEmployee newInstance() {
        if(fragment == null){
            fragment = new SearchEmployee();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_employee, container, false);
        txtName = view.findViewById(R.id.txt_user_name_search);
        txtNIM = view.findViewById(R.id.txt_user_nim_search);
        txtLoading = view.findViewById(R.id.txt_loading);
        txtSearchValue = view.findViewById(R.id.search_employee_value);
        btnSearch = view.findViewById(R.id.btn_search_employee);

        btnSearch.setOnClickListener(v -> {
            handleSearchEmployee();
        });

        employees = new ArrayList<>();
        searchedEmployees = new ArrayList<>();
        recyclerView = view.findViewById(R.id.search_employee_recyclerview);

        getAllEmployee();

        return view;
    }

    public void getAllEmployee(){
        Call<GetResponseModel> responseModelCall = getEmployeeInterface.getAllEmployee();

        responseModelCall.enqueue(new Callback<GetResponseModel>() {
            @Override
            public void onResponse(Call<GetResponseModel> call, Response<GetResponseModel> response) {

                GetResponseModel responseModel = response.body();
                txtName.setText("Nama: " + responseModel.getNama());
                txtNIM.setText("NIM: " + responseModel.getNim());

                employees = (ArrayList<Employee>) responseModel.getEmployees();

                setAdapter(employees);
                txtLoading.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GetResponseModel> call, Throwable t) {
                txtLoading.setText("Failed to fetch employee data");
                txtNIM.setText("");
                txtName.setText("");
            }
        });
    }

    public void setAdapter(ArrayList<Employee> employees){
        adapter = new EmployeeAdapter(getContext(), employees);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public void handleSearchEmployee(){
        String key = txtSearchValue.getText().toString();

        if(key.trim().length() <= 0){
            txtLoading.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            setAdapter(employees);
        }else{
            searchedEmployees.clear();
            for(int i=0; i< employees.size(); i++){

                String fullName = employees.get(i).getName().getFirst() + " " + employees.get(i).getName().getLast();
                if(fullName.trim().toLowerCase().contains(key.toLowerCase())){
                    searchedEmployees.add(employees.get(i));
                }
            }
            if(searchedEmployees.isEmpty()){
                txtLoading.setText("Employee not found");
                txtLoading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else{
                setAdapter(searchedEmployees);
            }
        }

    }
}