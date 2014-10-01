package org.nirbo.Layouts;

import com.vaadin.ui.VerticalLayout;

public class BodyLayout extends VerticalLayout {

    private MessagesTable messagesTable;

    public BodyLayout() {
        setSizeFull();

        messagesTable = new MessagesTable();
        addComponent(messagesTable);
    }

}
