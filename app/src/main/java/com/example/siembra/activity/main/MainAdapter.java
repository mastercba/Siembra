package com.example.siembra.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siembra.R;
import com.example.siembra.model.Note;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter>{

    private Context context;
    private List<Note> notes;
    private ItemClickListener itemClickListener;

    public MainAdapter(Context context, List<Note> notes, ItemClickListener itemClickListener) {
        this.context = context;
        this.notes = notes;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note,
                parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Note note = notes.get(position);
        holder.tv_ban.setText(note.getBan());
        holder.tv_desp.setText(note.getDesp());
        holder.tv_resp.setText(note.getResp());
        holder.tv_tag.setText(note.getTag());
        holder.tv_date.setText(note.getDate());
        holder.card_item.setCardBackgroundColor(note.getColor());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_ban, tv_desp, tv_resp, tv_tag, tv_date;
        CardView card_item;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_ban     =  itemView.findViewById(R.id.ban);
            tv_desp    =  itemView.findViewById(R.id.desp);
            tv_resp    =  itemView.findViewById(R.id.resp);
            tv_tag     =  itemView.findViewById(R.id.tag);
            tv_date    =  itemView.findViewById(R.id.date);
            card_item  =  itemView.findViewById(R.id.card_item);

            this.itemClickListener = itemClickListener;
            card_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
