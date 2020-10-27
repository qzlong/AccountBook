package com.example.accountbook.bean;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Model extends Detail implements Parcelable {
    public Model(){

    }
    public Model(Detail detail){
        this.setAccount(detail.getAccount1(),detail.getAccount2());
        this.setCategory(detail.getCategory1(),detail.getCategory2());
        this.setMember(detail.getMember());
        this.setMoney(detail.getMoney());
        this.setNote(detail.getNote());
        this.setProject(detail.getProject());
        this.setTime(detail.getTime());
        this.setTrader(detail.getTrader());
        this.setType(detail.getType());
    }
    protected Model(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        assert bundle != null;
        this.setType(bundle.getString("Type"));
        this.setTrader(bundle.getString("Store"));
        this.setProject(bundle.getString("Project"));
        this.setNote(bundle.getString("Note"));
        this.setMoney(bundle.getFloat("Money"));
        this.setCategory(bundle.getString("Category1"),bundle.getString("Category2"));
        this.setAccount(bundle.getString("Account1"),bundle.getString("Account2"));
        this.setMember(bundle.getString("Member"));
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putFloat("Money",getMoney());
        bundle.putString("Account1",getAccount1());
        bundle.putString("Account2",getAccount2());
        bundle.putString("Category1",getCategory1());
        bundle.putString("Category2",getCategory2());
        bundle.putString("Member",getMember());
        bundle.putString("Note",getNote());
        bundle.putString("Project",getProject());
        bundle.putString("Store",getTrader());
        bundle.putString("Type",getType());
        dest.writeBundle(bundle);
    }
}
