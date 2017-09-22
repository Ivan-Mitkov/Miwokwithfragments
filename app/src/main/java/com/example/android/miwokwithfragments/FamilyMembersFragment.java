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
public class FamilyMembersFragment extends Fragment {
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


    public FamilyMembersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager)getActivity(). getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));


        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word a = words.get(position);

                // Request audio focus for playback
                int audioFocusResult = audioManager.requestAudioFocus(audioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (audioFocusResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // Start playback. We have audiofocus now
                    mediaPlayer = MediaPlayer.create(getActivity(), a.getmAudio());
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
