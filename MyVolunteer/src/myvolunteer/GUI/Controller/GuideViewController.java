/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myvolunteer.GUI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import myvolunteer.GUI.Model.MainViewModel;
import myvolunteer.GUI.Model.ViewChangerModel;

/**
 * FXML Controller class
 *
 * @author Fjord82
 */
public class GuideViewController implements Initializable
{

    MainViewModel mainViewModel = MainViewModel.getInstance();
    ViewChangerModel vcm = new ViewChangerModel();

    @FXML
    private Button btnBack;

    private MediaPlayer mpGuide;
    private Media meGuide;
    @FXML
    private Label step1lbl;
    @FXML
    private Label step2lbl;
    @FXML
    private Label step3lbl;
    @FXML
    private Label lblToDo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        changeLanguage();
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
    private void handleGoBack(ActionEvent event) throws IOException
    {
        vcm.showLaugSelectionView((Stage) btnBack.getScene().getWindow());
    }

    private void changeLanguage()
    {
        ResourceBundle rb = ResourceBundle.getBundle(mainViewModel.getLastSelectedBundle(), mainViewModel.getLastSelectedLocale());
        step1lbl.setText(rb.getString("GuideView.step1lbl.text"));
        step2lbl.setText(rb.getString("GuideView.step2lbl.text"));
        step3lbl.setText(rb.getString("GuideView.step3lbl.text"));
        btnBack.setText(rb.getString("GuideView.btnBack.text"));
        lblToDo.setText(rb.getString("GuideView.lblToDo.text"));

    }

}
