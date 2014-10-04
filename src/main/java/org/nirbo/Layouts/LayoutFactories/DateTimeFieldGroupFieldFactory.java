package org.nirbo.Layouts.LayoutFactories;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Field;
import com.vaadin.ui.PopupDateField;

import java.util.Date;

public class DateTimeFieldGroupFieldFactory implements FieldGroupFieldFactory {

    private FieldGroupFieldFactory defaultFactory = new DefaultFieldGroupFieldFactory();
    private static final String DATE_FORMAT = "MMM dd, yyyy HH:mm:ss";

    @Override
    public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {

        if (dataType.isAssignableFrom(Date.class)) {
            return (T) createDateTimeField();
        }

        return defaultFactory.createField(dataType, fieldType);
    }

    protected  <T extends Field> T createDateTimeField() {
        PopupDateField dateTimeField = new PopupDateField();

        dateTimeField.setResolution(Resolution.SECOND);
        dateTimeField.setDateFormat(DATE_FORMAT);
        dateTimeField.setImmediate(true);
        dateTimeField.setInvalidAllowed(false);

        return (T) dateTimeField;
    }


}
