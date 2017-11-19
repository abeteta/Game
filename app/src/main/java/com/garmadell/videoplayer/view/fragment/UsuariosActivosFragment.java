package com.garmadell.videoplayer.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.activity.CursoActivity;
import com.garmadell.videoplayer.activity.UsuariosActivosActivity;
import com.garmadell.videoplayer.view.adapter.CursoAdapter;
import com.garmadell.videoplayer.view.adapter.UsuariosActivosAdapter;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.UsuariosActivos;

import java.util.List;

/**
 * Created by alex on 11/19/17.
 */

public class UsuariosActivosFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private UsuariosActivosAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<UsuariosActivos> beans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new UsuariosActivosAdapter(beans, (UsuariosActivosActivity) getContext());
        mAdapter.setCallBack(new UsuariosActivosAdapter.CallBack() {
            @Override
            public void onClick(View v, final UsuariosActivos dto) {

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

    public UsuariosActivosAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(UsuariosActivosAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public RecyclerView.LayoutManager getmLayoutManager() {
        return mLayoutManager;
    }

    public void setmLayoutManager(RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }
    public List<UsuariosActivos> getBeans() {
        return beans;
    }

    public void setBeans(List<UsuariosActivos> beans) {
        this.beans = beans;
    }
}
