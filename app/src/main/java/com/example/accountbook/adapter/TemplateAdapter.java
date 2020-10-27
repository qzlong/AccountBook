package com.example.accountbook.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.KeepAccountActivity;
import com.example.accountbook.R;
import com.example.accountbook.bean.Model;
import com.example.accountbook.fragments.ExpenditureFragment;
import com.example.accountbook.fragments.IncomeFragment;
import com.example.accountbook.fragments.LoanFragment;
import com.example.accountbook.fragments.TransferFragment;
import com.example.accountbook.setting.TemplateItem;

import java.io.IOException;
import java.util.List;

public class TemplateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Model> mItemList;
    private Context mContext;
    public static final int ITEM_TYPE_EXPENDITURE = 1;
    public static final int ITEM_TYPE_INCOME = 2;
    public static final int ITEM_TYPE_TRANSFER = 3;
    public static final int ITEM_TYPE_LOAN = 4;
    private KeepAccountActivity activity;
    public TemplateAdapter(Context mContext, List<Model> mItemList, KeepAccountActivity activity) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_item_layout, parent, false);
        final TemplateAdapter.TemplateHolder holder = new TemplateAdapter.TemplateHolder(view);
        holder.optionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Bundle args = new Bundle();
                Model model = mItemList.get(position);
                args.putParcelable(model.getType(),model);
                switch (viewType){
                    case ITEM_TYPE_EXPENDITURE:
                        ExpenditureFragment expenditureFragment = (ExpenditureFragment) activity.getFragment(model.getType());
                        expenditureFragment.setArguments(args);
                        expenditureFragment.setDefaultValue();
                        activity.changePage(model.getType());
                        break;
                    case ITEM_TYPE_INCOME:
                        IncomeFragment incomeFragment = (IncomeFragment) activity.getFragment(model.getType());
                        incomeFragment.setArguments(args);
                        activity.changePage(model.getType());
                        incomeFragment.setDefaultValue();
                        break;
                    case ITEM_TYPE_LOAN:
                        LoanFragment loanFragment = (LoanFragment) activity.getFragment(model.getType());
                        loanFragment.setArguments(args);
                        activity.changePage(model.getType());
                        loanFragment.setDefaultValue();
                        break;
                    case ITEM_TYPE_TRANSFER:
                        TransferFragment transferFragment = (TransferFragment) activity.getFragment(model.getType());
                        transferFragment.setArguments(args);
                        activity.changePage(model.getType());
                        transferFragment.setDefaultValue();
                        break;
                }
            }
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Model model = mItemList.get(position);
        if (holder instanceof TemplateHolder){
            switch (holder.getItemViewType()) {
                case ITEM_TYPE_EXPENDITURE:
                    ((TemplateHolder) holder).template_Icon.setImageResource(R.drawable.zhichu);
                    ((TemplateHolder) holder).template_Value.setTextColor(0xFF32CD32);
                    break;
                case ITEM_TYPE_INCOME:
                    ((TemplateHolder) holder).template_Icon.setImageResource(R.drawable.shouru);
                    ((TemplateHolder) holder).template_Value.setTextColor(0xFFB22222);
                    break;
                case ITEM_TYPE_TRANSFER:
                    ((TemplateHolder) holder).template_Icon.setImageResource(R.drawable.zhuanzhang);
                    ((TemplateHolder) holder).template_Value.setTextColor(0xFF555555);
                    break;
                case ITEM_TYPE_LOAN:
                    ((TemplateHolder) holder).template_Icon.setImageResource(R.drawable.jiedai);
                    ((TemplateHolder) holder).template_Value.setTextColor(0xFF555555);
                    break;
                default:
            }
            ((TemplateHolder) holder).template_Type.setText(typeToCharacter(model.getType()));
            ((TemplateHolder) holder).template_Category.setText(modelToCategory(model));
            ((TemplateHolder) holder).template_Value.setText(Float.toString(model.getMoney()));
        }
    }

    private String modelToCategory(Model model) {
        switch (model.getType()){
            case "PAY":
            case "INCOME":
                return model.getCategory2();
            case "TRANSFER":
                return model.getAccount1()+"转入"+model.getAccount2();
            case "LOAN":
                return "借贷人"+model.getCategory2();
        }
        return null;
    }

    private String typeToCharacter(String type) {
        switch(type){
            case "PAY":
                return "支出";
            case "INCOME":
                return "收入";
            case "TRANSFER":
                return "转账";
            case "LOAN":
                return "借贷";
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int ViewType = 1;
        if (mItemList.get(position).getType().equals("PAY")) {
            ViewType = ITEM_TYPE_EXPENDITURE;
        } else if (mItemList.get(position).getType().equals("INCOME")) {
            ViewType = ITEM_TYPE_INCOME;
        } else if (mItemList.get(position).getType().equals("TRANSFER")) {
            ViewType = ITEM_TYPE_TRANSFER;
        } else if (mItemList.get(position).getType().equals("LOAN")) {
            ViewType = ITEM_TYPE_LOAN;
        }
        return ViewType;
    }
    static class TemplateHolder extends RecyclerView.ViewHolder{
        View optionView;
        ImageView template_Icon;
        TextView template_Type;
        TextView template_Category;
        TextView template_Value;
        public TemplateHolder(View view){
            super(view);
            optionView = view;
            template_Icon = view.findViewById(R.id.template_icon);
            template_Type = view.findViewById(R.id.template_type);
            template_Category = view.findViewById(R.id.template_category);
            template_Value = view.findViewById(R.id.template_value);
        }
    }
}