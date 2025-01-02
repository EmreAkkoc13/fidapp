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

public class hayvanadapter extends RecyclerView.Adapter<hayvanadapter.hayvanViewHolder> {
   String veri1[];
   int resimler[];
   Context context ;
   public hayvanadapter (Context c , String s1[],int resiimler[]){
       veri1 = s1 ;
       resimler = resiimler;
       context = c ;
   }
    @NonNull
    @Override
    public hayvanadapter.hayvanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_hayvaninfosatirgorunumu,parent,false);
        return new hayvanViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull hayvanadapter.hayvanViewHolder holder, int position) {
    holder.hayvanadi.setText(veri1[position]);
    holder.hayvanresmi.setImageResource(resimler[position]);
      holder.itemView.setOnClickListener(v ->{
          Intent intentr = null ;
          switch (position) {
              case 0:
                  intentr = new Intent(context, a3kediinfo.class);
                  break;
              case 1:
                  intentr = new Intent(context, a3kopekinfo.class);
                  break;
              case 2:
                  intentr = new Intent(context, a3tavsaninfo.class);
                  break;
              case 3:
                  intentr = new Intent(context, a3muhabbetkusuinfo.class);
                  break;
              case 4:
                  intentr = new Intent(context, a3sukaplumbagasiinfo.class);
                  break;
              case 5:
                  intentr = new Intent(context, a3hamsterinfo.class);
                  break;
              case 6:
                    intentr=  new Intent(context, a3akvaryumbailigiinfo.class);
                    break;
              case 7:
                  intentr = new Intent(context, a3sultanpapaganiinfo.class);
                  break;
          }
        if(intentr != null){
            context.startActivities(new Intent[]{intentr});
        }

    });
    }

    @Override
    public int getItemCount() {
        return resimler.length;
    }

    public class hayvanViewHolder extends RecyclerView.ViewHolder {
      TextView   hayvanadi ;
      ImageView hayvanresmi;
        public hayvanViewHolder(@NonNull View itemView) {
            super(itemView);
            hayvanadi   = itemView.findViewById(R.id.hayvanadi);
            hayvanresmi = itemView.findViewById(R.id.hayvanresmi);
        }
    }
}
