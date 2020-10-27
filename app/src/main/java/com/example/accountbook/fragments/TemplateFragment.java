package com.example.accountbook.fragments;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountbook.KeepAccountActivity;
import com.example.accountbook.R;
import com.example.accountbook.adapter.TemplateAdapter;
import com.example.accountbook.bean.Model;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class TemplateFragment extends Fragment {
    private Context mContext;
    private ArrayList<Model> mItemList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.template, container, false);
        mContext = view.getContext();
        recyclerView = view.findViewById(R.id.template_recyclerview);
        return view;
    }

    private void initList(){
        mItemList.clear();
        String type1 = "PAY";
        String type2 = "INCOME";
        String type3 = "TRANSFER";
        String type4 = "LOAN";
        List<Model> ItemList1 = LitePal.where("Type like ?", type1).find(Model.class);
        List<Model> ItemList2 = LitePal.where("Type like ?",type2).find(Model.class);
        List<Model> ItemList3 = LitePal.where("Type like ?", type3).find(Model.class);
        List<Model> ItemList4 = LitePal.where("Type like ?",type4).find(Model.class);
        mItemList.addAll(ItemList1);
        mItemList.addAll(ItemList2);
        mItemList.addAll(ItemList3);
        mItemList.addAll(ItemList4);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        TemplateAdapter adapter = new TemplateAdapter(mContext,mItemList, (KeepAccountActivity) getActivity());
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }
}
