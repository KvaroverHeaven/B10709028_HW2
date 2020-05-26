package com.prismdawn.b10709028_hw2;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prismdawn.b10709028_hw2.Data.SpwContract.SpwEntry;

public class SpwRecyclerViewAdapter extends RecyclerView.Adapter<SpwViewHolder>
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Cursor mCursor;
    private Context mContext;
    @ColorInt
    private int colorKey;

    public SpwRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public final SpwViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.list_item, parent, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        onSetColor(sharedPreferences);
        return new SpwViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpwViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(SpwEntry._ID);
        int nameIndex = mCursor.getColumnIndex(SpwEntry.COLUMN_NAME);
        int sizeIndex = mCursor.getColumnIndex(SpwEntry.COLUMN_SIZE);

        mCursor.moveToPosition(position);

        final long id = mCursor.getInt(idIndex);
        String name = mCursor.getString(nameIndex);
        int size = mCursor.getInt(sizeIndex);


        holder.itemView.setTag(id);
        holder.getNameTextView().setText(name);
        holder.getSizeTextView()
                .getBackground().setColorFilter(colorKey, PorterDuff.Mode.SRC_IN);
        holder.getSizeTextView().setText(String.valueOf(size));
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (mCursor == newCursor) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = newCursor;

        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(mContext.getResources().getString(R.string.pref_color_key))) {
            onSetColor(sharedPreferences);
        }
    }

    public void onSetColor(SharedPreferences sharedPreferences) {
        colorKey = setColor(sharedPreferences.getString(
                mContext.getResources().getString(R.string.pref_color_key),
                mContext.getResources().getString(R.string.pref_color_red_value)));
    }


    public int setColor(String newColorKey) {
        @ColorInt
        int color;

        if (newColorKey.equals(
                mContext.getResources().getString(R.string.pref_color_blue_value))) {
            color = ContextCompat.getColor(mContext, R.color.Blue);
        } else if (newColorKey.equals(
                mContext.getResources().getString(R.string.pref_color_green_value))) {
            color = ContextCompat.getColor(mContext, R.color.Green);
        } else {
            color = ContextCompat.getColor(mContext, R.color.Red);
        }
        return color;
    }


}
