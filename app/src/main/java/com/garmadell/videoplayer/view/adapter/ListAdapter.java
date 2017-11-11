package com.garmadell.videoplayer.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garmadell.videoplayer.activity.MainActivity;
import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by garmaDell on 7/10/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    protected List<Video> beans;
    private MainActivity mainActivity;
    static CallBack mCallBack;

    public ListAdapter(List<Video> beans, MainActivity mainActivity) {
        this.beans = beans;
        this.mainActivity = mainActivity;
    }

    public interface CallBack {
        void onClick(View v, Video dto, ImageView imgPortada);
    }

    @Override
    public void onBindViewHolder(ListViewHolder listViewHolder, int position) {
        Video dto = beans.get(position);
        listViewHolder.txtNombre.setText(dto.getNombre());
        listViewHolder.txtDescripcion.setText(dto.getDescripcion());
        Picasso.with(mainActivity.getApplicationContext()).load(dto.getUrlPortada()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(listViewHolder.imgPortada);
    }

    @Override
    public int getItemCount() {
        if (beans == null)
            return 0;
        return this.beans.size();
    }

    public CallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(CallBack mCallBack) {
        ListAdapter.mCallBack = mCallBack;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.fragment_list_video, parent, false);
        return new ListViewHolder(view, parent, beans);
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView txtNombre;
        protected TextView txtDescripcion;
        protected ImageView imgPortada;

        //private ViewGroup parent;
        protected List<Video> items;

        public CallBack getCallBack() {
            return mCallBack;
        }

        public void setCallBack(CallBack mCallBack) {
            ListAdapter.mCallBack = mCallBack;
        }

        public ListViewHolder(View itemView, ViewGroup parent, List<Video> list) {
            super(itemView);
            items = list;
            txtNombre = itemView.findViewById(R.id.text_nombre);
            txtDescripcion = itemView.findViewById(R.id.text_descripcion);
            imgPortada = itemView.findViewById(R.id.imgPortada);
            imgPortada.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mCallBack != null) {
                mCallBack.onClick(v, items.get(super.getPosition()), imgPortada);
            }
        }
    }
}
