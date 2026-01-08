package com.example.vaadin.views;

import com.vaadin.flow.component.ComponentEffect;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.signals.NumberSignal;

@Route("")
public class SimpleSignalView extends VerticalLayout {

    private static final String BADGE_PRIMARY_SUCCESS = "badge primary success";
    private static final String BADGE_CONTRAST = "badge contrast";
    private static final String THEME = "theme";

    private final static NumberSignal numberSignal = new NumberSignal(0.0);

    public SimpleSignalView() {

        var numberField = new NumberField("Local Number");
        numberField.setStepButtonsVisible(true);
        numberField.addValueChangeListener(e -> numberSignal.value(e.getValue()));
        ComponentEffect.bind(numberField, numberSignal, NumberField::setValue);

        var evenBadge = new Span("even");
        evenBadge.getElement().bindAttribute(THEME,
                () -> numberSignal.value() % 2 == 0 ?
                        BADGE_PRIMARY_SUCCESS : BADGE_CONTRAST);

        var oddBadge = new Span("odd");
        oddBadge.getElement().bindAttribute(THEME,
                () -> numberSignal.value() % 2 == 0 ?
                        BADGE_CONTRAST : BADGE_PRIMARY_SUCCESS);

        add(numberField, new HorizontalLayout(evenBadge, oddBadge));
    }
}

