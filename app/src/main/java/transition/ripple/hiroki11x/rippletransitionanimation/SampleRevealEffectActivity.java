package transition.ripple.hiroki11x.rippletransitionanimation;

import android.os.Bundle;

import transition.ripple.hiroki11x.revealeffecttransition.RevealEffectActivity;

public class SampleRevealEffectActivity extends RevealEffectActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_reveal_effect);//これ消すとRevealできない
    }
}