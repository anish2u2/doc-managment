package org.workholick.doc.listener;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.workholick.doc.contracts.EventListener;

public class PasswordListener implements EventListener<View, TextView> {

    private TextView palate;

    @Override
    public void registerPalate(TextView palate) {
        this.palate=palate;
    }

    @Override
    public void listen(View eventInstance) {
        final TextView textView=palate;
        eventInstance.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                textView.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }
}
