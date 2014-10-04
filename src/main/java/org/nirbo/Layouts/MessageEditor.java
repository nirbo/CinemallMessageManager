package org.nirbo.Layouts;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.nirbo.Layouts.LayoutFactories.DateTimeFieldGroupFieldFactory;
import org.nirbo.MainUI;

public class MessageEditor extends Window implements Button.ClickListener {

    private FieldGroup group;
    private Button saveButton;
    private Button cancelButton;

    private Field<?> title;
    private Field<?> content;
    private Field<?> startDate;
    private Field<?> endDate;
    private Field<?> active;

    public MessageEditor(Item messageItem) {
        createAndBindFields(messageItem);
        configureFields();
        configureButtons();
        configureLayouts();

        setContent(configureLayouts());
    }

    private void createAndBindFields(Item messageItem) {
        group = new FieldGroup(messageItem);
        group.isBuffered();
        group.setFieldFactory(new DateTimeFieldGroupFieldFactory());

        title = group.buildAndBind("Title", "title");
        content = group.buildAndBind("Content", "content");
        startDate = group.buildAndBind("Start Date", "startDate");
        endDate = group.buildAndBind("End Date", "endDate");
        active = group.buildAndBind("Active", "active");
    }

    private void configureFields() {
        title.isRequired();
        title.setWidth(270, Unit.PIXELS);
        title.addValidator(new StringLengthValidator("The message should be between 1 and 25 characters", 1, 25, false));
        title.focus();

        content.isRequired();
        content.setWidth(270, Unit.PIXELS);
        content.addValidator(new StringLengthValidator("The content should be between 1 and 25 characters", 1, 25, false));

        startDate.isRequired();
        startDate.setWidth(270, Unit.PIXELS);

        endDate.isRequired();
        endDate.setWidth(270, Unit.PIXELS);

        active.setStyleName("editWindowCheckBox");
    }

    private void configureButtons() {
        saveButton = new Button("Save");
        saveButton.setWidth(125, Unit.PIXELS);
        saveButton.addClickListener(this);
        saveButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancelButton = new Button("Cancel");
        cancelButton.setWidth(125, Unit.PIXELS);
        cancelButton.addClickListener(this);
        cancelButton.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
    }

    private Component configureLayouts() {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        buttonsLayout.setMargin(true);
        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);

        VerticalLayout windowLayout = new VerticalLayout();
        windowLayout.setSpacing(true);
        windowLayout.setMargin(true);

        windowLayout.addComponent(title);
        windowLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

        windowLayout.addComponent(content);
        windowLayout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);

        windowLayout.addComponent(startDate);
        windowLayout.setComponentAlignment(startDate, Alignment.MIDDLE_CENTER);

        windowLayout.addComponent(endDate);
        windowLayout.setComponentAlignment(endDate, Alignment.MIDDLE_CENTER);

        windowLayout.addComponent(active);
        windowLayout.setComponentAlignment(active, Alignment.MIDDLE_CENTER);

        windowLayout.addComponent(buttonsLayout);
        windowLayout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);

        center();
        setModal(true);
        setResizable(false);
        setSizeUndefined();
        setImmediate(true);
        setCaption("Edit Message");

        return windowLayout;
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
