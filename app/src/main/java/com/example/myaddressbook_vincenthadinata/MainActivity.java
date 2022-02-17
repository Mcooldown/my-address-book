package com.example.myaddressbook_vincenthadinata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myaddressbook_vincenthadinata.fragments.AddressBook;
import com.example.myaddressbook_vincenthadinata.fragments.EmployeeDetails;
import com.example.myaddressbook_vincenthadinata.fragments.SearchEmployee;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {

    BottomNavigationView bottomNavigationView;
    SearchEmployee searchEmployeeFragment;
    AddressBook addressBookFragment;
    EmployeeDetails employeeDetailsFragment;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation_nav);
        searchEmployeeFragment = SearchEmployee.newInstance();
        addressBookFragment = AddressBook.newInstance();
        employeeDetailsFragment = EmployeeDetails.newInstance();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, searchEmployeeFragment, null)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment f = null;

                switch (item.getItemId()){
                    case R.id.menu_search_employee:
                        f = searchEmployeeFragment;
                        break;
                    case R.id.menu_address_book:
                        f = addressBookFragment;
                        break;
                }

                replaceFragment(f);
                return true;
            }
        });
    }

    public void viewEmployeeDetail(int id){
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        employeeDetailsFragment.setArguments(bundle);
        replaceFragment(employeeDetailsFragment);
    }

    public void redirectToSearchEmployee(){
        replaceFragment(searchEmployeeFragment);
    }

    public void redirectToAddressBook(){
        replaceFragment(addressBookFragment);
    }

    public void replaceFragment(Fragment f){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container, f, null)
                .commit();
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void callPhone(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(intentCall);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                }
            }
        }
    }
}