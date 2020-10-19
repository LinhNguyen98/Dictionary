package com.example.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mBackGroundColor;

    public WordAdapter(Context context, ArrayList<Word> words, int backGroundColor) {
        super(context,0,words);
        mBackGroundColor = backGroundColor;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView defautlTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        defautlTextView.setText(currentWord.getDefautlTranslation());

        TextView vietTextView = (TextView) listItemView.findViewById(R.id.viet_text_view);

        vietTextView.setText(currentWord.getVietTranslation());



        View textCointainer = listItemView.findViewById(R.id.linear_layout_background);

        int color = ContextCompat.getColor(getContext(), mBackGroundColor);

        textCointainer.setBackgroundColor(color);



        return listItemView;
    }
}
