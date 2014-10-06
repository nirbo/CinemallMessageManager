package org.nirbo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.nirbo.Layouts.PageLayout;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MainUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class, widgetset = "org.nirbo.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    private PageLayout pageLayout;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Cinemall Message Manager");
        initLayout();
    }

    private void initLayout() {
        pageLayout = new PageLayout();
        setContent(pageLayout);
    }

    public PageLayout getPageLayout() {
        return pageLayout;
    }

    public static MainUI getCurrent() {
        return (MainUI) UI.getCurrent();
    }
}
