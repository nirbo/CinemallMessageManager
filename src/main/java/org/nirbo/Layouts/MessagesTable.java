package org.nirbo.Layouts;

import com.vaadin.data.Item;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Table;
import org.nirbo.MainUI;
import org.nirbo.Persistence.CinemallJPAContainer;
import org.vaadin.dialogs.ConfirmDialog;

public class MessagesTable extends Table implements ItemClickEvent.ItemClickListener {

    private final static Action ACTION_EDIT = new Action("Edit");
    private final static Action ACTION_DELETE = new Action("Delete");
    private Item clickedItem;
    private MessageEditor editorWindow;

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

        setConverter("active", new StringToBooleanConverter() {
            @Override
            protected String getTrueString() {
                return "Yes";
            }

            @Override
            protected String getFalseString() {
                return "No";
            }
        });
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
                    editorWindow = new MessageEditor(clickedItem);
                    MainUI.getCurrent().addWindow(editorWindow);
                }

                if (ACTION_DELETE == action) {
                    showDeleteConfirmation(target);
                }
            }
        });
    }

    private void showDeleteConfirmation(Object target) {
        ConfirmDialog.show(MainUI.getCurrent(), "Delete Message", "Are you sure?", "Delete", "Cancel", new ConfirmDialog.Listener() {
            @Override
            public void onClose(ConfirmDialog dialog) {
                if (dialog.isConfirmed()) {
                    removeItem(target);
                }
            }
        });
    }

    @Override
    public void itemClick(ItemClickEvent event) {
        select(event.getItemId());
        this.clickedItem = event.getItem();
    }

    public MessagesTable getMessagesTable() {
        return this;
    }

}
