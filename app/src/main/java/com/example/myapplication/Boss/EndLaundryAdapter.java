package com.example.myapplication.Boss;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.customer.Laundry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EndLaundryAdapter extends RecyclerView.Adapter<EndLaundryAdapter.ViewHolder> {

    ArrayList<Laundry> arr_laundry;

    Context context;
    DatabaseReference mDatabase;
    public EndLaundryAdapter(ArrayList<Laundry> arr_laundry) {
        this.arr_laundry = arr_laundry;
    }

    public void setArr_laundry(ArrayList<Laundry> arr_laundry) {
        this.arr_laundry = arr_laundry;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public EndLaundryAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemlaundry_boss, parent, false);
        context = parent.getContext();

        return new EndLaundryAdapter.ViewHolder(view);
    }
    public void add(Laundry laundry){
        Log.e("adpater","add");
        arr_laundry.add(laundry);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.laundrytype_item.setText(arr_laundry.get(position).getType());
        holder.laundrynum_item.setText(arr_laundry.get(position).getNum() + "벌");
        holder.laundryfromid_item.setText(arr_laundry.get(position).getFromid());
        holder.completelaundry.setText("세탁 완료");
        holder.completelaundry.setVisibility(View.VISIBLE);
    }



    @Override
    public int getItemCount() {
        return arr_laundry.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView completelaundry;
        private TextView laundrytype_item;
        private TextView laundrynum_item;
        private TextView laundryfromid_item;
        private Button btn_lget;
        private Button btn_lstart;
        private Button btn_lfinish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            laundrytype_item = itemView.findViewById(R.id.laundrytype_item);
            laundrynum_item = itemView.findViewById(R.id.laundrynum_item);
            laundryfromid_item = itemView.findViewById(R.id.laundryfromid_item);
            completelaundry = itemView.findViewById(R.id.completelaundry);
            btn_lget = itemView.findViewById(R.id.btn_lget);
            btn_lstart = itemView.findViewById(R.id.btn_lstart);
            btn_lfinish = itemView.findViewById(R.id.btn_lfinish);
            btn_lget.setVisibility(View.GONE);
            btn_lfinish.setVisibility(View.GONE);
            btn_lstart.setVisibility(View.GONE);
        }

    }
}