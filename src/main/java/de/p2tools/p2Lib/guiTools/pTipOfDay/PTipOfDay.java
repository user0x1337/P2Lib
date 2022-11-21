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

import de.p2tools.p2Lib.P2LibConst;
import javafx.beans.property.StringProperty;

public class PTipOfDay {
    public static final String START = "                                                     " + P2LibConst.LINE_SEPARATOR;
    private final String text;
    private final String image;
    private final String hyperlinkWeb;
    private final StringProperty openUrlWithProg;
    private boolean wasShown = false;

    public PTipOfDay(String text, String image) {
        this.text = text;
        this.image = image;
        this.hyperlinkWeb = null;
        this.openUrlWithProg = null;
    }

    public PTipOfDay(String text, String image, String pHyperlink, StringProperty openUrlWithProg) {
        this.text = text;
        this.image = image;
        this.hyperlinkWeb = pHyperlink;
        this.openUrlWithProg = openUrlWithProg;
    }

    public static PTipOfDay getTipWebsite(StringProperty progOpenUrl) {
        final String URL_WEBSITE = "https://www.p2tools.de";
        String text = START;
        text = START;
        text += "Weiter Tips und Infos\n" +
                "finden sich auch auf der\n" +
                "Website. Dort gibt es\n" +
                "auch eine Anleitung zum\n" +
                "Programm.\n\n" +
                "Fragen zum Programm und\n" +
                "Ideen gerne auch per Mail.\n\n";

        String image = "/de/p2tools/p2Lib/toolTips/Website.png";
        return new PTipOfDay(text, image, URL_WEBSITE, progOpenUrl);
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }

    public String getHyperlinkWeb() {
        return hyperlinkWeb;
    }

    public String getOpenUrlWithProg() {
        return openUrlWithProg.get();
    }

    public StringProperty openUrlWithProgProperty() {
        return openUrlWithProg;
    }

    public boolean isWasShown() {
        return wasShown;
    }

    public void setWasShown(boolean wasShown) {
        this.wasShown = wasShown;
    }
}
