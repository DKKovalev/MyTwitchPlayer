package com.twitchplaya.psicho.mytwitchplayer.Assets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TopStreamsModel;
import com.twitchplaya.psicho.mytwitchplayer.R;

import java.util.List;


public class ListAdapter extends ArrayAdapter<TopStreamsModel.Top> {
    private List<TopStreamsModel.Top> topList;
    private Context context;

    public ListAdapter(Context context, int resource, List<TopStreamsModel.Top> objects) {
        super(context, resource, objects);
        this.context = context;
        this.topList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        TopStreamsModel.Top top = topList.get(position);
        TextView gameTV = (TextView) view.findViewById(R.id.game_tv);
        gameTV.setText(top.getGame().getName());
        return view;
    }
}
