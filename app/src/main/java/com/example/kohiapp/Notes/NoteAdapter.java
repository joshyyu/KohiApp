package com.example.kohiapp.Notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kohiapp.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private Context context;
    private List<NotesModel> notesModelList;

    public NoteAdapter(Context context) {
        this.context = context;
        notesModelList = new ArrayList<>();
    }

    public void add(NotesModel notesModel){
        notesModelList.add(notesModel);
        notifyDataSetChanged();
    }

    public void clear (){
        notesModelList.clear();
        notifyDataSetChanged();
    }

    public void filterList(List<NotesModel>newList){
        notesModelList = newList;
        notifyDataSetChanged();
    }

    public List<NotesModel>getList(){
        return notesModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotesModel notesModel = notesModelList.get(position);
        holder.title.setText(notesModel.getTitle());
        holder.context.setText(notesModel.getContext());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NoteUpdateActivity.class);
                intent.putExtra("id",notesModel.getId());
                intent.putExtra("title",notesModel.getTitle());
                intent.putExtra("context",notesModel.getContext());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return notesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title,context;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            title=itemView.findViewById(R.id.title);
            context=itemView.findViewById(R.id.context);
        }
    }

}

