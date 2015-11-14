package transition.ripple.hiroki11x.rippletransitionanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.Button;

import transition.ripple.hiroki11x.revealeffecttransition.RevealEffect;

public class SampleRevealEffectActivity extends AppCompatActivity {

    Button button;

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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            RevealEffect.unbindAnimation(this);
            return false;
        }else{
            return false;
        }
    }
}