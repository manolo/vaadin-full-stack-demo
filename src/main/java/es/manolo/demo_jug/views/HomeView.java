package es.manolo.demo_jug.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.ai.chat.client.ChatClient;
import org.vaadin.voiceengine.VoiceEngine;

@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView(ChatClient.Builder builder) {
        VoiceEngine voiceEngine = new VoiceEngine().setButtons(VoiceEngine.Buttons.MICROPHONE, VoiceEngine.Buttons.PLAY, VoiceEngine.Buttons.CANCEL, VoiceEngine.Buttons.LANG);
        UI ui = UI.getCurrent();
        ChatClient chatClient = builder.build();

        TextField question = new TextField();
        TextArea answer = new TextArea();
        Button ask = new Button("Ask", event -> {
            answer.clear();
            chatClient.prompt().user(question.getValue()).stream().content().subscribe(token -> {
                ui.access(() -> {
                    answer.setValue(answer.getValue() + token);
                });
            }, null, () -> {
                ui.access(() -> {
                    voiceEngine.play(answer.getValue());
                });

            });
        });
        ask.addClickShortcut(Key.ENTER);
        HorizontalLayout horizontalLayout = new HorizontalLayout(question, ask, voiceEngine);
        add(horizontalLayout, answer);
        voiceEngine.addEndListener(event -> {
            question.setValue(voiceEngine.getRecorded());
            ask.click();
        });

        this.setSizeFull();
        horizontalLayout.setWidthFull();
        question.setWidthFull();
        answer.setSizeFull();

    }
}
