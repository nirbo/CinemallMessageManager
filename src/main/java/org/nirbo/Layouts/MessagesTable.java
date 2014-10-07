package org.nirbo.Layouts;

import com.vaadin.data.Item;
import com.vaadin.data.util.converter.StringToBooleanConverter;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import org.nirbo.MainUI;
import org.nirbo.Persistence.CinemallJPAContainer;
import org.nirbo.Utilities.Utilities;
import org.vaadin.dialogs.ConfirmDialog;

import static com.vaadin.shared.MouseEventDetails.*;

public class MessagesTable extends Table implements ItemClickEvent.ItemClickListener {

    private final static Action ACTION_ADD = new Action("Add a Message");
    private final static Action ACTION_EDIT = new Action("Edit");
    private final static Action ACTION_DELETE = new Action("Delete");

    private Item clickedItem;
    private MessageEditor editorWindow;
    private MessageAdd addMessageWindow;
    private CinemallJPAContainer splashMessages;

    public MessagesTable() {
        createTable();
    }

    private void createTable() {
        setCaption("Cinemall Message Manager");

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
                return new Action[]{ACTION_ADD, ACTION_EDIT, ACTION_DELETE};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                if (ACTION_ADD == action) {
                    addMessageWindow = new MessageAdd(splashMessages);
                    MainUI.getCurrent().addWindow(addMessageWindow);
                }

                if (ACTION_EDIT == action) {
                    if (clickedItem == null) {
                        Utilities.notification("Please select a message and try again", "red");
                    } else {
                        editorWindow = new MessageEditor(clickedItem);
                        MainUI.getCurrent().addWindow(editorWindow);
                        clickedItem = null;
                    }
                }

                if (ACTION_DELETE == action) {
                    if (clickedItem == null) {
                        Utilities.notification("Please select a message and try again", "red");
                    } else {
                        showDeleteConfirmation(target);
                        clickedItem = null;
                    }
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
        if (MouseButton.LEFT == event.getButton()) {
            if (clickedItem == null) {
                this.clickedItem = event.getItem();
            } else {
                clickedItem = null;
            }
        } else if (MouseButton.RIGHT == event.getButton()) {
            select(event.getItemId());
            this.clickedItem = event.getItem();
        }
    }


}
