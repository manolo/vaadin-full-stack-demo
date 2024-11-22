package es.manolo.demo_jug.views;

import com.vaadin.flow.component.button.ButtonVariant;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import es.manolo.demo_jug.data.Contact;
import es.manolo.demo_jug.data.ContactRepository;
import org.springframework.data.domain.PageRequest;

@Route(layout = MainLayout.class)
public class ContactsView extends VerticalLayout {

    private Grid<Contact> grid = new Grid<>(Contact.class);
    private final TextField name = new TextField("Name");
    private final TextField surname = new TextField("Surname");
    private final TextField email = new TextField("Email");
    private final TextField phone = new TextField("Phone");
    private final DatePicker birthDay = new DatePicker("Birth Day");
    private final Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    public ContactsView(ContactRepository repo) {
        grid.setColumns("name", "surname", "email", "phone", "birthDay");

        grid.setItems(query -> repo.findAll(
                        PageRequest.of(
                                query.getPage(),
                                query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());

        FormLayout formLayout = new FormLayout();

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button deleteButton = new Button("Delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button addButton = new Button("Add");
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, deleteButton, cancelButton);
        formLayout.add(name, surname, email, phone, birthDay, buttonLayout);

        formLayout.setVisible(false);
        add(grid, addButton, formLayout);
        this.setSizeFull();


        binder.bindInstanceFields(this);
        grid.asSingleSelect().addValueChangeListener(event -> {
            binder.setBean(event.getValue());
        });
        binder.addStatusChangeListener(event -> {
            formLayout.setVisible(binder.getBean() != null);
            addButton.setVisible(binder.getBean() == null);
        });
        addButton.addClickListener(event -> {
            binder.setBean(new Contact());
        });
        cancelButton.addClickListener(event -> {
            binder.setBean(null);
        });
        saveButton.addClickListener(event -> {
            if (binder.validate().isOk()) {
                repo.save(binder.getBean());
                grid.getDataProvider().refreshAll();
                cancelButton.click();
            }
        });
        deleteButton.addClickListener(event -> {
            repo.delete(binder.getBean());
            binder.setBean(null);
            grid.getDataProvider().refreshAll();
        });


    }
}
