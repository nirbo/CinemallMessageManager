package org.nirbo.Layouts;

import com.vaadin.data.fieldgroup.DefaultFieldGroupFieldFactory;
import com.vaadin.data.fieldgroup.FieldGroupFieldFactory;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;

import java.util.Date;

public class DateTimeFieldGroupFieldFactory implements FieldGroupFieldFactory {

    private FieldGroupFieldFactory defaultFactory = new DefaultFieldGroupFieldFactory();

    @Override
    public <T extends Field> T createField(Class<?> dataType, Class<T> fieldType) {

        if (Date.class.isAssignableFrom(dataType)) {
            return (T) createDateTimeField();
        }

        return defaultFactory.createField(dataType, fieldType);
    }

    protected  <T extends Field> T createDateTimeField() {
        DateField dateTimeField = new DateField();

        dateTimeField.setResolution(Resolution.MINUTE);
        dateTimeField.setDateFormat("dd-mm-yyyy hh:MM");
        dateTimeField.setBuffered(true);
        dateTimeField.setImmediate(true);

        return (T) dateTimeField;
    }


}
