package transition.ripple.hiroki11x.revealeffecttransition;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by hirokinaganuma on 15/11/14.
 */
public class RevealTriggerIntent extends Intent {

    public RevealTriggerIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);

    }

    public void setRevealIntentPivot(View v) {
        this.putExtra("x", v.getX() + v.getWidth() / 2);
        this.putExtra("y", v.getY() + v.getHeight() / 2);
    }

}
