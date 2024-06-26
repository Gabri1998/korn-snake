package corn_snake.front_end.controllers;

import corn_snake.util.FX;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static corn_snake.util.IO.*;

public class CreditsController implements Initializable {

    @FXML
    private ImageView background, board, home, logo;

    @FXML
    private Label title, names;

    @FXML
    private AnchorPane window;

    static final Image
            BACKGROUND = FX.BACKGROUND,
            BOARD = FX.BOARD,
            HOME = FX.HOME,
            LOGO = FX.LOGO;

    private Timeline playCredits; // Declared here, so it can be stopped in initialize()

    /**
     * Called when the home button is clicked.
     * Returns to the title screen
     */
    @FXML
    public void onHomeClick() throws IOException {
        Stage stage = (Stage) window.getScene().getWindow();
        playCredits.stop();
        FX.loadScene(stage, "menu/menu-view.fxml", MenuController.class, "menu/MenuStyle.css");
    }

    /**
     * This method is implicitly called by JavaFX.
     * Initializes the credits screen and plays the credits.
     * Two {@link Timeline}s make components appear in quick succession and plays the credits with animations
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final double FADE_DURATION = 4.5;
        final double CREDITS_DELAY = 1.1;
        background.setImage(BACKGROUND);

        Timeline load = new Timeline(
                new KeyFrame(Duration.seconds(0.2), (event) -> board.setImage(BOARD)),
                new KeyFrame(Duration.seconds(0.4), (event) -> home.setImage(HOME))
        );
        load.setDelay(Duration.seconds(0.4));

        playCredits = new Timeline(
                new KeyFrame(Duration.seconds(CREDITS_DELAY), (event) -> {
                    fadeInTitleAndNames(PRODUCER_TITLE, PRODUCER_NAME);
                }),
                new KeyFrame(Duration.seconds(CREDITS_DELAY + FADE_DURATION), (event) -> {
                    fadeInTitleAndNames(DIRECTOR_TITLE, DIRECTOR_NAME);
                }),
                new KeyFrame(Duration.seconds(CREDITS_DELAY + 2 * FADE_DURATION), (event) -> {
                    fadeInTitleAndNames(LOGIC_TITLE, LOGIC_NAMES);
                }),
                new KeyFrame(Duration.seconds(CREDITS_DELAY + 3 * FADE_DURATION), (event) -> {
                    fadeInTitleAndNames(UI_TITLE, UI_NAMES);
                }),
                new KeyFrame(Duration.seconds(CREDITS_DELAY + 4 * FADE_DURATION), (event) -> {
                    fadeInTitleAndNames(VISUALS_TITLE, VISUALS_NAME);
                }),
                new KeyFrame(Duration.seconds(CREDITS_DELAY + 5 * FADE_DURATION), (event) -> {
                    fadeInTitleAndNames(SUPERVISOR_TITLE, SUPERVISOR_NAME);
                }),
                new KeyFrame(Duration.seconds(CREDITS_DELAY + 6 * FADE_DURATION), (event) -> {
                    fadeInTitleAndNames(EXAMINER_TITLE, EXAMINER_NAME);
                }),
                new KeyFrame(Duration.seconds(CREDITS_DELAY + 7 * FADE_DURATION), (event) -> {
                    final int INCREMENT = -103;

                    logo.setOpacity(0);
                    logo.setImage(LOGO);

                    TranslateTransition moveLogo = new TranslateTransition();
                    FadeTransition fadeLogo = new FadeTransition();

                    moveLogo.setNode(logo);
                    fadeLogo.setNode(logo);

                    moveLogo.setDuration(Duration.seconds(0.5));
                    fadeLogo.setDuration(Duration.seconds(0.5));

                    moveLogo.setByY(INCREMENT);
                    fadeLogo.setFromValue(0);
                    fadeLogo.setToValue(1);

                    moveLogo.play();
                    fadeLogo.play();
                })
        );

        load.play();
        playCredits.play();
    }

    /**
     * Animates a section of the credits. A fade in animation takes 0.5 s.
     * A full fade in and fade out takes four seconds in total. fadeOut defaults to true
     *
     * @param title the title of the developers to be listed
     * @param names the names of the developers being listed
     */
    private void fadeInTitleAndNames(String title, String names) {
        fadeInTitleAndNames(title, names, true);
    }

