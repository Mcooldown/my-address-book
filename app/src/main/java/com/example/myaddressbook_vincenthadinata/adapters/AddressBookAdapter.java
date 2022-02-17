package com.example.myaddressbook_vincenthadinata.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myaddressbook_vincenthadinata.MainActivity;
import com.example.myaddressbook_vincenthadinata.R;
import com.example.myaddressbook_vincenthadinata.models.Employee;

import java.util.ArrayList;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookViewHolder> {

    LayoutInflater inflater;
    Context context;
    ArrayList<Employee> employees;

    public AddressBookAdapter(Context context, ArrayList<Employee> employees){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public AddressBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View employeeView = inflater.inflate(R.layout.employee_address_item, parent, false);
        return new AddressBookViewHolder(employeeView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookViewHolder holder, int position) {

        Employee currEmployee = employees.get(position);
        holder.employeeName.setText(currEmployee.getName().getFirst() + " " + currEmployee.getName().getLast());
        holder.employeeAddress.setText(currEmployee.getLocation().getCity() + " " + currEmployee.getLocation().getCountry());
        Glide.with(context).load(currEmployee.getPicture().getMedium()).into(holder.employeePicture);
        holder.btnCall.setOnClickListener(v -> {
            ((MainActivity)context).setPhoneNumber(currEmployee.getPhone());
            ((MainActivity)context).callPhone();
        });
        holder.btnEmail.setOnClickListener(v -> {
            Intent intentEmail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + currEmployee.getEmail()));
            context.startActivity(intentEmail);
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class AddressBookViewHolder extends RecyclerView.ViewHolder {

        RecyclerView.Adapter adapter;
        ImageView employeePicture;
        TextView employeeName, employeeAddress;
        Button btnCall, btnEmail;

        public AddressBookViewHolder(@NonNull View itemView, AddressBookAdapter adapter) {
            super(itemView);

            employeePicture = itemView.findViewById(R.id.img_employee_picture_book);
            employeeName = itemView.findViewById(R.id.txt_employee_name_book);
            employeeAddress = itemView.findViewById(R.id.txt_employee_address_book);
            btnCall = itemView.findViewById(R.id.btn_call);
            btnEmail = itemView.findViewById(R.id.btn_email);

            this.adapter = adapter;
        }
    }

}
