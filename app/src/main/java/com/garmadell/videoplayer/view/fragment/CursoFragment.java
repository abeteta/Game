package com.garmadell.videoplayer.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.activity.CursoActivity;
import com.garmadell.videoplayer.activity.MainActivity;
import com.garmadell.videoplayer.activity.VideoActivity;
import com.garmadell.videoplayer.view.adapter.CursoAdapter;
import com.garmadell.videoplayer.view.adapter.ListAdapter;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Video;

import java.util.List;

/**
 * Created by alex on 10/21/17.
 */

public class CursoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CursoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Curso> beans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CursoAdapter(beans, (CursoActivity) getContext());
        mAdapter.setCallBack(new CursoAdapter.CallBack() {
            @Override
            public void onClick(View v, final Curso dto) {

            }
        });

        mLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return mRecyclerView;
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public CursoAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(CursoAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public RecyclerView.LayoutManager getmLayoutManager() {
        return mLayoutManager;
    }

    public void setmLayoutManager(RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    public List<Curso> getBeans() {
        return beans;
    }

    public void setBeans(List<Curso> beans) {
        this.beans = beans;
    }
}
