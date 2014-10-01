package org.nirbo.Layouts;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.nirbo.MainUI;

public class MessageEditor extends Window implements Button.ClickListener {

    private FieldGroup group;
    private Button saveButton;
    private Button cancelButton;

    public MessageEditor(Item messageItem) {
        group = new FieldGroup(messageItem);
        group.isBuffered();
        group.setFieldFactory(new DateTimeFieldGroupFieldFactory());

        Field<?> title = group.buildAndBind("Title", "title");
        Field<?> content = group.buildAndBind("Content", "content");
        Field<?> startDate = group.buildAndBind("Start Date", "startDate");
        Field<?> endDate = group.buildAndBind("End Date", "endDate");
        Field<?> active = group.buildAndBind("Active", "active");

        title.isRequired();
        content.isRequired();
        startDate.isRequired();
        endDate.isRequired();

        active.setStyleName("editWindowCheckBox");

        saveButton = new Button("Save");
        saveButton.addClickListener(this);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton = new Button("Cancel");
        cancelButton.addClickListener(this);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        buttonsLayout.setMargin(true);
        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);

        VerticalLayout windowLayout = new VerticalLayout();
        windowLayout.setSpacing(true);
        windowLayout.setMargin(true);
        windowLayout.addComponent(title);
        windowLayout.addComponent(content);
        windowLayout.addComponent(startDate);
        windowLayout.addComponent(endDate);
        windowLayout.addComponent(active);
        windowLayout.addComponent(buttonsLayout);

        center();
        setModal(true);
        setResizable(false);
        setSizeUndefined();
        setImmediate(true);
        setCaption("Edit Message");

        setContent(windowLayout);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        if (event.getButton().getCaption().equals("Cancel")) {
            group.discard();
            MainUI.getCurrent().removeWindow(this);
        }

        if (event.getButton().getCaption().equals("Save")) {
            try {
                group.commit();
                MainUI.getCurrent().removeWindow(this);
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }
        }
    }



}
