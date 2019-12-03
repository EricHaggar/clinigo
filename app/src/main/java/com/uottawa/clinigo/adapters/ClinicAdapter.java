package com.uottawa.clinigo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.Employee;

import java.util.List;


public class ClinicAdapter extends ArrayAdapter<Employee>{

    List<Employee> clinics;
    private Activity context;

    public ClinicAdapter(Activity context, List<Employee> clinics) {
        super(context, R.layout.patient_clinics_adapter, clinics);
        this.context = context;
        this.clinics = clinics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.patient_clinics_adapter, null, true);

        TextView textViewClinicName = listViewItem.findViewById(R.id.clinic_name);
        RatingBar ratingBar = listViewItem.findViewById(R.id.clinic_rating);

        Employee clinic = clinics.get(position);
        textViewClinicName.setText(clinic.getClinicInfo().getName());
        ratingBar.setRating(clinic.getClinicInfo().calculateAverageRating());
        return listViewItem;
    }
}
