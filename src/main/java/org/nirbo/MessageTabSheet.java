package org.nirbo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.nirbo.Model.SplashMessages;

public class MessageTabSheet extends TabSheet {

    Table messageTable;
    Label addMessageLabel;
    JPAContainer<SplashMessages> messages;

    public MessageTabSheet() {
        setSizeFull();
        VerticalLayout showMessagesTab = new VerticalLayout();
        VerticalLayout addMessageTab = new VerticalLayout();

        messageTable = createShowMessageTable();
        addMessageLabel = createAddMessage();

        addTab(showMessagesTab).setCaption("Show Messages");
        showMessagesTab.addComponent(messageTable);
        addTab(addMessageTab).setCaption("Add a Message");
        addMessageTab.addComponent(addMessageLabel);
    }

    private Table createShowMessageTable() {
        messages = JPAContainerFactory.make(SplashMessages.class, "cinemallPU");
        Object[] messageTableColumns = {"title", "content", "publishedDate", "startDate", "endDate", "active"};
        String[] messageTableHeaders = {"Title", "Content", "Published Date", "Start Date", "End Date", "Active"};

        Table showMessageTable = new Table(null, messages);
        showMessageTable.setVisibleColumns(messageTableColumns);
        showMessageTable.setColumnHeaders(messageTableHeaders);
        showMessageTable.setSizeFull();
        showMessageTable.setBuffered(true);
        showMessageTable.setSelectable(true);
        showMessageTable.setMultiSelectMode(MultiSelectMode.SIMPLE);
        showMessageTable.setImmediate(true);

        return showMessageTable;
    }

    private Label createAddMessage() {
        Label tempLabel = new Label("Temporary Placeholder");
        return tempLabel;
    }

}
