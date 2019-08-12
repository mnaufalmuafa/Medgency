package com.example.medgency.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medgency.R;
import com.example.medgency.activity.SearchDokter;
import com.example.medgency.activity.SearchRS;
import com.example.medgency.adapter.HomeAdapter;
import com.example.medgency.adapter.ViewPagerHomeAdapter;
import com.example.medgency.model.Bacaan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    Handler h = new Handler();
    RecyclerView recyclerView;
    TextView toolbar_text;
    CardView CVRS, CVDokter;
    private int delay = 8000;
    int page = 0;
    private ViewPager viewPager;
    Runnable runnable;
    private int[] pagerIndex = {0};

    ArrayList<Bacaan> list;
    HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        final Context context = getActivity();
        recyclerView = view.findViewById(R.id.RecyclerViewArticleHome);

        CVRS = view.findViewById(R.id.CVRumahSakit);
        CVRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SearchRS.class);
            startActivity(intent);
            }
        });

        CVDokter = view.findViewById(R.id.CVDokter);
        CVDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchDokter.class);
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Bacaan").child("Menarik");
        reference.keepSynced(true);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Bacaan>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Bacaan p = dataSnapshot1.getValue(Bacaan.class);
                    list.add(p);
                }
                homeAdapter = new HomeAdapter(getContext(),list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerHomeAdapter(getContext()));
        WormDotsIndicator dotsIndicator = (WormDotsIndicator) view.findViewById(R.id.dots_indicator);
        dotsIndicator.setViewPager(viewPager);


        h.postDelayed(new Runnable() {
            public void run() {
                pagerIndex[0]++;
                if (pagerIndex[0] >= 6) {
                    pagerIndex[0] = 0;
                }

                viewPager.setCurrentItem(pagerIndex[0]);
                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                pagerIndex[0] = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.ToolbarHome);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar_text = view.findViewById(R.id.toolbar_text);
        toolbar_text.setText("Medgency");
        return view;
    }
}
