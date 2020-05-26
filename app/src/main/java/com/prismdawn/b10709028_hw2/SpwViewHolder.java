package com.prismdawn.b10709028_hw2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpwViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView sizeTextView;

    public SpwViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.name_text_view);
        sizeTextView = itemView.findViewById(R.id.size_text_view);
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getSizeTextView() {
        return sizeTextView;
    }
}
