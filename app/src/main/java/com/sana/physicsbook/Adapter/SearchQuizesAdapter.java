package com.sana.physicsbook.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sana.physicsbook.Fragments.TestFragment;
import com.sana.physicsbook.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class SearchQuizesAdapter extends RecyclerView.Adapter<SearchQuizesAdapter.SearchChapterHolder> {

    Context context;
    ArrayList<String> ChapterNumber, ChapterTitle;

    public SearchQuizesAdapter(Context c, ArrayList<String> ChapterNumber, ArrayList<String> ChapterTitle) {
        this.context = c;
        this.ChapterNumber = ChapterNumber;
        this.ChapterTitle = ChapterTitle;
    }

    @NonNull
    @Override
    public SearchChapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.category_design, parent, false);
        return new SearchChapterHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchChapterHolder holder, int position) {
        holder.textSearch1.setText(ChapterNumber.get(position));
        holder.textSearch2.setText(ChapterTitle.get(position));

        holder.setOnclickListener(new SearchChapterHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Fragment fragment = new TestFragment();

                Bundle args = new Bundle();
                args.putString("quiz", holder.textSearch2.getText().toString());
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ChapterTitle.size();
    }

    static class SearchChapterHolder extends RecyclerView.ViewHolder {

        TextView textSearch1, textSearch2;
        View view;

        public SearchChapterHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;

            textSearch1 = (TextView) itemView.findViewById(R.id.categoryTitle1Id);
            textSearch2 = (TextView) itemView.findViewById(R.id.categoryTitle2Id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        private SearchChapterHolder.ClickListener mClickListener;

        public interface ClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnclickListener(SearchChapterHolder.ClickListener clickListener) {
            mClickListener = clickListener;
        }

    }
}
