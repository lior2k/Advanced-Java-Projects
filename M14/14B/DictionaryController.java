
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DictionaryController {
    private MyDictionary dictionary = new MyDictionary();

    @FXML
    private TextField term_to_add;
    @FXML
    private TextField translation_to_add;
    @FXML
    public void onAddButtonClicked() {
        if (term_to_add.getText() != null && translation_to_add.getText() != null) {
            dictionary.add(term_to_add.getText(), translation_to_add.getText());
            showDict();
            term_to_add.setText(null);
            translation_to_add.setText(null);
        }
    }

    @FXML
    private TextField term_to_update;
    @FXML
    private TextField translation_to_update;
    @FXML
    public void onUpdateButtonClicked() {
        if (term_to_update.getText() != null && translation_to_add.getText() != null) {
            dictionary.update(term_to_update.getText(), translation_to_update.getText());
            showDict();
            term_to_update.setText(null);
            translation_to_update.setText(null);
        }
    }

    @FXML
    private TextField term_to_delete;
    @FXML
    public void onDeleteButtonClicked() {
        if (term_to_delete.getText() != null) {
            dictionary.delete(term_to_delete.getText());
            showDict();
            term_to_delete.setText(null);
        }
    }

    @FXML
    private TextField term_to_search;
    @FXML
    private TextArea search_item;
    @FXML
    public void onSearchButtonClicked() {
        if (term_to_search.getText() != null) {
            if (dictionary.getTerm(term_to_search.getText()) == null) {
                search_item.setText("Term not found");
            } else {
                search_item.setText(dictionary.getTerm(term_to_search.getText()));
            }
        }
    }

    // while typing or deleting search characters,
    // pop a list of possible relevant terms containing the search value
    @FXML
    private void onSearchCharEnteredOrDeleted() {
        if (term_to_search.getText().isEmpty()) {
            search_item.setText(null);
            return;
        }
        StringBuilder result = new StringBuilder("Relevant terms:\n");
        String search = term_to_search.getText();
        for (String key : dictionary.getMap().keySet()) {
            if (key.contains(search)) {
                result.append(key).append("\n");
            }
        }
        search_item.setText(result.toString());
    }

    @FXML
    private TextArea dictionary_items;

    private void showDict() {
        dictionary_items.setText(dictionary.toShow());
    }

    @FXML
    private TextField save_as;
    @FXML
    private void onSaveButtonClicked() {
        if (save_as.getText() != null) {
            dictionary.save(save_as.getText());
        }
    }

    @FXML
    private TextField load;
    @FXML
    private void onLoadButtonClicked() {
        if (load.getText() != null) {
            if (dictionary.load(load.getText())) {
                showDict();
            } else {
                dictionary_items.setText("Load failed! make sure file name is correct");
            }
        }
    }

}
