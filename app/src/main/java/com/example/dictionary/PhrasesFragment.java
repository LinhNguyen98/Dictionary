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

public class PhrasesFragment extends Fragment {
    AudioManager mAudioManager;
    MediaPlayer mMediaPlayer;
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
    private MediaPlayer.OnCompletionListener mComletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };



    public PhrasesFragment() {
    }
    private static final int NO_IMAGE_PROVIDED = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);



        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("Where are you going?", "Bjan dự định đi đâu?",NO_IMAGE_PROVIDED,R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "Tên bạn là gì?",NO_IMAGE_PROVIDED,R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "Tên tôi là...",NO_IMAGE_PROVIDED,R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "Bạn cảm thấy như thê nào?",NO_IMAGE_PROVIDED,R.raw.phrase_im_feeling_good));
        words.add(new Word("I’m feeling good", "Tôi cảm thấy rất tốt",NO_IMAGE_PROVIDED,R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "Bạn đang đến à?",NO_IMAGE_PROVIDED,R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming", "Đúng, tôi đang đến",NO_IMAGE_PROVIDED,R.raw.phrase_yes_im_coming));
        words.add(new Word("Let’s go", "Đi thôi nào",NO_IMAGE_PROVIDED,R.raw.phrase_lets_go));
        words.add(new Word("Come here", "Đến đây ",NO_IMAGE_PROVIDED,R.raw.phrase_come_here));
        words.add(new Word("What color is it?", "Màu của nó là gì?",NO_IMAGE_PROVIDED,R.raw.what_color_is_it));
        words.add(new Word("It is...", "Đến đây ",NO_IMAGE_PROVIDED,R.raw.it_is));




        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_phrases);


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
