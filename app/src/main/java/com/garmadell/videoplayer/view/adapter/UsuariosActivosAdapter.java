package com.garmadell.videoplayer.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.activity.CursoActivity;
import com.garmadell.videoplayer.activity.UsuariosActivosActivity;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.UsuariosActivos;

import java.util.List;

/**
 * Created by alex on 11/19/17.
 */

public class UsuariosActivosAdapter extends RecyclerView.Adapter<UsuariosActivosAdapter.UsuariosActivosViewHolder> {
    protected List<UsuariosActivos> beans;
    private UsuariosActivosActivity mainActivity;
    static UsuariosActivosAdapter.CallBack mCallBack;
    boolean[] checkedStatus = null;

    public UsuariosActivosAdapter(List<UsuariosActivos> beans, UsuariosActivosActivity mainActivity) {
        this.beans = beans;
        this.mainActivity = mainActivity;
        checkedStatus = new boolean[beans.size()];
    }

    public interface CallBack {
        void onClick(View v, UsuariosActivos dto);
    }

    @Override
    public void onBindViewHolder(UsuariosActivosAdapter.UsuariosActivosViewHolder listViewHolder, final int position) {

        UsuariosActivos dto = beans.get(position);
        listViewHolder.text_nombre.setText(dto.getNombre());
        //Picasso.with(mainActivity.getApplicationContext()).load("http://ciencias.javeriana.edu.co/documents/3722984/3781371/logo.jpg/2c8d3e4b-8cd6-45d5-9a13-a72129d7768d?t=1443572960730&imagePreview=1").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(listViewHolder.imgPortada);

        //holder.bind should not trigger onCheckedChanged, it should just update UI
        listViewHolder.check_usuario.setOnCheckedChangeListener(null);

        boolean checked = checkedStatus[position];
        if (!checked) {
            listViewHolder.check_usuario.setChecked(false);
        } else {
            listViewHolder.check_usuario.setChecked(true);
        }
        listViewHolder.check_usuario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedStatus[position] = true;
                } else {
                    checkedStatus[position] = false;
                }
            }
        });
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

    public UsuariosActivosAdapter.CallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(UsuariosActivosAdapter.CallBack mCallBack) {
        UsuariosActivosAdapter.mCallBack = mCallBack;
    }

    @Override
    public UsuariosActivosAdapter.UsuariosActivosViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.fragment_list_usuarios_activos, parent, false);
        return new UsuariosActivosAdapter.UsuariosActivosViewHolder(view, parent, beans);
    }

    public static class UsuariosActivosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView text_nombre;
        //protected ImageView imgPortada;
        protected RadioButton check_usuario;
        //private ViewGroup parent;
        protected List<UsuariosActivos> items;

        public UsuariosActivosAdapter.CallBack getCallBack() {
            return mCallBack;
        }

        public void setCallBack(UsuariosActivosAdapter.CallBack mCallBack) {
            UsuariosActivosAdapter.mCallBack = mCallBack;
        }

        public UsuariosActivosViewHolder(View itemView, ViewGroup parent, List<UsuariosActivos> list) {
            super(itemView);
            items = list;
            text_nombre = itemView.findViewById(R.id.text_nombre);
            check_usuario = itemView.findViewById(R.id.rdb_oponente);
        }

        @Override
        public void onClick(View v) {
            if (mCallBack != null) {
                mCallBack.onClick(v, items.get(super.getPosition()));
            }
        }
    }
}
