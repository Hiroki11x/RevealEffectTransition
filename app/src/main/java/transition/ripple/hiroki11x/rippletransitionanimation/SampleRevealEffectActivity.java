package transition.ripple.hiroki11x.rippletransitionanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import transition.ripple.hiroki11x.revealeffecttransition.RevealEffect;

public class SampleRevealEffectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_reveal_effect);

        RevealEffect.bindAnimation(
                (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content),
                getIntent(),
                SampleRevealEffectActivity.this,
                getWindow(),
                getResources()
        );
    }

    @Override
    public void onStop(){
        RevealEffect.unbindAnimation();
        super.onStop();
    }

}