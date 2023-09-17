package com.example.kolokwium_ii_2022_2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    @FXML
    private TextField filterField;

    @FXML
    private ListView<String> wordList;

    @FXML
    private Label wordCountLabel;

    private List<String> allWords = new ArrayList<>();
    private int wordCount = 0;
    private ObservableList<String> displayedWords = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        filterField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                onFilterFieldTextChanged(newValue);
            }
        });
    }

    private void onFilterFieldTextChanged(String newValue) {
        displayedWords.clear();
        if (newValue.isEmpty()) {
            displayedWords.addAll(allWords);
        } else {
            for (String entry : allWords) {
                String[] parts = entry.split(" ");
                if (parts.length > 1 && parts[1].startsWith(newValue)) {
                    displayedWords.add(entry);
                }
            }
        }

        wordCountLabel.setText(String.valueOf(displayedWords.size()));
        wordList.setItems(displayedWords);
        sortWordList();
    }


    @FXML
    public void addEntryToList(String line) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String entry = simpleDateFormat.format(new Date()) + " " + line;
        allWords.add(entry);
         if(filterField.getText().isEmpty() || line.startsWith(filterField.getText())) {
             displayedWords.add(entry);
             wordCount++;
             wordCountLabel.setText(String.valueOf(wordCount));
             wordList.setItems(displayedWords);
             sortWordList();
         }
    }

    private void sortWordList() {
        Comparator<String> wordComparator = new Comparator<String>() {
            @Override
            public int compare(String word1, String word2) {
                String[] parts1 = word1.split(" ");
                String[] parts2 = word2.split(" ");

                return parts1[1].compareTo(parts2[1]);
            }
        };

        Collections.sort(wordList.getItems(), wordComparator);
    }

}
