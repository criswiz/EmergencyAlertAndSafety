package com.example.criswiz.emergencyalertandsafety.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.criswiz.emergencyalertandsafety.Common.ISO08601Parse;
import com.example.criswiz.emergencyalertandsafety.DetailsActivity;
import com.example.criswiz.emergencyalertandsafety.Interface.ItemClickListner;
import com.example.criswiz.emergencyalertandsafety.Model.Article;
import com.example.criswiz.emergencyalertandsafety.R;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ListNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ItemClickListner itemClickListner;

    TextView article_title;
    RelativeTimeTextView article_time;
    CircleImageView article_image;

    public ListNewsViewHolder(View itemView) {
        super(itemView);

        article_image = itemView.findViewById(R.id.article_image);
        article_time = itemView.findViewById(R.id.article_time);
        article_title = itemView.findViewById(R.id.article_title);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }
}

public class ListNewsAdapter extends RecyclerView.Adapter<ListNewsViewHolder> {

    private List<Article> articleList;
    private Context context;

    public ListNewsAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ListNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.news_layout, parent, false);
        return new ListNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNewsViewHolder holder, int position) {

        Picasso.get()
                .load(articleList.get(position).getUrlToImage())
                .into(holder.article_image);

        if (articleList.get(position).getTitle().length() > 65)
            holder.article_title.setText(articleList.get(position).getTitle().substring(0,65)+"...");
        else
            holder.article_title.setText(articleList.get(position).getTitle());

        Date date = null;
        try {
            date = ISO08601Parse.parse(articleList.get(position).getPublishedAt());
        }catch (ParseException ex){
            ex.printStackTrace();
        }
        holder.article_time.setReferenceTime(date.getTime());

        //set event click
        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                //
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("webURL", articleList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
