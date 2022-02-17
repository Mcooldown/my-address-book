package com.example.myaddressbook_vincenthadinata.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myaddressbook_vincenthadinata.MainActivity;
import com.example.myaddressbook_vincenthadinata.R;
import com.example.myaddressbook_vincenthadinata.models.Coordinates;
import com.example.myaddressbook_vincenthadinata.models.Employee;
import com.example.myaddressbook_vincenthadinata.models.GetResponseModel;
import com.example.myaddressbook_vincenthadinata.retrofit.GetEmployeeClient;
import com.example.myaddressbook_vincenthadinata.retrofit.GetEmployeeInterface;
import com.example.myaddressbook_vincenthadinata.sqlite.AddressBookDatabaseHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeDetails extends Fragment implements OnMapReadyCallback {

    private static EmployeeDetails fragment = null;
    GetEmployeeInterface getEmployeeInterface = GetEmployeeClient.getEmployeeClientInstance().create(GetEmployeeInterface.class);
    private AddressBookDatabaseHelper db = null;
    private Button btnBack, btnAddToAddressBook;
    private LinearLayout detailWrapper;
    private TextView txtLoading, txtNIM, txtName, txtEmployeeName, txtEmployeeAddress, txtEmployeePhoneCell, txtEmployeeSince, txtEmployeeEmail;
    private SupportMapFragment employeeMap;
    private GoogleMap mapObj;
    private Employee employee;

    public static EmployeeDetails newInstance() {

        if(fragment == null){
            fragment = new EmployeeDetails();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_employee_details, container, false);
        db = AddressBookDatabaseHelper.getDbInstance(getContext());
        btnBack = view.findViewById(R.id.btn_back);

        btnBack.setOnClickListener( v -> {
            ((MainActivity)getContext()).redirectToSearchEmployee();
        });

        btnAddToAddressBook = view.findViewById(R.id.btn_add_to_address_book);

        btnAddToAddressBook.setOnClickListener(v -> {

            boolean isExist = db.isExist(employee.getEmployeeId());

            if(isExist){
                Toast.makeText(getContext(), "This employee already in your address book", Toast.LENGTH_SHORT).show();
            }else{
                db.insertEmployee(employee.getEmployeeId(), employee.getName().getFirst(), employee.getName().getLast(),
                        employee.getLocation().getCity(), employee.getLocation().getCountry(), employee.getPhone(),
                        employee.getEmail(), employee.getPicture().getMedium());
                Toast.makeText(getContext(), "Success add to address book", Toast.LENGTH_SHORT).show();
                ((MainActivity)getContext()).redirectToAddressBook();
            }
        });

        txtLoading = view.findViewById(R.id.txt_detail_loading);
        txtName = view.findViewById(R.id.txt_user_name_detail);
        txtNIM = view.findViewById(R.id.txt_user_nim_detail);
        txtEmployeeName = view.findViewById(R.id.txt_detail_employee_name);
        txtEmployeeAddress = view.findViewById(R.id.txt_detail_employee_address);
        txtEmployeePhoneCell = view.findViewById(R.id.txt_detail_employee_phone_cell);
        txtEmployeeSince = view.findViewById(R.id.txt_detail_employee_member_since);
        txtEmployeeEmail = view.findViewById(R.id.txt_detail_employee_email);
        detailWrapper = view.findViewById(R.id.detail_wrapper);

        Bundle bundle = this.getArguments();
        Integer id = bundle.getInt("id");
        Log.d("server_response", id + "");
        getEmployeeDetail(id);

        return view;
    }

    public void getEmployeeDetail(int id){
        Call<GetResponseModel> responseModelCall = getEmployeeInterface.getEmployeeDetail(id);

        responseModelCall.enqueue(new Callback<GetResponseModel>() {
            @Override
            public void onResponse(Call<GetResponseModel> call, Response<GetResponseModel> response) {

                GetResponseModel responseModel = response.body();
                txtName.setText("Nama: " + responseModel.getNama());
                txtNIM.setText("NIM: " + responseModel.getNim());

                employee = responseModel.getEmployees().get(0);
                if(employee != null){

                    txtEmployeeName.setText(employee.getName().getFirst() + " " +employee.getName().getLast() );
                    txtEmployeeAddress.setText("City: " + employee.getLocation().getCity() + ", " + employee.getLocation().getCountry());
                    txtEmployeePhoneCell.setText("Phone: " + employee.getPhone() + " / " + employee.getCell());
                    txtEmployeeEmail.setText("Email: " + employee.getEmail());

                    SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM yyyy");
                    txtEmployeeSince.setText("Member since: " + dateFormatter.format(employee.getRegistered().getDate()));

                    generateMap();

                    txtLoading.setVisibility(View.GONE);
                    detailWrapper.setVisibility(View.VISIBLE);
                }else{
                    txtLoading.setText("Employee not found");
                }
            }

            @Override
            public void onFailure(Call<GetResponseModel> call, Throwable t) {
                txtLoading.setText("Failed to fetch data");
                txtNIM.setText("");
                txtName.setText("");
            }
        });
    }

    public void generateMap(){
        employeeMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_employee);
        employeeMap.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapObj = googleMap;
        Coordinates coordinates = employee.getLocation().getCoordinates();
        LatLng location = new LatLng(Double.valueOf(coordinates.getLatitude()), Double.valueOf(coordinates.getLongitude()));
        mapObj.addMarker(new MarkerOptions().position(location).title("Employee Map"));
        mapObj.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}