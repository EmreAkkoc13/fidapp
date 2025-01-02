package com.example.fidap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class bitkiadapter extends RecyclerView.Adapter<bitkiadapter.ViewHolder> {
    String veri2[];
    int  resimler2[];
    Context context;
    public  bitkiadapter (Context c, String s1[], int res[]){
        veri2 = s1;
        resimler2 = res;
        context = c ;
    }
    @NonNull
    @Override
    public bitkiadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_bitkisatirgorunumu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bitkiadapter.ViewHolder holder, int position) {
        holder.bitkiadi.setText(veri2[position]);
        holder.bitkiresmi.setImageResource(resimler2[position]);
        holder.itemView.setOnClickListener(v-> {
            Intent ints = null ;
            switch (position){
                case 0:
                    ints = new Intent(context,a2kadifeinfo.class);
                    break;
                case 1:
                    ints = new Intent(context, a2kekikinfo.class);
                    break;
                case 2:
                    ints = new Intent(context, a2maydonozinfo.class);
                    break;
                case 3:
                    ints = new Intent(context, a2naneinfo.class);
                    break;
                case 4:
                    ints = new Intent(context, a2orkideinfo.class);
                    break;
                case 5:
                    ints = new Intent(context, a2papatyainfo.class);
                    break;
                case 6:
                    ints = new Intent(context, a2pasakiliciinfo.class);
                    break;
                case 7:
                    ints = new Intent(context,a2philodendroninfo.class);
                    break;
                case 8:
                    ints = new Intent(context, a2sardunyainfo.class);
                    break;
                case 9:
                    ints = new Intent(context, a2semizotuinfo.class);
                    break;
                case 10:
                    ints = new Intent(context, a2afrikameneksesiinfo.class);
                    break;
                case 11:
                    ints = new Intent(context, a2aleoverainfo.class);
                    break;
                case 12:
                    ints = new Intent(context, a2bariscicegiinfo.class);
                    break;
                case 13:
                    ints = new Intent(context,a2benjamininfo .class);
                    break;
                case 14:
                    ints = new Intent(context,a2biberiyeinfo .class);
                    break;
                case 15:
                    ints = new Intent(context,a2dereotuinfo .class);
                    break;
                case 16:
                    ints = new Intent(context,a2devetabaniinfo .class);
                    break;
                case 17:
                    ints = new Intent(context,a2feslegeninfo .class);
                    break;
                case 18:
                    ints = new Intent(context,a2gulinfo .class);
                    break;
            }
            if(ints != null){
                context.startActivities(new Intent[]{ints});
            }
        });
    }
    @Override
    public int getItemCount() {
        return resimler2.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView   bitkiadi;
        ImageView  bitkiresmi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bitkiadi    = itemView.findViewById(R.id.bitkiadi);
            bitkiresmi  = itemView.findViewById(R.id.bitkiresmi);
        }
    }
}
