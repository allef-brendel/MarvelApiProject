package com.e.marvelapiproject.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.e.marvelapiproject.MainActivity;
import com.e.marvelapiproject.QuadrinhoDetalheActivity;
import com.e.marvelapiproject.R;
import com.e.marvelapiproject.adapter.QuadrinhoAdapter;
import com.e.marvelapiproject.interfaces.RecycleViewOnClickListinerHack;
import com.e.marvelapiproject.pojo.Quadrinho;

import java.util.List;


public class QuadrinhoFragments extends Fragment implements RecycleViewOnClickListinerHack, View.OnClickListener {

    private static final String TAG = "TAG: ";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personagens,container, false);

        //RecyclerView
        RecyclerView mRecyclerView = view.findViewById(R.id.rv_fragment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView,this));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        List<Quadrinho> mList = ((MainActivity) getActivity()).queryDadosContent();
        QuadrinhoAdapter adapter = new QuadrinhoAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    //OnClick dos itens de listagem dos quadrinhos
    @Override
    public void onClickListiner(View view, int position) {

        try{
            YoYo.with(Techniques.FadeIn)
                    .duration(700)
                    .playOn(view);

            Intent intent = new Intent(getContext(), QuadrinhoDetalheActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);

        }
        catch (Exception  e){
            Log.i(TAG, "Fail");
        }
    }
    // OnLongPress dos itens de listagem dos quadrinhos
    public void onLongPressClickListiner(View view, int position) {

        try{
            YoYo.with(Techniques.FadeOut)
                    .duration(700)
                    .playOn(view);

            Toast.makeText(getContext(), "onLongPressClickListiner", Toast.LENGTH_SHORT).show();
        }
        catch (Exception  e){
            Log.i(TAG, "Fail");
        }
    }

    @Override
    public void onClick(View view) {
    }


    // TouchListener do Recycler View
    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{

        private Context mContex;
        private GestureDetector mGestureDetector;
        private RecycleViewOnClickListinerHack mRecycleViewOnClickListinerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecycleViewOnClickListinerHack rvoclh){
            mContex = c;
            mRecycleViewOnClickListinerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContex, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(),e.getY());
                    if (cv != null && mRecycleViewOnClickListinerHack != null){
                        mRecycleViewOnClickListinerHack.onLongPressClickListiner(cv, rv.getChildPosition(cv));
                    }
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    View cv = rv.findChildViewUnder(e.getX(),e.getY());

                    if (cv != null && mRecycleViewOnClickListinerHack != null){
                        mRecycleViewOnClickListinerHack.onClickListiner(cv, rv.getChildPosition(cv));
                    }
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
