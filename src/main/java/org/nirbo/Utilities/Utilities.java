package org.nirbo.Utilities;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

public class Utilities {

    public static void notification(String caption, String notifyColor) {
        Notification notification = new Notification("");
        notification.setCaption(caption);
        notification.setDelayMsec(4000);
        notification.setPosition(Position.TOP_RIGHT);
        notification.setStyleName(notifyColor);

        notification.show(Page.getCurrent());
    }

}
