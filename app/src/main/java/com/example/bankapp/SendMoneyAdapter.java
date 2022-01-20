package com.example.bankapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SendMoneyAdapter extends RecyclerView.Adapter<ViewHolder> {

    sendMoney mSendMoney;
    List<CustomerModel> modelList;
    Context context;

    public SendMoneyAdapter(sendMoney mSendMoney, List<CustomerModel> modelList) {
        this.mSendMoney = mSendMoney;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_design, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mSendMoney.selectuser(position);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mName.setText(modelList.get(position).getName());
        holder.mPhonenumber.setText(modelList.get(position).getPhone());
        holder.mBalance.setText(modelList.get(position).getBankBalance());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void setFilter(ArrayList<CustomerModel> newList){
        modelList = new ArrayList<>();
        modelList.addAll(newList);
        notifyDataSetChanged();
    }

}

