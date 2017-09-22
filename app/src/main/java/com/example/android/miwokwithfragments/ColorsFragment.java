package com.example.android.miwokwithfragments;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {
    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();//pause and after gaining again tha audio focus
                        mediaPlayer.seekTo(0);//start  from the beggining
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                        // Stop playback
                    }
                }
            };



    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word( "red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        words.add(new Word( "green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new Word( "brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word( "gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word( "black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new Word( "white","kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new Word( "dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word( "mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words,R.color.category_colors);

        ListView listView = (ListView)rootView. findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word a =words.get(position);

                // Request audio focus for playback
                int audioFocusResult = audioManager.requestAudioFocus(audioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (audioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // Start playback. We have audiofocus now
                    mediaPlayer= MediaPlayer.create(getActivity(), a.getmAudio());
                    mediaPlayer.start();
                    //Clean up the media player by releasing its resources.
                    mediaPlayer.setOnCompletionListener(onComplet);
                }

            }
        });


        return rootView;
    }
    @Override
    public void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
    private MediaPlayer.OnCompletionListener onComplet= new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }

    };

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
            // Stop playback
        }
    }

}
