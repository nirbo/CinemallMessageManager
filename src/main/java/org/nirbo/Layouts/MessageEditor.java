package org.nirbo.Layouts;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.*;

public class MessageEditor extends Window {

    private FieldGroup group;
    private Button saveButton;
    private Button cancelButton;

    public MessageEditor(Item messageItem) {
        group = new FieldGroup(messageItem);

        Field<?> title = group.buildAndBind("Title", "title", TextField.class);
        Field<?> content = group.buildAndBind("Content", "content", TextField.class);
        Field<?> publishedDate = group.buildAndBind("Published Date", "publishedDate", DateField.class);
        Field<?> startDate = group.buildAndBind("Start Date", "startDate", DateField.class);
        Field<?> endDate = group.buildAndBind("End Date", "endDate", DateField.class);
        Field<?> active = group.buildAndBind("Active", "active");

        publishedDate.setReadOnly(true);

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");
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
        windowLayout.addComponent(publishedDate);
        windowLayout.addComponent(startDate);
        windowLayout.addComponent(endDate);
        windowLayout.addComponent(active);
        windowLayout.addComponent(buttonsLayout);

        center();
        setModal(true);
        setResizable(false);
        setSizeUndefined();
        setCaption("Edit Message");

        setContent(windowLayout);
    }



}
