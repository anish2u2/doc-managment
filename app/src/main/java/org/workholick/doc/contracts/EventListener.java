package org.workholick.doc.contracts;

import android.view.View;

public interface EventListener<T,P> {
    public void registerPalate(P palate);
    public void listen(T eventInstance);
}
