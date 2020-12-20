package com.sana.physicsbook.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sana.physicsbook.Adapter.SearchQuizesAdapter;
import com.sana.physicsbook.Holder.HolderChaptersCategoriesQuiz;
import com.sana.physicsbook.Model.ModelChaptersCategory;
import com.sana.physicsbook.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class QuizesFragment extends Fragment {

    DatabaseReference reference;
    FirebaseRecyclerAdapter<ModelChaptersCategory, HolderChaptersCategoriesQuiz> adapter;
    FirebaseRecyclerOptions<ModelChaptersCategory> options;
    RecyclerView mRecyclerViewQuizCategories;
    ArrayList<String> ChapterNumber, ChapterTitle;
    SearchQuizesAdapter searchQuizesAdapter;
    EditText searchQuizCategoryEt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);
        getActivity().setTitle("Quiz");

        ChapterNumber = new ArrayList<>();
        ChapterTitle = new ArrayList<>();

        mRecyclerViewQuizCategories = view.findViewById(R.id.rv_quiz_home);
        searchQuizCategoryEt = view.findViewById(R.id.search_quiz_CategoryId);
        reference = FirebaseDatabase.getInstance().getReference().child("CategoryChapter");

        mRecyclerViewQuizCategories.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerViewQuizCategories.setLayoutManager(gridLayoutManager);
        mRecyclerViewQuizCategories.addItemDecoration(new DividerItemDecoration(getContext(), GridLayoutManager.VERTICAL));

        searchQuizCategoryEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    filter(s.toString());
                } else {
                    filter("");
                }
            }
        });


        return view;
    }

    private void filter(final String searchedString) {

        ChapterNumber.clear();
        ChapterTitle.clear();
        mRecyclerViewQuizCategories.removeAllViews();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                int counter = 0;
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    String title1 = snapshot.child("ChapterNumber").getValue(String.class);
                    String title2 = snapshot.child("ChapterTitle").getValue(String.class);

                    if (title2.toLowerCase().contains(searchedString.toLowerCase())) {
                        ChapterNumber.add(title1);
                        ChapterTitle.add(title2);
                        counter++;
                    }

                    if (counter == 15) {
                        break;
                    }

                    searchQuizesAdapter = new SearchQuizesAdapter(getContext(), ChapterNumber, ChapterTitle);
                    mRecyclerViewQuizCategories.setAdapter(searchQuizesAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        options = new FirebaseRecyclerOptions.Builder<ModelChaptersCategory>()
                .setQuery(reference, ModelChaptersCategory.class).build();

        adapter = new FirebaseRecyclerAdapter<ModelChaptersCategory, HolderChaptersCategoriesQuiz>(options) {

            @Override
            protected void onBindViewHolder(@NonNull HolderChaptersCategoriesQuiz holderChaptersCategoriesQuiz, int i, @NonNull final ModelChaptersCategory modelChaptersCategory) {

                try {
                    holderChaptersCategoriesQuiz.setCategories(getContext(), modelChaptersCategory.getChapterNumber(), modelChaptersCategory.getChapterTitle());
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
                }

                holderChaptersCategoriesQuiz.setOnclickListener(new HolderChaptersCategoriesQuiz.ClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

                        Fragment fragment = new TestFragment();

                        Bundle args = new Bundle();
                        args.putString("quiz", modelChaptersCategory.getChapterTitle());
                        fragment.setArguments(args);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }

            @NonNull
            @Override
            public HolderChaptersCategoriesQuiz onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_design, parent, false);
                return new HolderChaptersCategoriesQuiz(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerViewQuizCategories.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        mRecyclerViewQuizCategories.setAdapter(adapter);
    }
}
