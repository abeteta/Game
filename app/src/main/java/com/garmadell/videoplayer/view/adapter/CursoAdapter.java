package com.garmadell.videoplayer.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.activity.CursoActivity;
import com.garmadell.videoplayer.activity.MainActivity;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Michael Estrada on 10/21/17.
 */

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursoViewHolder> {
    protected List<Curso> beans;
    private CursoActivity mainActivity;
    static CursoAdapter.CallBack mCallBack;
    boolean[] checkedStatus = null;

    public CursoAdapter(List<Curso> beans, CursoActivity mainActivity) {
        this.beans = beans;
        this.mainActivity = mainActivity;
        checkedStatus = new boolean[beans.size()];
    }

    public interface CallBack {
        void onClick(View v, Curso dto);
    }

    @Override
    public void onBindViewHolder(CursoAdapter.CursoViewHolder listViewHolder, final int position) {

        Curso dto = beans.get(position);
        listViewHolder.text_curso.setText(dto.getDesc_curso());
        //Picasso.with(mainActivity.getApplicationContext()).load("http://ciencias.javeriana.edu.co/documents/3722984/3781371/logo.jpg/2c8d3e4b-8cd6-45d5-9a13-a72129d7768d?t=1443572960730&imagePreview=1").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(listViewHolder.imgPortada);

        //holder.bind should not trigger onCheckedChanged, it should just update UI
        listViewHolder.check_curso.setOnCheckedChangeListener(null);

        boolean checked = checkedStatus[position];
        if (!checked) {
            listViewHolder.check_curso.setChecked(false);
        } else {
            listViewHolder.check_curso.setChecked(true);
        }
        listViewHolder.check_curso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedStatus[position] = true;
                } else {
                    checkedStatus[position] = false;
                }
            }
        });
        //Picasso.with(mainActivity.getApplicationContext()).load("http://i.imgur.com/QQL2Htb.png").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(listViewHolder.imgPortada);

        //Picasso.with(mainActivity.getApplicationContext()).load(dto.getUrlPortada()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(listViewHolder.imgPortada);
    }


    public boolean[] getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(boolean[] checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    @Override
    public int getItemCount() {
        if (beans == null)
            return 0;
        return this.beans.size();
    }

    public CursoAdapter.CallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(CursoAdapter.CallBack mCallBack) {
        CursoAdapter.mCallBack = mCallBack;
    }

    @Override
    public CursoAdapter.CursoViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.fragment_list_curso, parent, false);
        return new CursoAdapter.CursoViewHolder(view, parent, beans);
    }

    public static class CursoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView text_curso;
        protected ImageView imgPortada;
        protected CheckBox check_curso;
        //private ViewGroup parent;
        protected List<Curso> items;

        public CursoAdapter.CallBack getCallBack() {
            return mCallBack;
        }

        public void setCallBack(CursoAdapter.CallBack mCallBack) {
            CursoAdapter.mCallBack = mCallBack;
        }

        public CursoViewHolder(View itemView, ViewGroup parent, List<Curso> list) {
            super(itemView);
            items = list;
            text_curso = itemView.findViewById(R.id.text_curso);
            imgPortada = itemView.findViewById(R.id.imgPortada);
            check_curso = itemView.findViewById(R.id.check_curso);



        }

        @Override
        public void onClick(View v) {
            if (mCallBack != null) {
                mCallBack.onClick(v, items.get(super.getPosition()));
            }
        }
    }
}
