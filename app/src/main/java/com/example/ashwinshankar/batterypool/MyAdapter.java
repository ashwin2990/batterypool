package com.example.ashwinshankar.batterypool;

/**
 * Created by ashwinshankar on 8/22/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> count = new ArrayList<>();
    ArrayList<Integer> flags = new ArrayList<>();
    Context mContext;

    public MyAdapter(Context context, ArrayList<String> countryNames, ArrayList<Integer> countryFlags, ArrayList<String> countryCount) {
        super(context, R.layout.list_view);
        this.names = countryNames;
        this.count = countryCount;
        this.flags = countryFlags;
        this.mContext = context;
        System.out.println("In here");
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("In here2");
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_view, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView);
            mViewHolder.mCount = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        System.out.println("In here3");
        mViewHolder.mFlag.setImageResource(flags.get(position));
        mViewHolder.mName.setText(names.get(position).toString());

        mViewHolder.mCount.setText("Number of Cycles : " + count.get(position));
        System.out.println("In here" + names);
        return convertView;
    }

    static class ViewHolder {
        ImageView mFlag;
        TextView mName;
        TextView mCount;
    }
}


