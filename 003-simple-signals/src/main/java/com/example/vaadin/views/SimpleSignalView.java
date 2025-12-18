package com.example.vaadin.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

@Route("")
public class SimpleSignalView extends VerticalLayout {

    public static final String BADGE_CONTRAST = "badge contrast";
    public static final String BADGE_PRIMARY_SUCCESS = "badge primary success";

    public SimpleSignalView() {

        /***
         * 1. Vaadin Signals provide a reactive way to manage states of components and views, ensuring that UI updates
         * are targeted and efficient. They replace heavy listeners and are thread-safe as well as lightweight.
         */

        /***
         * 4. By using Signals, you create a single source of truth for your state. You bind the input field to the
         * signal, ensuring that any value changes propagate automatically to the rest of the UI.
         */

//        var numberSignal = new NumberSignal(42.0);

        /***
         * 2. Take this scenario: We have a number field and two badges for 'even' and 'odd'. Depending on the input,
         * the corresponding badge reactively turns green to indicate the current state.
         */
        var numberField = new NumberField("Local Number");
        numberField.setStep(1);
        numberField.setStepButtonsVisible(true);
//        numberField.addValueChangeListener(e -> numberSignal.value(e.getValue()));
//        ComponentEffect.bind(numberField, numberSignal, NumberField::setValue);

        var evenBadge = new Span("even");
        evenBadge.getElement().getThemeList().add(BADGE_CONTRAST);
        /***
         * 5. Signals eliminate the need for complex, cross-component listeners. By letting each component react to the
         * state independently, we decouple our UI and simplify the overall architecture.
         */
//        ComponentEffect.effect(evenBadge, () -> {
//            evenBadge.getElement().getThemeList().clear();
//            evenBadge.getElement().getThemeList().add(numberSignal.value() % 2 == 0 ?
//                    BADGE_PRIMARY_SUCCESS : BADGE_CONTRAST);
//        });

        var unevenBadge = new Span("uneven");
        unevenBadge.getElement().getThemeList().add(BADGE_CONTRAST);
//        ComponentEffect.effect(unevenBadge, () -> {
//            unevenBadge.getElement().getThemeList().clear();
//            unevenBadge.getElement().getThemeList().add(numberSignal.value() % 2 == 0 ?
//                    BADGE_CONTRAST : BADGE_PRIMARY_SUCCESS);
//        });


        /***
         * 3. In a traditional approach you would use a ValueChangeListener to change the style, which creates tight
         * coupling between the components. The input field ends up managing the state of its neighbors, making the
         * code harder to maintain.
         */
        numberField.addValueChangeListener(event -> {
            boolean isEven = event.getValue().intValue() % 2 == 0;

            evenBadge.getElement().getThemeList().clear();
            unevenBadge.getElement().getThemeList().clear();

            evenBadge.getElement().getThemeList().add(isEven ? BADGE_PRIMARY_SUCCESS : BADGE_CONTRAST);
            unevenBadge.getElement().getThemeList().add(isEven ? BADGE_CONTRAST : BADGE_PRIMARY_SUCCESS);
        });

        add(numberField , new HorizontalLayout(evenBadge, unevenBadge));
    }
}
