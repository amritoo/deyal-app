package app.deyal.deyal_app.managers;

import app.deyal.deyal_app.data.Constants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlertManager {

    /**
     * Shows a material style dialog with blurred background and given number of buttons. If no button is given, shows default 'Okay' button.
     *
     * @param root            - StackPane
     * @param nodeToBeBlurred - Node
     * @param buttons         - button list
     * @param header          - dialog header
     * @param body            - dialog body
     */
    public static void showMaterialDialog(StackPane root, Node nodeToBeBlurred, List<JFXButton> buttons, String header, String body) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        if (buttons == null) {
            buttons = Collections.singletonList(new JFXButton("Okay")); // default button
        }
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.getStyleClass().add("root");
        JFXDialog dialog = new JFXDialog(root, dialogLayout, JFXDialog.DialogTransition.TOP);

        buttons.forEach(jfxButton -> {
            jfxButton.getStyleClass().add("dialog-button"); // adding style class
            jfxButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> dialog.close());
        });

        Label title = new Label(header);
        title.getStyleClass().add("label-title");
        Label content = new Label(body);
        content.getStyleClass().add("label-content");

        dialogLayout.setHeading(title);
        dialogLayout.setBody(content);
        dialogLayout.setActions(buttons);

        dialog.setOnDialogClosed(event -> nodeToBeBlurred.setEffect(null));
        nodeToBeBlurred.setEffect(blur);

        dialog.show();
    }

    public static void showTrayMessage(String title, String message) {
        try {
            SystemTray tray = SystemTray.getSystemTray();
            BufferedImage image = ImageIO.read(Constants.APP_ICON);
            TrayIcon trayIcon = new TrayIcon(image, Constants.APP_NAME);
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip(Constants.APP_NAME);
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            tray.remove(trayIcon);
        } catch (Exception ex) {
            Logger.getLogger(Constants.LOG_NAME).log(Level.SEVERE, "AlertManager:showTrayMessage", ex);
        }
    }

}
