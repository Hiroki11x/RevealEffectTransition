package transition.ripple.hiroki11x.rippletransitionanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Button button;
    RelativeLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        Fade fade = new Fade();
//        fade.setDuration(10000);
//
//        getWindow().setExitTransition(fade);

        button = (Button) findViewById(R.id.button);
        layout = (RelativeLayout) findViewById(R.id.parentlayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RippleEffectActivity.class);
                i.putExtra("x",v.getX() +v.getWidth()/2);
                i.putExtra("y",v.getY()+v.getHeight()/2);
                startActivity(i);
            }
        });
    }
}
