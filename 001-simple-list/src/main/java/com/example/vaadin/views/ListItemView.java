package com.example.vaadin.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import java.sql.Array;
import java.util.ArrayList;

@PageTitle("List Item View")
@Route("")
public class ListItemView extends VerticalLayout {

    public record Item(String name) { }

    public ListItemView() {
        var itemFied = new TextField("", "insert item name ...");
        var addButton = new Button("Add");
        var itemGrid = new Grid<>(Item.class);

        add(new HorizontalLayout(itemFied, addButton), itemGrid);

        var itemList = new ArrayList<Item>();

        addButton.addClickListener(e -> {
            itemList.add(new Item(itemFied.getValue()));
            itemGrid.setItems(itemList);
        });
    }
}
