package com.example.vaadin.views;

import com.vaadin.flow.component.ComponentEffect;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.signals.NumberSignal;

@Route("")
public class SimpleSignalView extends VerticalLayout {

    // Signal for Global Value for multiple clients
    private static final NumberSignal globalSignal = new NumberSignal(42.0);

    // Signal for UI Scoped Value
    private final NumberSignal uiScopedSignal = new NumberSignal(0.5);

    public SimpleSignalView() {

        var globalField = createNumberField("Global Value", globalSignal);
        // Re-runs whenever globalSignal changes while globalField is attached
        ComponentEffect.effect(globalField, () -> Notification
                .show("Global value changed to " + globalSignal.value(), 1000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.LUMO_CONTRAST));

        var uiField = createNumberField("UI Scoped Value", uiScopedSignal);
        var uiValueField = new Span("");
        // Binds span text to uiScopedSignal
        ComponentEffect.bind(uiValueField, uiScopedSignal,
                (span, value) -> span.setText(String.valueOf(value)));
        // shows notification on each signal change
        ComponentEffect.effect(uiValueField, () -> Notification
                .show("UI Scoped value changed to " + uiScopedSignal.value(), 1000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.LUMO_PRIMARY));

        VerticalLayout uiLayout = new VerticalLayout(uiField, uiValueField);
        uiLayout.setPadding(false);
        uiLayout.setAlignItems(Alignment.CENTER);

        add(new HorizontalLayout(globalField, uiLayout));
    }

    private NumberField createNumberField(String fieldLabel, NumberSignal signal) {
        var numberField = new NumberField(fieldLabel);
        numberField.setStep(0.1);
        numberField.setStepButtonsVisible(true);
        numberField.addValueChangeListener(event -> signal.value(event.getValue()));
        // bind field value to signal; active only while component is attached
        ComponentEffect.bind(numberField, signal, NumberField::setValue);

        return numberField;
    }
}
