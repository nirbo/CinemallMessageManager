package org.nirbo.Layouts;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import org.nirbo.Model.SplashMessages;

public class MessagesTabSheet extends TabSheet implements ItemClickEvent.ItemClickListener {

    Table messageTable;
    Label addMessageLabel;
    JPAContainer<SplashMessages> messages;

    public MessagesTabSheet() {
        setSizeFull();
        VerticalLayout showMessagesTab = new VerticalLayout();
        VerticalLayout addMessageTab = new VerticalLayout();

        messageTable = createShowMessageTab();
        addMessageLabel = createAddMessageTab();

        addTab(showMessagesTab).setCaption("Cinemall Messages");
        showMessagesTab.addComponent(messageTable);
        addTab(addMessageTab).setCaption("Add a Message");
        addMessageTab.addComponent(addMessageLabel);
    }

    private Table createShowMessageTab() {
        messages = JPAContainerFactory.make(SplashMessages.class, "cinemallPU");
        Object[] messageTableColumns = {"title", "content", "publishedDate", "startDate", "endDate", "active"};
        String[] messageTableHeaders = {"Title", "Content", "Published Date", "Start Date", "End Date", "Active"};

        Table showMessageTable = new Table();
        showMessageTable.setContainerDataSource(messages);
        showMessageTable.setVisibleColumns(messageTableColumns);
        showMessageTable.setColumnHeaders(messageTableHeaders);
        showMessageTable.setSizeFull();
        showMessageTable.setBuffered(true);
        showMessageTable.setSelectable(true);
        showMessageTable.setImmediate(true);
        showMessageTable.setStyleName("noScrollBars");
        showMessageTable.setPageLength(showMessageTable.size());

        showMessageTable.addGeneratedColumn("Message Actions",
                new Table.ColumnGenerator() {
                    @Override
                    public Object generateCell(Table source, Object itemId, Object columnId) {
                        Button messageState = new Button("Set Active");
                        Button editRow = new Button("Edit");
                        Button deleteRow = new Button("Delete");

                        return null;
                    }
                });

        showMessageTable.addItemClickListener(this);
        return showMessageTable;
    }

    private Label createAddMessageTab() {
        Label tempLabel = new Label("Temporary Placeholder");
        return tempLabel;
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        Item item = event.getItem();
        Property property = item.getItemProperty(event.getPropertyId());

        Notification notification = new Notification(null, property.getValue().toString(), Notification.Type.HUMANIZED_MESSAGE);
        notification.setStyleName("green");
        notification.setPosition(Position.TOP_RIGHT);
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }
}
