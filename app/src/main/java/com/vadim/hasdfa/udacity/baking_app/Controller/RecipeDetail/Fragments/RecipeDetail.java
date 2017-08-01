package com.vadim.hasdfa.udacity.baking_app.Controller.RecipeDetail.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.vadim.hasdfa.udacity.baking_app.Model.RecipesHelper;
import com.vadim.hasdfa.udacity.baking_app.Model.Step;
import com.vadim.hasdfa.udacity.baking_app.R;

import java.util.ArrayList;

/**
 * Created by Raksha Vadim on 01.08.17, 20:03.
 */

public class RecipeDetail extends Fragment {
    private OnButtonPressed mCallback;
    public interface OnButtonPressed {
        void onPrevious(Button button);
        void onNext(Button button);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mCallback == null) {
            try {
                mCallback = (OnButtonPressed) context;
            } catch (Exception e) {
                e.printStackTrace();
                mCallback = null;
            }
        }
    }


    public static final String PLAYER_NAME = "recipe_videos";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView noVideo;

    String videoUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getBoolean("isToolbarHidden"))
            if (getActivity().getActionBar() != null)
                getActivity().getActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reciple_detail_fragment, container, false);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.simpleExoPlayer);
        noVideo = (TextView) view.findViewById(R.id.no_video);
        noVideo.setVisibility(View.INVISIBLE);

        ArrayList<Step> steps = RecipesHelper.shared()
                .getRecipes()
                .get(getArguments()
                        .getInt("selectedR")
                ).getSteps();
        int stepNum = getArguments().getInt("selectedS");
        Step step = steps
                .get(
                        stepNum
                );

        TextView info = (TextView) view.findViewById(R.id.step_instruction);
        if (info != null) {
            info.setText(step.getDescription());
        }

        videoUrl = step.getVideoUrl();

        Button nextButton = view.findViewById(R.id.next_button);
        Button previousButton = view.findViewById(R.id.previous_button);
        if (nextButton != null
                && previousButton != null) {
            if (stepNum == 0) {
                previousButton.setEnabled(false);
            }
            if (stepNum == steps.size()-1) {
                nextButton.setEnabled(false);
            }

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallback != null) {
                        mCallback.onNext((Button) view);
                    }
                }
            });
            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallback != null) {
                        mCallback.onPrevious((Button) view);
                    }
                }
            });
        }

        return view;
    }


    private void initializePlayer(Uri uri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepearing the MediaSource
            String userAgent = Util.getUserAgent(getContext(), PLAYER_NAME);
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null
            );
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoUrl == null || videoUrl.equals("")) {
            Log.d("myLog", "videoURL is null");
            mPlayerView.setBackgroundColor(Color.BLACK);
            noVideo.setVisibility(View.VISIBLE);
        } else {
            Log.d("myLog", "videoURL: " + videoUrl);
            Uri video = Uri.parse(videoUrl);
            initializePlayer(video);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    int currentTimeline = 0;
    private void releasePlayer() {
        if (mExoPlayer != null) {
            currentTimeline = (int) mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("vURL", videoUrl);
        outState.putInt("exoPlayerTime", currentTimeline);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            if (videoUrl != null)
                if (!videoUrl.equals("")) {
                    videoUrl = savedInstanceState.getString("vURL");
                    releasePlayer();
                    initializePlayer(Uri.parse(videoUrl));
                }
            int exoPlayerTime = savedInstanceState.getInt("exoPlayerTime");
            if (mExoPlayer != null) {
                mExoPlayer.seekTo(exoPlayerTime);
            }
        }
    }
}
