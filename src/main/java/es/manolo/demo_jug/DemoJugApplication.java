package es.manolo.demo_jug;

import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@Theme(value = "my-theme", variant = "dark")
@Push
@PWA(name = "Demo JUG", shortName = "Demo JUG", offlineResources = {"images/logo.png"})
public class DemoJugApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(DemoJugApplication.class, args);
    }
}
