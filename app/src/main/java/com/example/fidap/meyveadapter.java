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

public class meyveadapter extends RecyclerView.Adapter<meyveadapter.meyveViewHolder> {
    String veri1[];
    int resimler[];
    Context context ;

    public  meyveadapter (Context c, String s1[], int resimler1[]){
        context = c ;
        veri1 = s1;
        resimler = resimler1;

    }
    @NonNull
    @Override
    public meyveadapter.meyveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_meyvesatirgorunumu,parent,false);
        return  new meyveViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull meyveadapter.meyveViewHolder holder, int position) {
    holder.meyveadi.setText(veri1[position]);
    holder.resimmm.setImageResource(resimler[position]);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = null;
            switch (position) {
                case 0:
                    intent = new Intent(context, a1domatesinfo.class);
                    break;
                case 1:
                    intent = new Intent(context, a1salatalikinfo.class);
                    break;
                case 2:
                    intent = new Intent(context, a1patlicaninfo.class);
                    break;
                case 3:
                    intent = new Intent(context, a1biberinfo.class);
                    break;
                case 4:
                    intent = new Intent(context, a1soganinfo.class);
                    break;
                case 5:
                    intent = new Intent(context, a1ispanakinfo.class);
                    break;
                case 6:
                    intent = new Intent(context, a1marulinfo.class);
                    break;
                case 7:
                    intent = new Intent(context,a1patatesinfo.class);
                    break;
                case 8:
                    intent = new Intent(context, a1havucinfo.class);
                    break;
                case 9:
                    intent = new Intent(context, a1cilekinfo.class);
                    break;
                case 10:
                    intent = new Intent(context, a1kavuninfo.class);
                    break;
                case 11:
                    intent = new Intent(context, a1karpuzinfo.class);
                    break;
                case 12:
                    intent = new Intent(context, a1bodurelmainfo.class);
                    break;
                case 13:
                    intent = new Intent(context, a1narinfo.class);
                    break;
            }
            if (intent != null) {
                context.startActivity(intent);
            }

        });
    }
    @Override
    public int getItemCount() {
        return resimler.length;
    }
    public class meyveViewHolder extends RecyclerView.ViewHolder {
        TextView meyveadi;
        ImageView resimmm;



        public meyveViewHolder(@NonNull View itemView) {
            super(itemView);
            meyveadi = itemView.findViewById(R.id.bitkiadi);
            resimmm = itemView.findViewById(R.id.bitkiresmi);
        }
    }
}
