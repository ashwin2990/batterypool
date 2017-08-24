package com.example.ashwinshankar.batterypool;

/**
 * Created by ashwinshankar on 8/23/17.
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

public class MyAdapterLocation extends ArrayAdapter<String> {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> distances = new ArrayList<>();
    ArrayList<Integer> flags = new ArrayList<>();
    Context mContext;

    public MyAdapterLocation(Context context, ArrayList<String> locationName, ArrayList<Integer> locationDistance, ArrayList<Integer> locationImg) {
        super(context, R.layout.list_view);
        this.names = locationName;
        this.distances = locationDistance;
        this.flags = locationImg;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        mViewHolder.mFlag.setImageResource(flags.get(position));
        mViewHolder.mName.setText(names.get(position));

        mViewHolder.mCount.setText(distances.get(position) + " kms away");
        return convertView;
    }

    static class ViewHolder {
        ImageView mFlag;
        TextView mName;
        TextView mCount;
    }
}