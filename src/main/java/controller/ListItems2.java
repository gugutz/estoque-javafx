package controller;

import db.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import model.TableItem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ListItems2 extends BorderPane implements Initializable {

    @FXML
    private TableView<TableItem> products_table;
    @FXML
    private TableColumn col_id, col_codigo, col_descricao, col_linha, col_peso;

    private ObservableList<TableItem> row = FXCollections.observableArrayList();
    List<TableItem> rowList = new ArrayList<>();
    private static final String SQL_QUERY = "SELECT rowid, codigo, descricao, linha, peso from perfis;";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // instantiating a router object to change panes when needed
        Router router = new Router();

        try {
            Connection connection = DB.connect();

            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(SQL_QUERY);


            while (results.next()) {
                Integer rowid = results.getInt("rowid");
                String codigo = results.getString("codigo");
                String descricao = results.getString("descricao");
                String linha = results.getString("linha");
                Double peso = results.getDouble("peso");
                row.add(new TableItem(rowid, codigo, descricao, linha, peso));
                rowList.add(new TableItem(rowid, codigo, descricao, linha, peso));

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }


        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        col_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        col_linha.setCellValueFactory(new PropertyValueFactory<>("linha"));
        col_peso.setCellValueFactory(new PropertyValueFactory<>("peso"));


    // building the context menu
    //
    final ContextMenu contextMenu = new ContextMenu();

    // EDIT CONTEXT MENU
    MenuItem item0 = new MenuItem("Editar Item");
    item0.setOnAction(e -> {
        try {
            TableItem row = getSelectedItem();
            int itemId = row.getId();
            BorderPane details = new ItemDetails(itemId);
            router.setContentPane(details, "ItemDetails");
        } catch (IOException e1) {
            System.out.println("nao achou tela do itemdetails");
            e1.printStackTrace();
        }
        System.out.println("About");

    });
    MenuItem item1 = new MenuItem("Deletar Item");
                item1.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            System.out.println("About");
        }
    });
    MenuItem item2 = new MenuItem("Ver detalhes");
                item2.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            System.out.println("Preferences");
        }
    });

    // adding the submenu items to the context menu
    contextMenu.getItems().addAll(item0, item1, item2);
    products_table.setContextMenu(contextMenu);

    //testing print of objects added to the list.
    for (int i = 0; i < rowList.size(); i++) {
        System.out.println(rowList.get(i));
    }

    //FINALLY ADDED TO TableView
    products_table.setItems(row);
}

    public TableItem getSelectedItem() {
        TableItem selectedItem = products_table.getSelectionModel().getSelectedItem();
        System.out.println(selectedItem.getId());
        return selectedItem;
    }





}