    /**
     * Animates a section of the credits. A fade in animation takes 0.5 s.
     * A full fade in and fade out takes four seconds in total. fadeOut defaults to true
     *
     * @param title the title of the developers to be listed
     * @param names the names of the developers being listed
     * @param fadeOut set to true for the labels to fade out
     */
    private void fadeInTitleAndNames(String title, String names, boolean fadeOut) {
        final int INCREMENT = -52;
        final int FADE_OUT_BY_SECONDS = 3;

        this.title.setOpacity(0);
        this.names.setOpacity(0);
        this.title.setText(title);
        this.names.setText(names);

        // Fade in
        TranslateTransition moveInTitle = new TranslateTransition();
        TranslateTransition moveInNames = new TranslateTransition();
        FadeTransition fadeInTitle = new FadeTransition();
        FadeTransition fadeInNames = new FadeTransition();

        moveInTitle.setNode(this.title);
        moveInNames.setNode(this.names);
        fadeInTitle.setNode(this.title);
        fadeInNames.setNode(this.names);

        moveInTitle.setDuration(Duration.seconds(0.5));
        moveInNames.setDuration(Duration.seconds(0.5));
        fadeInTitle.setDuration(Duration.seconds(0.5));
        fadeInNames.setDuration(Duration.seconds(0.5));

        moveInTitle.setFromY(0);
        moveInNames.setFromY(0);
        moveInTitle.setByY(INCREMENT);
        moveInNames.setByY(INCREMENT);
        fadeInTitle.setFromValue(0);
        fadeInTitle.setToValue(1);
        fadeInNames.setFromValue(0);
        fadeInNames.setToValue(1);

        // Play animations
        moveInTitle.play();
        moveInNames.play();
        fadeInTitle.play();
        fadeInNames.play();

        // Fade out
        if (fadeOut) {
            PauseTransition fadeOutAnimation = new PauseTransition();
            fadeOutAnimation.setDelay(Duration.seconds(FADE_OUT_BY_SECONDS));
            fadeOutAnimation.setOnFinished(
                    (event) -> fadeOutTitleAndNames()
            );
            fadeOutAnimation.play();
        }
    }

    /**
     * Animates a section of the credits by fading Nodes out.
     * Fade out animation takes 0.5 seconds to complete
     */
    private void fadeOutTitleAndNames() {
        final int INCREMENT = -52;

        TranslateTransition moveOutTitle = new TranslateTransition();
        TranslateTransition moveOutNames = new TranslateTransition();
        FadeTransition fadeOutTitle = new FadeTransition();
        FadeTransition fadeOutNames = new FadeTransition();

        moveOutTitle.setNode(this.title);
        moveOutNames.setNode(this.names);
        fadeOutTitle.setNode(this.title);
        fadeOutNames.setNode(this.names);

        moveOutTitle.setDuration(Duration.seconds(0.5));
        moveOutNames.setDuration(Duration.seconds(0.5));
        fadeOutTitle.setDuration(Duration.seconds(0.5));
        fadeOutNames.setDuration(Duration.seconds(0.5));

        moveOutTitle.setByY(INCREMENT);
        moveOutNames.setByY(INCREMENT);
        fadeOutTitle.setFromValue(1);
        fadeOutTitle.setToValue(0);
        fadeOutNames.setFromValue(1);
        fadeOutNames.setToValue(0);

        moveOutTitle.play();
        moveOutNames.play();
        fadeOutTitle.play();
        fadeOutNames.play();
    }

    /**
     * Called whenever the cursor hovers over a button.
     * The button gets enlarged by 10%
     * @param event the {@link MouseEvent} used to fetch the button
     */
    @FXML
    public void onHover(MouseEvent event) {
        Node node = (Node) event.getTarget();
        if (node.getId().equals("home")) {
            FX.scale(node, 1.1);
        }
    }

    /**
     * Called whenever the cursor stops hovering over a button.
     * The button reverts to its original size
     * @param event the {@link MouseEvent} used to fetch the button
     */
    @FXML
    public void onUnHover(MouseEvent event) {
        Node node = (Node) event.getTarget();
        if (node.getId().equals("home")) {
            FX.scale(node, 1);
        }
    }
}
