package com.example.criswiz.emergencyalertandsafety;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ListOnlineViewHolder extends RecyclerView.ViewHolder {
    TextView txtEmail;
    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        txtEmail = itemView.findViewById(R.id.txt_email);
    }
}
