package com.uottawa.clinigo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.User;
import java.util.List;


public class UserAdapter extends ArrayAdapter<User> {

    List<User> users;
    private Activity context;

    public UserAdapter(Activity context, List<User> users) {
        super(context, R.layout.admin_services_adapter, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.admin_users_adapter, null, true);

        TextView textViewEmail = listViewItem.findViewById(R.id.text_email);
        TextView textViewRole = listViewItem.findViewById(R.id.text_role);

        User user = users.get(position);
        textViewEmail.setText(user.getEmail());
        textViewRole.setText("Role: " + user.getRole());
        return listViewItem;
    }

}
