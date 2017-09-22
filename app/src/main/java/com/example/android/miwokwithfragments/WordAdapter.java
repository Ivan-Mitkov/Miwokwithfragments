package com.example.android.miwokwithfragments;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ivan on 16.9.2017 г..
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;
    //private  int mSoundResource;
    public WordAdapter(@NonNull Activity context, ArrayList<Word> words, int colorResourceId) {
        super(context,0, words);
       // mSoundResource=soundResource;
        mColorResourceId=colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        miwokTextView.setText(currentWord.getMiwokWord());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView

        defaultTextView.setText(currentWord.getDefaultTranslation());
        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        //int mSoundResource= (int)currentWord.getPos();
        //If there's no image
        if(currentWord.getImageResourceId()==0){

            iconView.setVisibility(View.GONE);
        }
        //else
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        iconView.setImageResource(currentWord.getImageResourceId());
        // Set the theme color for the list item
         View textContainer = listItemView.findViewById(R.id.text_container);
         // Find the color that the resource ID maps to
         int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
         textContainer.setBackgroundColor(color);
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView

        return listItemView;

    }



}
