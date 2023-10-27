package com.test.listviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;



    public FruitAdapter(@NonNull Context context, int resource, @NonNull List<Fruit> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取当前项Fruit实例
        Fruit item = getItem(position);
        View view;
        ViewHolder viewHolder;
      //  View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //优化
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = view.findViewById(R.id.fruit_name);
            //将ViewHolder放在View中
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.fruitImage.setImageResource(item.getImageId());
        viewHolder.fruitName.setText(item.getName());

        return view;
    }

    /**
     * 优化
     * 用于对控件的实例进行缓存
     */
    class ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
    }
}
