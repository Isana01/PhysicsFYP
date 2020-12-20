package com.sana.physicsbook.Holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sana.physicsbook.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolderChaptersCategoriesQuiz extends RecyclerView.ViewHolder {

    View view;
    TextView categoryTitle1Tv, categoryTitle2Tv;
    Context context;

    public HolderChaptersCategoriesQuiz(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        view = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

    }

    public void setCategories(Context context, String ChapterNumber, String ChapterTitle) {

        categoryTitle1Tv = view.findViewById(R.id.categoryTitle1Id);
        categoryTitle2Tv = view.findViewById(R.id.categoryTitle2Id);

        categoryTitle1Tv.setText(ChapterNumber);
        categoryTitle2Tv.setText(ChapterTitle);

    }

    private HolderChaptersCategoriesQuiz.ClickListener mClickListener;

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnclickListener(HolderChaptersCategoriesQuiz.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
