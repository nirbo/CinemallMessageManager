package org.nirbo.Layouts;

import com.vaadin.ui.VerticalLayout;

public class PageLayout extends VerticalLayout {

    private BodyLayout bodyLayout;

    MessagesTable messagesTable;

    public PageLayout() {
        setMargin(true);
        setSizeFull();

        bodyLayout = new BodyLayout();
        addComponent(bodyLayout);
    }

    public BodyLayout getBodyLayout() {
        return bodyLayout;
    }

}
