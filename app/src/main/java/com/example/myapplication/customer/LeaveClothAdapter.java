package com.example.myapplication.customer;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LeaveClothAdapter extends RecyclerView.Adapter<LeaveClothAdapter.ViewHolder> {

    ArrayList<Laundry> arr_laundry;

    Context context;

    public LeaveClothAdapter(ArrayList<Laundry> arr_laundry) {
        this.arr_laundry = arr_laundry;
        Log.e("LeaveClothAdapter", "생성자");
    }

    public void setArr_laundry(ArrayList<Laundry> arr_laundry) {
        this.arr_laundry = arr_laundry;
    }


    @NotNull
    @Override
    public LeaveClothAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemlaundry_customer, parent, false);

        Log.e("tag", "oncreateviewholder");
        return new LeaveClothAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.clothtype_item.setText(arr_laundry.get(position).type);
        holder.clothnum_item.setText(arr_laundry.get(position).num+"벌");
        holder.shopname_item.setText(arr_laundry.get(position).toshop);

                switch(arr_laundry.get(position).getStatus()){
                  case  0:
                      holder.pg.setProgress(0);
                    break;
                    case 1:
                        holder.pg.setProgress(30);
                        holder.txtprogress.setText("수거 되었습니다.");
                        break;
                    case 2:
                        holder.pg.setProgress(60);
                        holder.txtprogress.setText("세탁 진행 중..");
                        break;
                    case 3:
                        holder.pg.setProgress(100);
                        holder.txtprogress.setText("세탁 완료");
                        holder.btn_receive.setVisibility(View.VISIBLE);
                        break;
                  default:
                    break;
                }
                Log.e("adapter","onbind");


    }


    @Override
    public int getItemCount() {
        return arr_laundry.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView shopname_item;
        private TextView clothtype_item;
        private TextView clothnum_item;
        private TextView txtprogress;
        private Button btn_receive;
        private ProgressBar pg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_receive = itemView.findViewById(R.id.btn_receive);
            shopname_item = itemView.findViewById(R.id.laundrytype_item);
            clothtype_item = itemView.findViewById(R.id.clothtype_item);
            clothnum_item = itemView.findViewById(R.id.laundrynum_item);
            txtprogress = itemView.findViewById(R.id.txtprogress);
            pg = itemView.findViewById(R.id.progressBar2);


            btn_receive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  getAdapterPosition();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("수거 하시겠습니까?")        // 제목 설정
                            .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                // 확인 버튼 클릭시 설정, 오른쪽 버튼입니다.
                                public void onClick(DialogInterface dialog, int whichButton){


                                    //원하는 클릭 이벤트를 넣으시면 됩니다.
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://laundry-9a9bc-default-rtdb.firebaseio.com/").getReference(); // 파이어베이스 realtime database 에서 정보 가져오기

                                    DatabaseReference laundryref = mDatabase.child("BossLaundry").child(arr_laundry.get(position).getToshop());

                                    laundryref.child("endreceive").child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));

                                    laundryref.child("receive").child(arr_laundry.get(position).getLid()).setValue(null);

                                    // 손님 데이터베이스
                                    DatabaseReference laundryref2 = mDatabase.child("CustomerLaundry").child(arr_laundry.get(position).getFfromid());

                                    laundryref2.child("endleave").child(arr_laundry.get(position).getLid()).setValue(arr_laundry.get(position));

                                    laundryref2.child("leave").child(arr_laundry.get(position).getLid()).setValue(null);

                                    arr_laundry.remove(position);
                                    notifyDataSetChanged();

                                    dialog.dismiss();

                                    Toast.makeText(context.getApplicationContext(), "수거 되었습니다.", Toast.LENGTH_SHORT).show();
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
