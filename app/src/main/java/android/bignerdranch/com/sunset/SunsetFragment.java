package android.bignerdranch.com.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;

public class SunsetFragment extends Fragment {
    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    private int mBluSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);
        mSceneView = view;

        Resources resources = getResources();
        mBluSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.blue_sky);

        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);
        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        return view;
    }

    private void startAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();
        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBluSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        sunsetAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(3000);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();

    }
}
