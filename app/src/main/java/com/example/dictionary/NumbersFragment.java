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

public class NumbersFragment extends Fragment {
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



    public NumbersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);



        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("One","Một",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("Two","Hai",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("Three","Ba",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("Four","Bốn",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("Five","Năm",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("Six","Sáu",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("Seven","Bảy",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("Eight","Tám",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("Nine","Chín",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("Ten","Mười",R.drawable.number_ten,R.raw.number_ten));
        words.add(new Word("Eleven","Mười một",R.drawable.elevent,R.raw.eleven));
        words.add(new Word("Twelve","Mười hai",R.drawable.twelve,R.raw.twelve));
        words.add(new Word("Thirteen","Mười ba",R.drawable.thirteen,R.raw.thirteen));
        words.add(new Word("Fourteen","Mười bốn",R.drawable.fourteen,R.raw.fourteen));
        words.add(new Word("Fitteen","Mười lăm",R.drawable.fitteen,R.raw.fifteen));
        words.add(new Word("Sixteen","Mười sáu",R.drawable.sixteen,R.raw.sixteen));
        words.add(new Word("Seventeen","Mười bảy",R.drawable.seven,R.raw.seventeen));
        words.add(new Word("Eighteen","Mười tám",R.drawable.eighteen,R.raw.eighteen));
        words.add(new Word("Nineteen","Mười chín",R.drawable.nineteen,R.raw.nineteen));
        words.add(new Word("Twenty","Hai mươi",R.drawable.twenty,R.raw.twenty));









        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

//        ListView listView = (ListView) findViewById(R.id.list);
//
//        listView.setAdapter(itemsAdapter);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word resId = words.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // we have a audio focus now
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
