package com.uottawa.clinigo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.Booking;

import java.util.List;

public class PatientBookingsAdapter extends ArrayAdapter<Booking> {

    List<Booking> bookings;
    private Activity context;

    public PatientBookingsAdapter(Activity context, List<Booking> bookings){
        super(context, R.layout.patient_bookings_adapter, bookings);
        this.context = context;
        this.bookings = bookings;
    }

    @Override
    public View getView(int positoin, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View lisViewItem = inflater.inflate(R.layout.patient_bookings_adapter, null, true);
        TextView textViewBookingDate = lisViewItem.findViewById(R.id.textView_patient_booking_date);

        Booking booking = bookings.get(positoin);
        textViewBookingDate.setText(booking.getDate());
        return lisViewItem;

    }
}
