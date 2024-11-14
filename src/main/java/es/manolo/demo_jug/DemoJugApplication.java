package es.manolo.demo_jug;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("my-theme")
@Push
public class DemoJugApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(DemoJugApplication.class, args);
    }

}
