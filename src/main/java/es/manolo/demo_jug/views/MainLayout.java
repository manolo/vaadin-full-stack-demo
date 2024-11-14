package es.manolo.demo_jug.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
  public MainLayout() {
    addToNavbar(new DrawerToggle(), new H3("Demo JUG"));
    addToDrawer(new VerticalLayout(
        new RouterLink("AI Chat", HomeView.class),
        new RouterLink("Contact", ContactsView.class)));

  }
}
