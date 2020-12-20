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
import com.sana.physicsbook.Adapter.SearchChapterAdapter;
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

public class HomeFragment extends Fragment {

    DatabaseReference reference;
    FirebaseRecyclerAdapter<ModelChaptersCategory, HolderChaptersCategoriesQuiz> adapter;
    FirebaseRecyclerOptions<ModelChaptersCategory> options;
    RecyclerView mRecyclerViewCategories;
    ArrayList<String> ChapterNumber, ChapterTitle;
    SearchChapterAdapter searchChapterAdapter;
    EditText searchCategoryEt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        getActivity().setTitle("Home");

        ChapterNumber = new ArrayList<>();
        ChapterTitle = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("CategoryChapter");
        mRecyclerViewCategories = view.findViewById(R.id.rv_home);
        searchCategoryEt = view.findViewById(R.id.searchCategoryId);

        mRecyclerViewCategories.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerViewCategories.setLayoutManager(gridLayoutManager);
        mRecyclerViewCategories.addItemDecoration(new DividerItemDecoration(getContext(), GridLayoutManager.VERTICAL));


          searchCategoryEt.addTextChangedListener(new TextWatcher() {
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
        mRecyclerViewCategories.removeAllViews();

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

                    searchChapterAdapter = new SearchChapterAdapter(getContext(), ChapterNumber, ChapterTitle);
                    mRecyclerViewCategories.setAdapter(searchChapterAdapter);
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
            protected void onBindViewHolder(@NonNull HolderChaptersCategoriesQuiz holderChaptersCategory, int i, @NonNull final ModelChaptersCategory modelChaptersCategory) {

                try {
                    holderChaptersCategory.setCategories(getContext(), modelChaptersCategory.getChapterNumber(), modelChaptersCategory.getChapterTitle());
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
                }

                holderChaptersCategory.setOnclickListener(new HolderChaptersCategoriesQuiz.ClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

                        if (modelChaptersCategory.getChapterTitle().equals("Forces")){

                            Fragment fragment = new ChapterOneFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        if (modelChaptersCategory.getChapterTitle().equals("Autonomous Agents")){

                            Fragment fragment = new ChapterTwoFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        if (modelChaptersCategory.getChapterTitle().equals("Oscillations")){

                            Fragment fragment = new ChapterThreeFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        if (modelChaptersCategory.getChapterTitle().equals("Quantum Physics")){

                            Fragment fragment = new ChapterFourFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        if (modelChaptersCategory.getChapterTitle().equals("Astrology")){

                            Fragment fragment = new ChapterFiveFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

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
        }

        ;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerViewCategories.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        mRecyclerViewCategories.setAdapter(adapter);
    }
}
