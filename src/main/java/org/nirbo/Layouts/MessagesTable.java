package org.nirbo.Layouts;

import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Table;
import org.nirbo.MainUI;
import org.nirbo.Persistence.CinemallJPAContainer;

public class MessagesTable extends Table implements ItemClickEvent.ItemClickListener {

    private final static Action ACTION_EDIT = new Action("Edit");
    private final static Action ACTION_DELETE = new Action("Delete");
    Item clickedItem;

    CinemallJPAContainer splashMessages;

    public MessagesTable() {
        createTable();
    }

    private void createTable() {
        populateTable();
        configColumns();
        setTableProperties();
    }

    private void populateTable() {
        splashMessages = new CinemallJPAContainer();
        setContainerDataSource(splashMessages);
    }

    private void configColumns() {
        Object[] messageTableColumns = {"title", "content", "publishedDate", "startDate", "endDate", "active"};
        String[] messageTableHeaders = {"Title", "Content", "Published Date", "Start Date", "End Date", "Active"};

        setVisibleColumns(messageTableColumns);
        setColumnHeaders(messageTableHeaders);
    }

    private void setTableProperties() {
        setSizeFull();
        setBuffered(true);
        setSelectable(true);
        setImmediate(true);
        setReadOnly(false);
        setStyleName("noScrollBars");
        setPageLength(size());

        setActions();
        addItemClickListener(this);
    }

    private void setActions() {
        addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{ACTION_EDIT, ACTION_DELETE};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                if (ACTION_EDIT == action) {
                    MessageEditor editorWindow = new MessageEditor(clickedItem);
                    MainUI.getCurrent().addWindow(editorWindow);
                }

                if (ACTION_DELETE == action) {
                    removeItem(target);
                }
            }
        });
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        this.clickedItem = event.getItem();
    }

}
