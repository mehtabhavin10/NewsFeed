package com.example.admin.newsfeed.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.newsfeed.Interfaces.ItemClickListener;
import com.example.admin.newsfeed.Model.RSSObject;
import com.example.admin.newsfeed.R;
import com.example.admin.newsfeed.WebActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView mTitleTextView, mTimestampTextView;
    public ImageView mDisplayImageView;
    private ItemClickListener itemClickListener;

    public FeedViewHolder(View itemView) {
        super(itemView);

        mTitleTextView = itemView.findViewById(R.id.title_tv);
        mTimestampTextView = itemView.findViewById(R.id.timestamp_tv);
        mDisplayImageView = itemView.findViewById(R.id.display_iv);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), true);
        return true;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {

        holder.mTitleTextView.setText(rssObject.getItems().get(position).getTitle());
        holder.mTimestampTextView.setText(rssObject.getItems().get(position).getPubDate());
        //holder.mContentTextView.setText(rssObject.getItems().get(position).getContent());
        String url = rssObject.getItems().get(position).getThumbnail();
        Picasso.get().load(url).into(holder.mDisplayImageView);
   /*     try {
            URL url = new URL(rssObject.getFeed().getImage());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.mDisplayImageView.setImageBitmap(bmp);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClicked) {

                if (!isLongClicked) {
                   // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    //mContext.startActivity(browserIntent);
                    String url = rssObject.getItems().get(position).getLink();
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("url", url);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.getItems().size();
    }
}
