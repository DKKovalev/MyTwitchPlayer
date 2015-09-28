package com.twitchplaya.psicho.mytwitchplayer.Assets;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitchplaya.psicho.mytwitchplayer.Assets.Models.TopStreamsModel;
import com.twitchplaya.psicho.mytwitchplayer.R;

import java.util.List;


public class RecyclerTopStreamsAdapter extends RecyclerView.Adapter<RecyclerTopStreamsAdapter.CustomViewHolder> {

    private List<TopStreamsModel.Top> topList;
    private Context context;
    private OnRecyclerItemClick recyclerItemClick;

    public RecyclerTopStreamsAdapter(List<TopStreamsModel.Top> topList, Context context) {
        this.topList = topList;
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, null);
        CustomViewHolder customViewHolder = new CustomViewHolder(view, context);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        TopStreamsModel.Top top = topList.get(position);
        //holder.gameTV.setText(top.getGame().getName());
        Picasso.with(context)
                .load(Uri.parse(top.getGame().getBox().getLarge()))
                .fit()
                .into(holder.thumbnailIV);
    }

    @Override
    public int getItemCount() {
        return topList.size();
    }

    public void setClickListener(OnRecyclerItemClick clickListener) {
        this.recyclerItemClick = clickListener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView gameTV;
        private ImageView thumbnailIV;
        private Context context;

        public CustomViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            //gameTV = (TextView) itemView.findViewById(R.id.tv_game);
            thumbnailIV = (ImageView)itemView.findViewById(R.id.iv_thumbnail);
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if (recyclerItemClick != null) {
                recyclerItemClick.itemClicked(v, getAdapterPosition());
            }
        }
    }

    public interface OnRecyclerItemClick {
        void itemClicked(View view, int pos);
    }
}
