package com.example.myaddressbook_vincenthadinata.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myaddressbook_vincenthadinata.MainActivity;
import com.example.myaddressbook_vincenthadinata.R;
import com.example.myaddressbook_vincenthadinata.models.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>
{
    LayoutInflater inflater;
    ArrayList<Employee> employees;
    Context context;

    public EmployeeAdapter(Context context, ArrayList<Employee> employees){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View employeeView = inflater.inflate(R.layout.employee_item, parent, false);
        return new EmployeeViewHolder(employeeView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee currentEmployee =employees.get(position);
        holder.employeeName.setText(currentEmployee.getName().getFirst() + " " +currentEmployee.getName().getLast() );
        holder.employeeAddress.setText("City: " + currentEmployee.getLocation().getCity() + ", " + currentEmployee.getLocation().getCountry());
        holder.employeePhoneCell.setText("Phone: " + currentEmployee.getPhone() + " / " + currentEmployee.getCell());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM yyyy");
        holder.employeeSince.setText("Member since: " + dateFormatter.format(currentEmployee.getRegistered().getDate()));

        Glide.with(context).load(currentEmployee.getPicture().getMedium()).into(holder.employeePicture);

        holder.itemView.setTag(currentEmployee.getEmployeeId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer id = Integer.parseInt(view.getTag().toString());
                ((MainActivity)context).viewEmployeeDetail(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        RecyclerView.Adapter adapter;
        TextView employeeName,employeeAddress,employeePhoneCell,employeeSince;
        ImageView employeePicture;

        public EmployeeViewHolder(@NonNull View itemView, EmployeeAdapter adapter) {
            super(itemView);

            employeeName = itemView.findViewById(R.id.txt_employee_name);
            employeeAddress = itemView.findViewById(R.id.txt_employee_address);
            employeePhoneCell = itemView.findViewById(R.id.txt_employee_phone_cell);
            employeeSince = itemView.findViewById(R.id.txt_employee_member_since);
            employeePicture = itemView.findViewById(R.id.img_employee_picture);

            this.adapter = adapter;
        }
    }

}
