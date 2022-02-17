package com.example.myaddressbook_vincenthadinata.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myaddressbook_vincenthadinata.models.Employee;
import com.example.myaddressbook_vincenthadinata.models.Location;
import com.example.myaddressbook_vincenthadinata.models.Name;
import com.example.myaddressbook_vincenthadinata.models.Picture;

import java.util.ArrayList;

public class AddressBookDatabaseHelper extends SQLiteOpenHelper {

    public static AddressBookDatabaseHelper dbInstance = null;
    public static final String DATABASE_NAME = "address_book_database";
    public static final Integer VERSION = 1;

    public static AddressBookDatabaseHelper getDbInstance(Context context){
        if(dbInstance == null){
            dbInstance = new AddressBookDatabaseHelper(context);
        }
        return dbInstance;
    }

    public AddressBookDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE address_book (" +
                "employeeId INTEGER PRIMARY KEY," +
                "employeeFirstName TEXT," +
                "employeeLastName TEXT," +
                "employeeCity TEXT," +
                "employeeCountry TEXT," +
                "employeePhone TEXT," +
                "employeeEmail TEXT," +
                "employeePicture TEXT)");
    }

    public void insertEmployee(Integer employeeId, String employeeFirstName, String employeeLastName,
                              String employeeCity, String employeeCountry, String employeePhone,
                              String employeeEmail, String employeePicture){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("employeeId", employeeId);
        contentValues.put("employeeFirstName", employeeFirstName);
        contentValues.put("employeeLastName", employeeLastName);
        contentValues.put("employeeCity",employeeCity);
        contentValues.put("employeeCountry", employeeCountry);
        contentValues.put("employeePhone", employeePhone);
        contentValues.put("employeeEmail", employeeEmail);
        contentValues.put("employeePicture",employeePicture);

        db.insert("address_book", null, contentValues);
        db.close();
    }

    public ArrayList<Employee> getAllEmployees(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Employee> employees = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM address_book", null);
        if(cursor.moveToFirst()){
            do{
                Employee currEmployee = new Employee();
                currEmployee.setEmployeeId(cursor.getInt(0));

                Name name = new Name();
                name.setFirst(cursor.getString(1));
                name.setLast(cursor.getString(2));
                currEmployee.setName(name);

                Location location = new Location();
                location.setCity(cursor.getString(3));
                location.setCountry(cursor.getString(4));
                currEmployee.setLocation(location);

                currEmployee.setPhone(cursor.getString(5));
                currEmployee.setEmail(cursor.getString(6));

                Picture picture = new Picture();
                picture.setMedium(cursor.getString(7));
                currEmployee.setPicture(picture);

                employees.add(currEmployee);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return employees;
    }

    public boolean isExist(Integer employeeId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM address_book WHERE employeeId =" + employeeId, null);

        if(cursor.moveToFirst()){
           return true;
        }else{
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
