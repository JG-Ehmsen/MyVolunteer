/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Utility;


import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Fjord82
 */
public abstract class ClipBoardUtility
{
    public static void installCopyPasteHandler(TableView<?> table)
    {
        table.setOnKeyPressed(new TableKeyEventHandler());
    }
    
    public static class TableKeyEventHandler implements EventHandler<KeyEvent>
    {

        KeyCodeCombination copyKeyCodeCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        
        public void handle(final KeyEvent keyEvent)
        {
            if(copyKeyCodeCombination.match(keyEvent))
            {
                if(keyEvent.getSource() instanceof TableView)
                {
                    copySelectionToClipboard((TableView<?>) keyEvent.getSource());
                    keyEvent.consume();
                }
            }
        }   
    }
    
    public static void copySelectionToClipboard(TableView<?> table)
    {
        StringBuilder clipboardString = new StringBuilder();
        
        ObservableList<TablePosition> positionList = table.getSelectionModel().getSelectedCells();
        
        int prevRow = -1;
        
        for (TablePosition position : positionList)
        {
            int row = position.getRow();
            int col = position.getColumn();
            
            Object cell = (Object) table.getColumns().get(col).getCellData(row);
            
            // null-check: provide empty string for nulls
            if(cell == null)
            {
                cell = "";
            }
            
            // determine whether we advance in a row (tab) or a column
            // (newline).
            if (prevRow == row) {

                clipboardString.append('\t');

            } else if (prevRow != -1) {

                clipboardString.append('\n');

            }
            // create string from cell
            String text = cell.toString();

            // add new item to clipboard
            clipboardString.append(text);

            // remember previous
            prevRow = row;  
        }
        // create clipboard content
        final ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(clipboardString.toString());

        // set clipboard content
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    
    }
}
