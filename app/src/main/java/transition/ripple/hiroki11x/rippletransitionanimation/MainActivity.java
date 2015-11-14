package transition.ripple.hiroki11x.rippletransitionanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Button button;
    RelativeLayout layout;
//    private Toolbar toolbar;

    private Interpolator interpolator;

    private static final int DELAY = 100;
    protected static final String EXTRA_SAMPLE = "sample";
    protected static final String EXTRA_TYPE = "type";
    protected static final int TYPE_PROGRAMMATICALLY = 0;
    protected static final int TYPE_XML = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupTransitionAnimation();
        MyAnimation.setupWindowAnimations(getWindow());

    }

    public void setupTransitionAnimation(){
//        bindData();
        setupWindowAnimations();
        setupLayout();
        setupToolbar();
    }

    private void bindData() {
//        ActivityRevealBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        Sample sample = (Sample) getIntent().getExtras().getSerializable(EXTRA_SAMPLE);
//        binding.setReveal1Sample(sample);
    }

    private void setupWindowAnimations() {
        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.cycle);
        setupEnterAnimations();
        setupExitAnimations();
    }

    private void setupLayout() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        button = (Button) findViewById(R.id.button);
        layout = (RelativeLayout) findViewById(R.id.parentlayout);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NextActivity.class);
                View sharedView = layout;
                String transitionName = "CircularTransition";
                ActivityOptions transitionActivityOptions = ActivityOptions.makeClipRevealAnimation(sharedView, (int) button.getPivotX(), (int) button.getPivotY(), layout.getWidth(), layout.getHeight());
//                        .makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
//                ActivityOptions transitionActivityOptions = MyAnimation.myMakeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
//                ActivityOptions tr = ActivityOptions.makeClipRevealAnimation(sharedView,(int)button.getPivotX(),(int)button.getPivotY(),layout.getWidth(),layout.getHeight());
//                tr = ActivityOptions.makeCustomAnimation(MainActivity.this,R.transition.changebounds_with_arcmotion,R.transition.changebounds_with_arcmotion);
//                Animator anim = MyAnimation.revealAnimationStart(layout.getPivotX(),layout.getPivotY(),layout,MainActivity.this);
                startActivity(i, transitionActivityOptions.toBundle());
            }
        });
    }

    private void setupEnterAnimations() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                // Removing listener here is very important because shared element transition is executed again backwards on exit. If we don't remove the listener this code will be triggered again.
                transition.removeListener(this);
//                hideTarget();
//                animateRevealShow(toolbar);
                animateButtonsIn();
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    private void setupExitAnimations() {
        Fade returnTransition = new Fade();
        getWindow().setReturnTransition(returnTransition);
        returnTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.setStartDelay(getResources().getInteger(R.integer.anim_duration_medium));
        returnTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                animateButtonsOut();
                animateRevealHide(layout);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }
        });
    }

    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    private void animateButtonsIn() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(interpolator)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    private void animateButtonsOut() {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.animate()
                    .setStartDelay(i)
                    .setInterpolator(interpolator)
                    .alpha(0)
                    .scaleX(0f)
                    .scaleY(0f);
        }
    }

    private void animateRevealHide(final View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int initialRadius = viewRoot.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewRoot.setVisibility(View.INVISIBLE);
            }
        });
        anim.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        anim.start();
    }


    protected void setupToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

//    public static ActivityOptions makeClipRevealAnimation(View source,
//                                                          int startX, int startY, int width, int height) {
//        ActivityOptions opts = new ActivityOptions();
//        opts.mAnimationType = ANIM_CLIP_REVEAL;
//        int[] pts = new int[2];
//        source.getLocationOnScreen(pts);
//        opts.mStartX = pts[0] + startX;
//        opts.mStartY = pts[1] + startY;
//        opts.mWidth = width;
//        opts.mHeight = height;
//        return opts;
//    }


    public static class MyAnimation extends Activity {
        public static void setupWindowAnimations(Window window) {
            try {
                Explode explode = new Explode();
                explode.setDuration(2000);
                window.setExitTransition(explode);
                Fade fade = new Fade();
                window.setReenterTransition(fade);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static ActivityOptions myMakeSceneTransitionAnimation(Activity activity, View view, String str) {
            ActivityOptions result = ActivityOptions.makeSceneTransitionAnimation(activity, view, str);
            return result;
        }

        public static Animator revealAnimationStart(float x, float y,ViewGroup layout,Context context) {
            Animator anim =animateRevealColorFromCoordinates(layout, R.color.sample_yellow, (int) x, (int) y, context);

            return anim;
        }

        public static  Animator animateRevealColorFromCoordinates(ViewGroup viewRoot, @ColorRes int color, int x, int y,Context context) {
            int duration = 500;
            float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
            viewRoot.setBackgroundColor(ContextCompat.getColor(context, color));
            anim.setDuration(duration);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.start();
            return anim;
        }
    }
}
