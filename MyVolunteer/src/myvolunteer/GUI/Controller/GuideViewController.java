/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import myvolunteer.GUI.Model.MainViewModel;

/**
 * FXML Controller class
 *
 * @author Fjord82
 */
public class GuideViewController implements Initializable
{
    MainViewModel mainViewModel = MainViewModel.getInstance();

    @FXML
    private Button btnBack;
    @FXML
    private MediaView mediaGuide;
    
    private MediaPlayer mpGuide;
    private Media meGuide;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
//        String path = new File("/GUI/Utility/GuideMovie2.mp4").getAbsolutePath();
//        meGuide = new Media(new File(path).toURI().toString());
//        mpGuide = new MediaPlayer(meGuide);
//        mediaGuide.setMediaPlayer(mpGuide);
//        mpGuide.setAutoPlay(true);
//        
//        DoubleProperty width = mediaGuide.fitWidthProperty();
//        DoubleProperty height = mediaGuide.fitHeightProperty();
//        
//        width.bind(Bindings.selectDouble(mediaGuide.sceneProperty(), "width"));
//        height.bind(Bindings.selectDouble(mediaGuide.sceneProperty(), "height"));
    }    

    @FXML
    private void handleBack(ActionEvent event) throws IOException
    {
        mainViewModel.changeView("Laug", "GUI/View/LaugViewSpecial.fxml");

        // Closes the primary stage
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
    
}
