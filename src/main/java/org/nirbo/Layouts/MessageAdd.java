package org.nirbo.Layouts;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import org.nirbo.Layouts.LayoutFactories.DateTimeFieldGroupFieldFactory;
import org.nirbo.MainUI;
import org.nirbo.Model.SplashMessages;
import org.nirbo.Persistence.CinemallJPAContainer;
import org.nirbo.Utilities.Utilities;

import java.util.*;
import java.util.Calendar;

public class MessageAdd extends Window implements Button.ClickListener {

    private FieldGroup group;
    private Button saveButton;
    private Button cancelButton;
    private CinemallJPAContainer dataSource;
    private SplashMessages newMessage;

    private Field<?> title;
    private Field<?> content;
    private Field<?> publishedDate;
    private Field<?> startDate;
    private Field<?> endDate;
    private Field<?> active;

    public MessageAdd(CinemallJPAContainer dataSource) {
        this.dataSource = dataSource;

        createAndBindFields();
        configureFields();
        configureButtons();

        setContent(configureLayouts());
    }

    private void createAndBindFields() {
        group = new FieldGroup(dataSource.createEntityItem(new SplashMessages()));
        group.isBuffered();
        group.setFieldFactory(new DateTimeFieldGroupFieldFactory());

        title = group.buildAndBind("Title", "title");
        content = group.buildAndBind("Content", "content");
        publishedDate = group.buildAndBind("publishedDate");
        startDate = group.buildAndBind("Start Date", "startDate");
        endDate = group.buildAndBind("End Date", "endDate");
        active = group.buildAndBind("Active", "active");
    }

    private void configureFields() {
        title.isRequired();
        title.setWidth(270, Unit.PIXELS);
        title.addValidator(new StringLengthValidator("The message should be between 1 and 25 characters", 1, 25, false));
        title.getPropertyDataSource().setValue("");
        title.focus();

        content.isRequired();
        content.setWidth(270, Unit.PIXELS);
        content.addValidator(new StringLengthValidator("The content should be between 1 and 25 characters", 1, 25, false));
        content.getPropertyDataSource().setValue("");

        publishedDate.isRequired();
        publishedDate.setWidth(270, Unit.PIXELS);
        publishedDate.setReadOnly(true);
        publishedDate.setEnabled(false);
        publishedDate.getPropertyDataSource().setValue(new Date());

        startDate.isRequired();
        startDate.setWidth(270, Unit.PIXELS);

        endDate.isRequired();
        endDate.setWidth(270, Unit.PIXELS);

        active.setStyleName("editWindowCheckBox");
        active.getPropertyDataSource().setValue(false);
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

        VerticalLayout addMessageForm = new VerticalLayout();
        addMessageForm.setSpacing(true);
        addMessageForm.setMargin(true);

        addMessageForm.addComponent(title);
        addMessageForm.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

        addMessageForm.addComponent(content);
        addMessageForm.setComponentAlignment(content, Alignment.MIDDLE_CENTER);

        addMessageForm.addComponent(publishedDate);
        addMessageForm.setComponentAlignment(publishedDate, Alignment.MIDDLE_CENTER);

        addMessageForm.addComponent(startDate);
        addMessageForm.setComponentAlignment(startDate, Alignment.MIDDLE_CENTER);

        addMessageForm.addComponent(endDate);
        addMessageForm.setComponentAlignment(endDate, Alignment.MIDDLE_CENTER);

        addMessageForm.addComponent(active);
        addMessageForm.setComponentAlignment(active, Alignment.MIDDLE_CENTER);

        addMessageForm.addComponent(buttonsLayout);
        addMessageForm.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);

        center();
        setModal(true);
        setResizable(false);
        setSizeUndefined();
        setImmediate(true);
        setCaption("Add a New Message");

        return addMessageForm;
    }

    private void prepareNewEntity() {
        newMessage = new SplashMessages();

        newMessage.setTitle(title.getValue().toString());
        newMessage.setContent(content.getValue().toString());
        newMessage.setPublishedDate((Date) publishedDate.getValue());
        newMessage.setStartDate((Date) startDate.getValue());
        newMessage.setEndDate((Date) endDate.getValue());
        newMessage.setActive((Boolean) active.getValue());
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
                prepareNewEntity();
                dataSource.addEntity(newMessage);
                MainUI.getCurrent().getPageLayout().getBodyLayout().getMessagesTable().refreshRowCache();
                MainUI.getCurrent().removeWindow(this);
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
                Utilities.notification("Failed to add the message!", "red");
            }
        }
    }


}
