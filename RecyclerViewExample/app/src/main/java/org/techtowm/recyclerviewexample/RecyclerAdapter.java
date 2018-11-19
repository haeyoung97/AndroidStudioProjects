package org.techtowm.recyclerviewexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{
    private ArrayList<Diary> diaryList;
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<Diary> diaryList){
        this.context = context;
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new ViewHolder(inflater.inflate(R.layout.item_card_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Diary diary = diaryList.get(position);

        viewHolder.title.setText(diary.getTitle());
        viewHolder.date.setText(diary.getDate());
        viewHolder.goButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(context, diary.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return diaryList.size();  }

}

class ViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView date;
    CardView goButton;
    public ViewHolder(@NonNull View itemView){
        super(itemView);
        title = itemView.findViewById(R.id.title);
        date = itemView.findViewById(R.id.date);
        goButton = itemView.findViewById(R.id.goButton);

    }
}
