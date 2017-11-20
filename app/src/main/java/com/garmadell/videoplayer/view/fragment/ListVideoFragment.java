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
import android.widget.ImageView;

import com.garmadell.videoplayer.activity.MainActivity;
import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.activity.VideoActivity;
import com.garmadell.videoplayer.view.adapter.ListAdapter;
import com.garmadell.videoplayer.view.bean.Video;

import java.util.List;

/**
 * Created by Michael Estrada on 7/10/2017.
 */

public class ListVideoFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Video> beans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ListAdapter(beans, (MainActivity) getContext());
        mAdapter.setCallBack(new ListAdapter.CallBack() {
            @Override
            public void onClick(View v, final Video dto, ImageView imgPortada) {


                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences settings = getContext().getSharedPreferences(getResources().getString(R.string.sharedPreferences), 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("id", 0);
                editor.putLong("position", 0L);
                editor.commit();

                Intent intent = new Intent(getContext(), VideoActivity.class);

                Bundle extras = new Bundle();
                extras.putInt("id", dto.getId());
                extras.putString("nombre", dto.getNombre());
                extras.putString("urlVideo", dto.getUrlVideo());
                extras.putInt("tipoVideo", dto.getTipoVideo());

                imgPortada.buildDrawingCache();
                Bitmap image= imgPortada.getDrawingCache();
                extras.putParcelable("imgPortada", image);
                intent.putExtras(extras);
                startActivity(intent);
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

    public ListAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(ListAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public RecyclerView.LayoutManager getmLayoutManager() {
        return mLayoutManager;
    }

    public void setmLayoutManager(RecyclerView.LayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    public List<Video> getBeans() {
        return beans;
    }

    public void setBeans(List<Video> beans) {
        this.beans = beans;
    }
}
