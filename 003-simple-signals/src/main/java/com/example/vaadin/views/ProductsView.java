package com.example.vaadin.views;

import com.example.vaadin.data.Product;
import com.example.vaadin.data.ProductRepository;
import com.vaadin.flow.component.ComponentEffect;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.signals.NumberSignal;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Route("")
public class ProductsView extends VerticalLayout {

    // Signal for VAT Factor for multiple clients
    private static final NumberSignal globalSignal = new NumberSignal(42.0);

    // Signal for UI Scoped multiply Factor
    private final NumberSignal uiScopedSignal = new NumberSignal(0.5);

    // Signal for Local multiply Factor
    private final Map<String, NumberSignal> localSignalMap = new ConcurrentHashMap<>();

    public ProductsView(ProductRepository repository) {

        var vatField = createNumberField("Global Factor", globalSignal);
        var multiplyField = createNumberField("UI Scoped Factor", uiScopedSignal);

        add(new HorizontalLayout(vatField, multiplyField));

        var grid = new Grid<>(Product.class);
        grid.setColumns("title", "content", "price");
        grid.addColumn(new ComponentRenderer<>(product -> {
            var localSignal = localSignalMap.computeIfAbsent("localFactor/" + product.getId(), k -> new NumberSignal(1.2));

            var localFactorField = createNumberField("", localSignal);
            localFactorField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            localFactorField.addClassName("local-factor-field");
            return localFactorField;
        })).setHeader("Local Factor").setAutoWidth(true);

        grid.addColumn(new ComponentRenderer<>(product -> {
            var localSignal = localSignalMap.computeIfAbsent("localFactor/" + product.getId(), k -> new NumberSignal(1.2));

            var priceSpan = new Span();

            ComponentEffect.effect(priceSpan, () ->
                    priceSpan.setText(
                            product.getPrice()
                                    .multiply(BigDecimal.valueOf(globalSignal.value()))
                                    .multiply(BigDecimal.valueOf(uiScopedSignal.value()))
                                    .multiply(BigDecimal.valueOf(localSignal.value()))
                                    .setScale(2, RoundingMode.HALF_UP)
                                    .toString()));

            return priceSpan;
        })).setHeader("Calculated Price").setAutoWidth(true);

        grid.setItems(repository.findAll());

        add(grid);
    }

    private static @NonNull NumberField createNumberField(String Global_Factor, NumberSignal signal) {
        var numberField = new NumberField(Global_Factor);
        //<theme-editor-local-classname>
        numberField.setStep(0.1);
        numberField.setStepButtonsVisible(true);
        numberField.addValueChangeListener(event -> {
            if (event.isFromClient()) //to avoid changes in a read-only transaction
                signal.value(event.getValue());
        });
        ComponentEffect.bind(numberField, signal, NumberField::setValue);

        return numberField;
    }
}
