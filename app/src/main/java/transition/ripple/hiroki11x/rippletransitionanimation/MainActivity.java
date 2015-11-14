package transition.ripple.hiroki11x.rippletransitionanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import transition.ripple.hiroki11x.revealeffecttransition.RevealTriggerIntent;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RevealTriggerIntent intent = new RevealTriggerIntent(MainActivity.this, SampleRevealEffectActivity.class);
                intent.setRevealIntentPivot(v);
                startActivity(intent);
            }
        });

    }
}
