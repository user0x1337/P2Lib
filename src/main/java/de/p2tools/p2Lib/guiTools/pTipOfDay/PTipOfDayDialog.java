/*
 * P2tools Copyright (C) 2021 W. Xaver W.Xaver[at]googlemail.com
 * https://www.p2tools.de/
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */


package de.p2tools.p2Lib.guiTools.pTipOfDay;

import de.p2tools.p2Lib.dialogs.dialog.PDialogExtra;
import de.p2tools.p2Lib.guiTools.PButton;
import de.p2tools.p2Lib.guiTools.PHyperlink;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class PTipOfDayDialog extends PDialogExtra {

    public static final Image ICON_BUTTON_NEXT = new Image(PTipOfDayDialog.class.getResourceAsStream("button-next.png"));
    public static final Image ICON_BUTTON_PREV = new Image(PTipOfDayDialog.class.getResourceAsStream("button-prev.png"));

    private final List<PTipOfDay> pTipList;
    private ImageView iv = new ImageView();
    private Label lblText = new Label();
    private HBox hBoxHyper = new HBox();
    private StringProperty shownProp;
    private BooleanProperty showTip;
    private int imageSize = 0;
    private Button btnOk, btnPrev, btnNext;
    private CheckBox chkShow = new CheckBox("Tips anzeigen");
    private int actTipOfDay = 0;

    public PTipOfDayDialog(Stage stage, List<PTipOfDay> pTipList, StringProperty shownProp, BooleanProperty showTip) {
        super(stage, null, "Tip des Tages", true, true, DECO.NONE);
        this.pTipList = pTipList;
        this.shownProp = shownProp;
        this.showTip = showTip;
        init(true);
    }

    public PTipOfDayDialog(Stage stage, List<PTipOfDay> pTipList, StringProperty shownProp, BooleanProperty showTip, int imageSize) {
        super(stage, null, "Tip des Tages", true, true, DECO.NONE);
        this.pTipList = pTipList;
        this.shownProp = shownProp;
        this.showTip = showTip;
        this.imageSize = imageSize;
        init(true);
    }

    @Override
    public void make() {
        PTipOfDayFactory.setToolTipsFromShownString(pTipList, shownProp.getValueSafe());
        initTop();
        initButton();
    }

    private void initTop() {
        iv.setSmooth(true);
        setFirstTip();

        VBox vBoxL = new VBox(0);
        VBox vBoxR = new VBox(0);
        vBoxL.getChildren().add(iv);
        vBoxR.getChildren().add(lblText);

        hBoxHyper.setPadding(new Insets(0));
        vBoxR.getChildren().add(hBoxHyper);

        HBox hBox = new HBox(20);
        HBox.setHgrow(vBoxR, Priority.ALWAYS);
        hBox.getChildren().addAll(vBoxL, vBoxR);

        VBox.setVgrow(hBox, Priority.ALWAYS);
        this.getvBoxCont().getChildren().add(hBox);
    }

    private void initButton() {
        chkShow.selectedProperty().bindBidirectional(showTip);

        btnOk = new Button("_Ok");
        btnOk.setOnAction(a -> {
            super.close();
        });

        btnNext = PButton.getButton(new ImageView(ICON_BUTTON_NEXT), "nächste Seite");
        btnNext.setOnAction(event -> {
            selectActToolTip(true);
        });
        btnPrev = PButton.getButton(new ImageView(ICON_BUTTON_PREV), "vorherige Seite");
        btnPrev.setOnAction(event -> {
            selectActToolTip(false);
        });

        btnOk.getStyleClass().add("btnStartDialog");
        btnNext.getStyleClass().add("btnStartDialog");
        btnPrev.getStyleClass().add("btnStartDialog");

        addOkButton(btnOk);
        ButtonBar.setButtonData(btnPrev, ButtonBar.ButtonData.BACK_PREVIOUS);
        ButtonBar.setButtonData(btnNext, ButtonBar.ButtonData.NEXT_FORWARD);
        addAnyButton(btnNext);
        addAnyButton(btnPrev);
        getButtonBar().setButtonOrder("BX+CO");

        getHBoxOverButtons().getChildren().add(chkShow);
    }

    private void selectActToolTip(boolean next) {
        if (next) {
            //next
            if (actTipOfDay < pTipList.size() - 1) {
                ++actTipOfDay;
            } else {
                actTipOfDay = 0;
            }

        } else {
            //!next
            if ((actTipOfDay > 0)) {
                --actTipOfDay;
            } else {
                actTipOfDay = pTipList.size() - 1;
            }
        }
        setTipOfDay();
    }

    private void setTipOfDay() {
        pTipList.get(actTipOfDay).setWasShown(true);
        Image im;
        if (imageSize > 0) {
            im = new Image(pTipList.get(actTipOfDay).getImage(), imageSize, imageSize, true, true);
        } else {
            im = new Image(pTipList.get(actTipOfDay).getImage(), 400, 400, true, true);
        }
        iv.setImage(im);
        lblText.setText(pTipList.get(actTipOfDay).getText());

        hBoxHyper.getChildren().clear();
        if (pTipList.get(actTipOfDay).getHyperlinkWeb() != null) {
            PHyperlink hyperlinkWeb;
            if (pTipList.get(actTipOfDay).openUrlWithProgProperty() != null) {
                hyperlinkWeb = new PHyperlink(pTipList.get(actTipOfDay).getHyperlinkWeb(),
                        pTipList.get(actTipOfDay).openUrlWithProgProperty());
            } else {
                hyperlinkWeb = new PHyperlink(pTipList.get(actTipOfDay).getHyperlinkWeb());
            }

            hBoxHyper.getChildren().add(hyperlinkWeb);
        }

        super.getStage().setTitle("Tip des Tages: " + (actTipOfDay + 1));
        shownProp.setValue(PTipOfDayFactory.getToolTipShownString(pTipList));
    }

    private void setFirstTip() {
        //ersten noch nicht angezeigten suchen, wenn alle schon gesehen, wieder am Anfang beginnen
        for (int i = 0; i < pTipList.size(); ++i) {
            if (!pTipList.get(i).isWasShown()) {
                actTipOfDay = i;
                break;
            }
        }
        setTipOfDay();
    }
}
