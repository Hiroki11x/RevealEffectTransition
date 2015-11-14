package transition.ripple.hiroki11x.revealeffecttransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * Created by hirokinaganuma on 15/11/14.
 */

public class RevealEffect {


    private static Interpolator interpolator;
    private static int delay = 100;
    private static int duration = 500;

    private static Intent mIntent;
    private static Context mContext;
    private static ViewGroup parentLayout;
    private static Window mWindow;
    private static Resources mResources;

    private static int reveal_center_x;
    private static int reveal_center_y;


    public static void bindAnimation(ViewGroup viewGroup, Intent argIntent, Context context, Window window, Resources resources) {

        mResources = resources;
        mWindow = window;
        mIntent = argIntent;
        mContext = context;
        parentLayout = viewGroup;

        setupWindowAnimations();
        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(parentLayout.getViewTreeObserver(), this);
                reveal_center_x = (int) mIntent.getFloatExtra("x", 0.0f);
                reveal_center_y = (int) mIntent.getFloatExtra("y", 0.0f);
                animateRevealColorFromCoordinates(reveal_center_x, reveal_center_y);
            }
        });
    }

    private static void removeOnGlobalLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (observer == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            observer.removeGlobalOnLayoutListener(listener);
        } else {
            observer.removeOnGlobalLayoutListener(listener);
        }
    }

    private static void setupWindowAnimations() {
        interpolator = AnimationUtils.loadInterpolator(mContext, android.R.interpolator.cycle);
        setupEnterAnimations();
        setupExitAnimations();
    }

    private static void setupEnterAnimations() {
        Transition transition = TransitionInflater.from(mContext).inflateTransition(R.transition.changebounds_with_arcmotion);
        mWindow.setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
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

    private static void setupExitAnimations() {
        Fade returnTransition = new Fade();
        mWindow.setReturnTransition(returnTransition);
        returnTransition.setDuration(mResources.getInteger(R.integer.anim_duration_medium));
        returnTransition.setStartDelay(mResources.getInteger(R.integer.anim_duration_medium));
        returnTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                transition.removeListener(this);
                animateButtonsOut();
                animateRevealHide(parentLayout);
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

    private static void animateRevealHide(final View viewRoot) {
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
        anim.setDuration(mResources.getInteger(R.integer.anim_duration_medium));
        anim.start();
    }

    private static void animateButtonsIn() {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.animate()
                    .setStartDelay(100 + i * delay)
                    .setInterpolator(interpolator)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }

    private static void animateButtonsOut() {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            child.animate()
                    .setStartDelay(i)
                    .setInterpolator(interpolator)
                    .alpha(0)
                    .scaleX(0f)
                    .scaleY(0f);
        }
    }

    private static Animator animateRevealColorFromCoordinates(int x, int y) {
        ViewGroup viewRoot = (ViewGroup) mWindow.getDecorView().findViewById(android.R.id.content);
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        anim.setDuration(duration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }

    private static Animator animateRevealColorFromCoordinatesFade(int x, int y) {
        ViewGroup viewRoot = (ViewGroup) mWindow.getDecorView().findViewById(android.R.id.content);
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
//        Animator anim = ValueAnimator.ofFloat(0.f, 100.f);

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 500, 0);
//        anim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//            @Override
//            public void onAnimationEnd(Animator animation) {
//            }
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
        anim.setDuration(duration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();

        return anim;
    }

    public static void unbindAnimation() {
        animateRevealColorFromCoordinatesFade(reveal_center_x, reveal_center_y);
    }


}