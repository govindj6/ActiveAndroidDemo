package com.example.activeandroiddemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnSave, btnDisplay, btnUpdate, btnDelete;
    EditText etName,etAddress;
    ListView lv;
    ArrayList<Employee> arrayList;
    ArrayAdapter<Employee> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave = findViewById(R.id.btnSave);
        btnDisplay = findViewById(R.id.btnDisplay);
        lv = findViewById(R.id.lv);
        etName = findViewById(R.id.etName);
        etAddress= findViewById(R.id.etAddress);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        arrayList = new ArrayList<>();
        adapter = new EmployeeAdapter(MainActivity.this, arrayList);
        lv.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().equalsIgnoreCase("") && etAddress.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please enter your name and mobile", Toast.LENGTH_SHORT).show();
                } else {
                    Employee e = new Employee();
                    e.name = etName.getText().toString();
                    e.address=etAddress.getText().toString();
                    e.save();
                    etName.getText().clear();
                    etAddress.getText().clear();
                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmployeeList();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please enter name, which you want to update", Toast.LENGTH_SHORT).show();
                } else {
                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    View view = li.inflate(R.layout.dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setView(view);
                    final EditText name = view.findViewById(R.id.et_name_dialog);
                    final EditText address=view.findViewById(R.id.et_address_dialog);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            new Update(Employee.class).set("name = ?", name.getText().toString()).where("name = ?", etName.getText().toString()).execute();
                                            new Update(Employee.class).set("address = ?", address.getText().toString()).where("name = ?", etName.getText().toString()).execute();
                                            Toast.makeText(MainActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                            etName.getText().clear();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etName.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please enter name, which you want to delete", Toast.LENGTH_SHORT).show();
                } else {
                    new Delete().from(Employee.class).where("name = ?", etName.getText().toString()).execute();
                    Toast.makeText(MainActivity.this, etName.getText().toString() + " is deleted", Toast.LENGTH_SHORT).show();
                    etName.getText().clear();
                }
            }
        });
    }
    private List<Employee> getAll() {
        return new Select()
                .from(Employee.class)
                .orderBy("Name ASC")
                .execute();
    }

    private void showEmployeeList() {
        arrayList.clear();
        List<Employee> employeeList = getAll();

        for (int i = 0; i < employeeList.size(); i++) {
            Employee employee = employeeList.get(i);
            arrayList.add(employee);
        }
        adapter.notifyDataSetChanged();


    }
}
