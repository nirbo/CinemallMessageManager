package org.nirbo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.nirbo.Model.SplashMessages;

public class MessagesTabSheet extends TabSheet {

    Table messageTable;
    Label addMessageLabel;
    JPAContainer<SplashMessages> messages;

    public MessagesTabSheet() {
        setSizeFull();
        VerticalLayout showMessagesTab = new VerticalLayout();
        VerticalLayout addMessageTab = new VerticalLayout();

        messageTable = createShowMessageTab();
        addMessageLabel = createAddMessageTab();

        addTab(showMessagesTab).setCaption("Show Messages");
        showMessagesTab.addComponent(messageTable);
        addTab(addMessageTab).setCaption("Add a Message");
        addMessageTab.addComponent(addMessageLabel);
    }

    private Table createShowMessageTab() {
        messages = JPAContainerFactory.make(SplashMessages.class, "cinemallPU");
        Object[] messageTableColumns = {"title", "content", "publishedDate", "startDate", "endDate", "active"};
        String[] messageTableHeaders = {"Title", "Content", "Published Date", "Start Date", "End Date", "Active"};

        Table showMessageTable = new Table(null, messages);
        showMessageTable.setVisibleColumns(messageTableColumns);
        showMessageTable.setColumnHeaders(messageTableHeaders);
        showMessageTable.setSizeFull();
        showMessageTable.setBuffered(true);
        showMessageTable.setSelectable(true);
        showMessageTable.setMultiSelect(true);
        showMessageTable.setMultiSelectMode(MultiSelectMode.DEFAULT);
        showMessageTable.setImmediate(true);

        return showMessageTable;
    }

    private Label createAddMessageTab() {
        Label tempLabel = new Label("Temporary Placeholder");
        return tempLabel;
    }

}
