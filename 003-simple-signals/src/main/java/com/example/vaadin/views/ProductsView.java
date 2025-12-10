package com.example.vaadin.views;

import com.example.vaadin.data.Product;
import com.example.vaadin.data.ProductRepository;
import com.vaadin.flow.component.ComponentEffect;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.signals.NumberSignal;
import com.vaadin.signals.SignalFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Route("")
public class ProductsView extends VerticalLayout {

    // Signal for VAT Factor for multiple clients
    private final NumberSignal vatSignal = SignalFactory.IN_MEMORY_SHARED.number("vat", 19.0);

    // Signal for local multiply factor
    private final NumberSignal multiplySignal = new NumberSignal(1.0);

    public ProductsView(ProductRepository repository) {

        var vatField = new NumberField("VAT");
        vatField.setStep(0.5);
        vatField.setStepButtonsVisible(true);
        vatField.setSuffixComponent(new Div("%"));
        vatField.addValueChangeListener(event -> {
            if (event.isFromClient()) //to avoid changes in a read-only transaction
                vatSignal.value(event.getValue());
        });
        ComponentEffect.bind(vatField, vatSignal, NumberField::setValue);

        var multiplyField = new NumberField("Multiply");
        multiplyField.setStep(1.0);
        multiplyField.setStepButtonsVisible(true);
        multiplyField.addValueChangeListener(event -> multiplySignal.value(event.getValue()));
        multiplyField.setValue(multiplySignal.value());

        add(new HorizontalLayout(vatField, multiplyField));

        var grid = new Grid<>(Product.class);
        grid.setColumns("title", "content");
        grid.addColumn("price").setHeader("Net Price");
        grid.addColumn(new ComponentRenderer<>(article -> {
            Span priceSpan = new Span();

            // Effect for showing the current price with VAT
            ComponentEffect.effect(priceSpan, () ->
                    priceSpan.setText(article.getPrice()
                            .multiply(BigDecimal.ONE.add(BigDecimal.valueOf(vatSignal.value() / 100)))
                            .multiply(BigDecimal.valueOf(multiplySignal.value()))
                            .setScale(2, RoundingMode.HALF_UP).toString())
            );

            return priceSpan;
        })).setHeader("Price with VAT");

        grid.setItems(repository.findAll());

        add(grid);
    }
}
