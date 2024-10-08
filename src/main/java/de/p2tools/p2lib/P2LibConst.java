/*
 * P2Tools Copyright (C) 2023 W. Xaver W.Xaver[at]googlemail.com
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

package de.p2tools.p2lib;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class P2LibConst {
    public static final int NUMBER_NOT_STARTED = Integer.MAX_VALUE;
    public static final int NUMBER_NULL = 0;
    public static final int NUMBER_ALL_OR_MIN = 0;


    public static final int WINDOW_ICON_WIDTH = 58;
    public static final int WINDOW_ICON_HEIGHT = 58;

    public static final String MAIL_XAVER = "w.xaver@googlemail.com";
    public static final String URL_WEBSITE_DONATE = "https://www.p2tools.de/donate/";
    public static final String URL_WEBSITE_FORUM = "https://p2forum.de";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String LINE_SEPARATORx2 = LINE_SEPARATOR + LINE_SEPARATOR;
    public static final String LINE_SEPARATORx3 = LINE_SEPARATOR + LINE_SEPARATOR + LINE_SEPARATOR;
    public static final String LINE_SEPARATORx4 = LINE_SEPARATOR + LINE_SEPARATOR + LINE_SEPARATOR + LINE_SEPARATOR;
    public static final String CONFIG_XML_START = "Mediathek";
    public static List<String> cssFileList = new ArrayList();
    public static String logfile = "P2Tools_%g.log";
    public static String logfile_0 = "P2Tools_0.log";
    public static String logdir = "Log";
    public static String styleFile = ""; // zb. für die Anpassung der Schriftgröße
    public static int MIN_BUTTON_WIDTH = 100;
    //sind die Einstellungen des Programms
    public static Stage primaryStage = null;
    public static String progName = "P2Tools";
    public static String userAgent = "";
    public static BooleanProperty darkMode = new SimpleBooleanProperty(false);
    public static BooleanProperty blackWhite = new SimpleBooleanProperty(false);

    public static BooleanProperty useProxy = new SimpleBooleanProperty(false);
    public static StringProperty proxyHost = new SimpleStringProperty("");
    public static StringProperty proxyPort = new SimpleStringProperty("");
    public static StringProperty proxyUser = new SimpleStringProperty("");
    public static StringProperty proxyPwd = new SimpleStringProperty("");

    public static boolean debug = false; //Debugmodus
    public static boolean duration = false; //Duration ausgeben

    //Projektweite Abstände
    public static int DIST_BUTTON = 5;//Abstand zwischen den Button
    public static int DIST_BUTTON_BLOCK = 10;//Abstand zwischen den Button-Blöcken
    public static int DIST_BUTTON_BLOCK_BIG = 20;//Abstand zwischen den Button-Blöcken

    public static int DIST_GRIDPANE_HGAP = 5;
    public static int DIST_GRIDPANE_VGAP = 5;
    public static int PADDING_GRIDPANE = 10;
    public static int PADDING_GRIDPANE_SMALL = 5;

    public static int PADDING = 10;//Abstand zum Rand
    public static int PADDING__SMALL = 5;//Abstand zum Rand

    public static int SPACING_VBOX = 10;
    public static int SPACING_VBOX_SMALL = 5;
    public static int SPACING_HBOX = 10;
    public static int SPACING_HBOX_SMALL = 5;

    public static int PADDING_VBOX = 10;//Abstand zum Rand
    public static int PADDING_VBOX_SMALL = 5;//Abstand zum Rand
    public static int PADDING_HBOX = 10;//Abstand zum Rand
    public static int PADDING_HBOX_SMALL = 5;//Abstand zum Rand
}
