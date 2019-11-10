package com.uottawa.clinigo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.Service;

import java.util.List;

public class ServiceAdapter extends ArrayAdapter<Service> {

    List<Service> services;
    private Activity context;

    public ServiceAdapter(Activity context, List<Service> services) {
        super(context, R.layout.admin_services_adapter, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.admin_services_adapter, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.text_email);
        TextView textViewRole = listViewItem.findViewById(R.id.text_role);

        Service service = services.get(position);
        textViewName.setText(service.getName());
        textViewRole.setText("Provider: " + service.getRole());
        return listViewItem;
    }
}
