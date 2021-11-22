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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.customer.Laundry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LaundryClothAdapter_Boss extends RecyclerView.Adapter<LaundryClothAdapter_Boss.ViewHolder> {

    ArrayList<Laundry> arr_laundry;

    Context context;
    DatabaseReference mDatabase;
    public LaundryClothAdapter_Boss(ArrayList<Laundry> arr_laundry) {
        this.arr_laundry = arr_laundry;
    }


    @NotNull
    @Override
    public LaundryClothAdapter_Boss.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.itemlaundry_boss, parent, false);
        context = parent.getContext();

        return new LaundryClothAdapter_Boss.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.laundrytype_item.setText(arr_laundry.get(position).getType());
        holder.laundrynum_item.setText(arr_laundry.get(position).getNum() + "벌");
        holder.laundryfromid_item.setText(arr_laundry.get(position).getFromid());

        switch(arr_laundry.get(position).getStatus()){
          case  0:
            break;
            case  1:
                holder.btn_lget.setVisibility(View.INVISIBLE);
                break;
            case  2:
                holder.btn_lget.setVisibility(View.INVISIBLE);
                holder.btn_lstart.setVisibility(View.INVISIBLE);
                break;
            case  3:
                holder.completelaundry.setVisibility(View.VISIBLE);
                holder.btn_lget.setVisibility(View.GONE);
                holder.btn_lstart.setVisibility(View.GONE);
                holder.btn_lfinish.setVisibility(View.GONE);
                break;
          default:
            break;
        }
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

            btn_lget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Log.e("position",getAdapterPosition()+"");
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기
                    int position =  getAdapterPosition();
                    DatabaseReference laundryref = mDatabase.child("BossLaundry").child(arr_laundry.get(getAdapterPosition()).getToshop()).child("receive");
                    arr_laundry.get(position).setStatus(1);
                    Log.e("position",getAdapterPosition()+"");
                    notifyDataSetChanged();
                    laundryref.child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));
                    Log.e("position",getAdapterPosition()+"");
                    DatabaseReference laundryref2 = mDatabase.child("CustomerLaundry").child(arr_laundry.get(position).getFfromid()).child("leave");
                    laundryref2.child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));

                    Toast.makeText(context.getApplicationContext(), "수거 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });

            btn_lstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  getAdapterPosition();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기

                    DatabaseReference laundryref = mDatabase.child("BossLaundry").child(arr_laundry.get(position).getToshop()).child("receive");
                    arr_laundry.get(position).setStatus(2);
                    notifyDataSetChanged();
                    laundryref.child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));

                    DatabaseReference laundryref2 = mDatabase.child("CustomerLaundry").child(arr_laundry.get(position).getFfromid()).child("leave");
                    laundryref2.child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));

                    Toast.makeText(context.getApplicationContext(), "세탁을 시작합니다.", Toast.LENGTH_SHORT).show();
                }
            });

            btn_lfinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("세탁을 완료하셨습니까?")        // 제목 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                // 확인 버튼 클릭시 설정, 오른쪽 버튼입니다.
                                public void onClick(DialogInterface dialog, int whichButton){
                                    //원하는 클릭 이벤트를 넣으시면 됩니다.
                                    int position =  getAdapterPosition();
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기

                                    DatabaseReference laundryref = mDatabase.child("BossLaundry").child(arr_laundry.get(position).getToshop()).child("receive");
                                    arr_laundry.get(position).setStatus(3);
                                    notifyDataSetChanged();
                                    laundryref.child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));

                                    DatabaseReference laundryref2 = mDatabase.child("CustomerLaundry").child(arr_laundry.get(position).getFfromid()).child("leave");
                                    laundryref2.child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));

                                    dialog.dismiss();

                                    Toast.makeText(context.getApplicationContext(), "세탁을 완료합니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                // 취소 버튼 클릭시 설정, 왼쪽 버튼입니다.
                                public void onClick(DialogInterface dialog, int whichButton){
                                    //원하는 클릭 이벤트를 넣으시면 됩니다.
                                }
                            });

                    AlertDialog dialog = builder.create();    // 알림창 객체 생성
                    dialog.show();    // 알림창 띄우기

                }
            });


        }

    }
}