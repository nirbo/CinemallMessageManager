package org.nirbo.Layouts;

import com.vaadin.ui.VerticalLayout;

public class BodyLayout extends VerticalLayout {

    private MessagesTable messagesTable;

    public BodyLayout() {
        setSizeFull();

        messagesTable = new MessagesTable();
        messagesTable.setStyleName("tableCaption");
        addComponent(messagesTable);
    }

    public MessagesTable getMessagesTable() {
        return messagesTable;
    }

}
