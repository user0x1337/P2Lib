/*
 * P2tools Copyright (C) 2023 W. Xaver W.Xaver[at]googlemail.com
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


package de.p2tools.p2lib.guitools;

import de.p2tools.p2lib.P2LibConst;
import de.p2tools.p2lib.ProgIconsP2Lib;
import de.p2tools.p2lib.tools.log.PLog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class P2WindowIcon {
    private P2WindowIcon() {
    }

    public static void addWindowP2Icon(Stage stage) {
        try {
            stage.getIcons().add(0, ProgIconsP2Lib.P2_WINDOW_ICON.getImage());
        } catch (Exception ex) {
            PLog.errorLog(945720146, "Kann Window-Icon nicht setzen");
        }
    }

    public static void setWindowIcon(Stage stage, String iconPath) {
        if (iconPath.isEmpty() || !new File(iconPath).exists()) {
            addWindowP2Icon(stage);
            return;
        }

        try {
            Image icon = new Image(new File(iconPath).toURI().toString(),
                    P2LibConst.WINDOW_ICON_WIDTH, P2LibConst.WINDOW_ICON_HEIGHT, true, true);
            stage.getIcons().add(0, icon);
        } catch (Exception ex) {
            PLog.errorLog(204503978, ex);
            addWindowP2Icon(stage);
        }
    }
}
