package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {

    ArrayList<LinkItem> linkItems;
    Context context;

    public LinkAdapter(ArrayList<LinkItem> linkItems, Context context) {
        this.linkItems = linkItems;
        this.context = context;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.link_item, parent, false);

        context=parent.getContext();

        Log.i("tag","oncreateviewholder");
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder( @NotNull ViewHolder holder, int position) {
        LinkItem linkItem = linkItems.get(position);
        holder.list_WhereEx.setText(linkItem.getList_WhereEx());
        holder.list_Link.setText(linkItem.getList_Link());
        holder.list_image.setImageResource(linkItem.getList_image());

        Log.i("tag","onBindViewHolder");

//        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
//
//            @Override
//            public String transformUrl(Matcher match, String url) {
//                return "";
//
//            }
//        };
//        Pattern pattern = Pattern.compile(linkItem.getList_Link());
//        Linkify.addLinks(holder.list_Link,pattern,linkItem.getList_Link(),null,transformFilter);

        holder.delete.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("삭제");
                builder.setMessage("삭제 하시겠습니까?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        int deletePosition = (int) view.getTag();
                        linkItems.remove(deletePosition);
                        notifyDataSetChanged();

                        SharedPreferences linkShared_remove = context.getSharedPreferences("link_shared",MODE_PRIVATE);
                        SharedPreferences.Editor linkShared_removeEdt = linkShared_remove.edit();

                        int link_list = linkShared_remove.getInt("link_list",0);

                        Gson gson = new Gson();
                        String json = gson.toJson(linkItems);
                        linkShared_removeEdt.putString("link"+link_list, json);
                        linkShared_removeEdt.apply();
                        System.out.println(json);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        holder.edit.setTag(position);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.activity_edit_link, null, false);
                builder.setView(view);

                final RadioButton armCheck = (RadioButton) view.findViewById(R.id.armCheck);
                final RadioButton legCheck = (RadioButton) view.findViewById(R.id.legCheck);
                final RadioButton muscleCheck = (RadioButton) view.findViewById(R.id.muscleCheck);

                final EditText mod_link = (EditText) view.findViewById(R.id.mod_link);
                final Button mod_bt = (Button) view.findViewById(R.id.mod_bt);

                mod_link.setText(linkItem.getList_Link());

                Log.d("tag", linkItem.getList_WhereEx() + "값");

                switch (linkItem.getList_WhereEx()) {
                    case "상체": {
                        armCheck.setChecked(true);
                        break;
                    }
                    case "하체": {
                        legCheck.setChecked(true);
                        break;
                    }
                    case "복부": {
                        muscleCheck.setChecked(true);
                        break;
                    }

                }

                AlertDialog dialog = builder.create();

                mod_bt.setOnClickListener(new View.OnClickListener() {//여기고치기
                    @Override
                    public void onClick(View view) {

                        String link = mod_link.getText().toString();
                        String WhereEx = null;

                        if (armCheck.isChecked() == true) {
                            armCheck.setChecked(true);
                            WhereEx = armCheck.getText().toString();

                        }
                        else if (legCheck.isChecked() == true) {
                            legCheck.setChecked(true);
                            WhereEx = legCheck.getText().toString();
                        }
                        else if (muscleCheck.isChecked() == true) {
                            muscleCheck.setChecked(true);
                            WhereEx = muscleCheck.getText().toString();
                        }

                        int image;
                        switch (WhereEx) {
                            case "상체": {
                                image = R.drawable.arm;
                                break;
                            }
                            case "하체": {
                                image = R.drawable.leg2;
                                break;
                            }
                            case "복부": {
                                image = R.drawable.sixpack;
                                break;
                            }
                            default:
                                image = R.drawable.ic_launcher_background;
                        }


                        LinkItem routine = new LinkItem(WhereEx,link,image);
                        linkItems.set(position,routine);
                        notifyDataSetChanged();
                        dialog.dismiss();

                        SharedPreferences linkShared_edit = context.getSharedPreferences("link_shared",MODE_PRIVATE);
                        SharedPreferences.Editor linkShared_editEdt = linkShared_edit.edit();

                        int link_list = linkShared_edit.getInt("link_list",0);

                        Gson gson = new Gson();
                        String json = gson.toJson(linkItems);
                        linkShared_editEdt.putString("link"+link_list, json);
                        linkShared_editEdt.apply();
                        System.out.println(json);
                    }
                });

                dialog.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return linkItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView list_WhereEx, list_Link;
        private ImageView list_image;
        public View delete;
        public View edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            list_WhereEx = itemView.findViewById(R.id.list_whereEx);
            list_Link = itemView.findViewById(R.id.list_Link);
            list_image = itemView.findViewById(R.id.imageView);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }

    }







}
