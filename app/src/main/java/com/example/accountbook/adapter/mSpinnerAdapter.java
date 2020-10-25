package com.example.accountbook.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class mSpinnerAdapter<T> extends ArrayAdapter<T> {

    private int selectedPostion;

    public void setSelectedPostion(int selectedPostion) {
        this.selectedPostion = selectedPostion;
    }

    public mSpinnerAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = (TextView)view;
        if(selectedPostion == position){
            textView.setTextColor(0xff373741);
            textView.getPaint().setFakeBoldText(true);
        }
        else{
            textView.setTextColor(0xff6d6d6d);
            textView.getPaint().setFakeBoldText(false);
        }
        return view;
    }

    public static @NonNull
    mSpinnerAdapter<CharSequence> createFromResource(@NonNull Context context,
                                                     @ArrayRes int textArrayResId, @LayoutRes int textViewResId) {
        final CharSequence[] strings = context.getResources().getTextArray(textArrayResId);
        return new mSpinnerAdapter<>(context, textViewResId, strings);
    }
}
