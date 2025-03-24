/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.dht.fxenglishapp;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.CategoryServices;
import com.dht.services.QuestionServices;
import com.dht.services.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class FXQuestionsController implements Initializable {
    @FXML ComboBox<Category> categories;
    @FXML TableView<Question> questions;
    @FXML TextField txtSearch;
    @FXML RadioButton rdoA;
    @FXML RadioButton rdoB;
    @FXML RadioButton rdoC;
    @FXML RadioButton rdoD;
    @FXML TextField txtA;
    @FXML TextField txtB;
    @FXML TextField txtC;
    @FXML TextField txtD;
    @FXML TextArea txtContent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryServices s = new CategoryServices();
        try {
            List<Category> cates = s.getCates();
            this.categories.setItems(FXCollections.observableList(cates));
        } catch (SQLException ex) {
            Logger.getLogger(FXQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadColumns();
        loadData(null);
        
        this.txtSearch.textProperty().addListener(e -> {
            loadData(this.txtSearch.getText());
        });
    }    
    
    public void loadData(String kw) {
        QuestionServices s = new QuestionServices();
        try {
            List<Question> q = s.getQuestions(0, kw);
            questions.setItems(FXCollections.observableList(q));
        } catch (SQLException ex) {
            Logger.getLogger(FXQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadColumns() {
        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setPrefWidth(300);
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        
        TableColumn colCate = new TableColumn("Danh mục câu hỏi");
        colCate.setPrefWidth(150);
        colCate.setCellValueFactory(new PropertyValueFactory("categoryId"));
        
        TableColumn colAction = new TableColumn();
        colAction.setCellFactory((e -> {
            Button btn = new Button("Xóa");
            btn.setOnAction((evt) -> {
                try {
                    String id = ((Question)((TableRow)((Button)evt.getSource()).getParent().getParent()).getItem()).getId();
                    QuestionServices s = new QuestionServices();
                    s.deleteQuestion(id);
                    Utils.getAlert("Delete successful").show();
                    loadData("");
                } catch (SQLException ex) {
                    Logger.getLogger(FXQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        }));
        
        this.questions.getColumns().addAll(colContent, colCate, colAction);
    }
    
    public void addHandler(ActionEvent e) {
        Question q = new Question(UUID.randomUUID().toString(), this.txtContent.getText(), 
                this.categories.getSelectionModel().getSelectedItem().getId());
        
        Choice c1 = new Choice(UUID.randomUUID().toString(), txtA.getText(), rdoA.isSelected(), q.getId());
        Choice c2 = new Choice(UUID.randomUUID().toString(), txtB.getText(), rdoB.isSelected(), q.getId());
        Choice c3 = new Choice(UUID.randomUUID().toString(), txtC.getText(), rdoC.isSelected(), q.getId());
        Choice c4 = new Choice(UUID.randomUUID().toString(), txtD.getText(), rdoD.isSelected(), q.getId());
        List<Choice> choices = Arrays.asList(c1, c2, c3, c4);
        
        QuestionServices s = new QuestionServices();
        try {
            s.addQuestion(q, choices);
            
            Utils.getAlert("Add successful!").show();
            loadData("");
        } catch (SQLException ex) {
            Logger.getLogger(FXQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
