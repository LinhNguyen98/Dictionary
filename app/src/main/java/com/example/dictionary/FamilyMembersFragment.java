package com.example.dictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FamilyMembersFragment extends Fragment {

    private MediaPlayer.OnCompletionListener mComletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                        releaseMediaPlayer();
                    }
                }
            };

    MediaPlayer mMediaPlayer;



    public FamilyMembersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);



        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("father", "bố",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("mother", "mẹ",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("son", "con trai",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("daughter", "con gái",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("older brother", "anh trai",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("younger brother", "em trai",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("older sister", "chị gái",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("younger sister", "em gái",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("grandmother ", "bà",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("grandfather", "ông",R.drawable.family_grandfather,R.raw.family_grandfather));
        words.add(new Word("aunt", "cô, dì",R.drawable.aunt,R.raw.aunt));
        words.add(new Word("uncle", "chú, cậu",R.drawable.uncle,R.raw.uncle));
        words.add(new Word("wife", "vợ",R.drawable.wife,R.raw.wife));
        words.add(new Word("husband", "chồng",R.drawable.husband,R.raw.husband));





        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_family
        );


        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word resId = words.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mMediaPlayer = MediaPlayer.create(getActivity(), resId.getmAudioResourceId());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mComletionListener);
                }
            }
        });


        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {

            mMediaPlayer.release();


            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
