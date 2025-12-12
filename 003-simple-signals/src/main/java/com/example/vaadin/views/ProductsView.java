package com.example.vaadin.views;

import com.vaadin.flow.component.ComponentEffect;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.signals.NumberSignal;

@Route("")
public class ProductsView extends VerticalLayout {

    // Signal for VAT Factor for multiple clients
    private static final NumberSignal globalSignal = new NumberSignal(42.0);

    // Signal for UI Scoped multiply Factor
    private final NumberSignal uiScopedSignal = new NumberSignal(0.5);

    public ProductsView() {

        var globalField = createNumberField("Global Factor", globalSignal);
        ComponentEffect.effect(globalField, () -> Notification.show("Global Factor changed to " + globalSignal.value()));

        var uiField = createNumberField("UI Scoped Factor", uiScopedSignal);
        var uiValueField = new Span("");
        ComponentEffect.bind(uiValueField, uiScopedSignal,
                (span, value) -> span.setText(String.valueOf(value)));


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
        ComponentEffect.bind(numberField, signal, NumberField::setValue);

        return numberField;
    }
}
