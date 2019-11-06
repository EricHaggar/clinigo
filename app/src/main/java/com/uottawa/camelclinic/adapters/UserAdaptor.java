package com.uottawa.camelclinic.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uottawa.camelclinic.R;
import com.uottawa.camelclinic.model.Service;
import com.uottawa.camelclinic.model.User;
import java.util.List;


public class UserAdaptor extends ArrayAdapter<User> {

    List<User> users; //
    private Activity context;

    public UserAdaptor(Activity context, List<User> users) {
        super(context, R.layout.admin_services_adapter, users); // layout hasnt been done yet
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.admin_user_adaptor, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.text_name); //
        TextView textViewRole = (TextView) listViewItem.findViewById(R.id.text_role); //

        User user = users.get(position);
        textViewName.setText(users.getFirstName());
        textViewRole.setText("Provider: " + user.getRole());
        return listViewItem;
    }

}
