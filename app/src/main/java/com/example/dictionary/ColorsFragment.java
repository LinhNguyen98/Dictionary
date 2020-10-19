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

public class ColorsFragment extends Fragment{
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



    public ColorsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);



        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("red", "màu đỏ",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("green", "màu xanh lá cây",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("brown", "màu nâu",R.drawable.color_brown,R.raw.color_brown));
        words.add(new Word("gray", "màu xám",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("black", "màu đen",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("white", "màu trắng",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("blue", "màu xanh dương",R.drawable.color_blue,R.raw.color_blue));
        words.add(new Word("pink", "màu hồng",R.drawable.color_pink,R.raw.color_pink));
        words.add(new Word("purple", "màu tím",R.drawable.color_purple,R.raw.color_purple));
        words.add(new Word("orange", "màu cam",R.drawable.color_orange,R.raw.color_orange));
        words.add(new Word("yellow", "màu vàng",R.drawable.color_yellow,R.raw.color_yellow));






        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);


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
