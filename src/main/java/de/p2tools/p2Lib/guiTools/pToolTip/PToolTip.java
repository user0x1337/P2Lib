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


package de.p2tools.p2Lib.guiTools.pToolTip;

import javafx.beans.property.StringProperty;

public class PToolTip {
    private final String text;
    private final String image;
    private final String hyperlinkWeb;
    private final StringProperty openUrl;
    private boolean wasShown = false;

    public PToolTip(String text, String image) {
        this.text = text;
        this.image = image;
        this.hyperlinkWeb = null;
        this.openUrl = null;
    }

    public PToolTip(String text, String image, String pHyperlink, StringProperty openUrl) {
        this.text = text;
        this.image = image;
        this.hyperlinkWeb = pHyperlink;
        this.openUrl = openUrl;
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

    public String getOpenUrl() {
        return openUrl.get();
    }

    public StringProperty openUrlProperty() {
        return openUrl;
    }

    public boolean isWasShown() {
        return wasShown;
    }

    public void setWasShown(boolean wasShown) {
        this.wasShown = wasShown;
    }
}
