/*
 * P2tools Copyright (C) 2018 W. Xaver W.Xaver[at]googlemail.com
 * https://www.p2tools.de
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

package de.p2tools.p2Lib.checkForUpdates;

import de.p2tools.p2Lib.PConst;
import de.p2tools.p2Lib.alert.PAlert;
import de.p2tools.p2Lib.tools.log.PLog;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.stage.Stage;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SearchProgInfo {

    private final String UPDATE_SEARCH_TITLE = "Software-Aktualisierung";
    private final String UPDATE_ERROR_MESSAGE =
            "Es ist ein Fehler bei der Softwareaktualisierung aufgetreten." + PConst.LINE_SEPARATOR
                    + "Die aktuelle Version konnte nicht ermittelt werden.";

    private static final int TIMEOUT = 10_000; // timeout ms
    private ProgInfo progInfo = new ProgInfo();
    private String searchUrl = "";
    private int lastInfoNr;
    private ArrayList<Infos> newInfosList = new ArrayList<>(5);
    boolean newVersion = false;
    boolean newInfo = false;
    BooleanProperty bPropShowUpdateInfo = null;
    private final Stage stage;

    public SearchProgInfo() {
        this.stage = PConst.primaryStage;
    }

    public SearchProgInfo(Stage stage) {
        this.stage = stage;
    }

    public boolean checkUpdate(String searchUrl, int progVersion,
                               IntegerProperty infoNr,
                               boolean showAllwaysInfo, boolean showError) {

        return check(searchUrl, progVersion, infoNr, showAllwaysInfo, showError);
    }

    public boolean checkUpdate(String searchUrl, int progVersion,
                               IntegerProperty infoNr, BooleanProperty bPropShowUpdateInfo,
                               boolean showAlwaysInfo, boolean showError) {

        // return neue Version oder neue Infos
        this.bPropShowUpdateInfo = bPropShowUpdateInfo;
        return check(searchUrl, progVersion, infoNr, showAlwaysInfo, showError);
    }

    private boolean check(String searchUrl, int progVersion,
                          IntegerProperty infoNr,
                          boolean showAlwaysInfo, boolean showError) {

        // prüft auf neue Version, aneigen: wenn true
        // showAllwaysInfo-> dann wird die Info immer angezeigt

        PLog.sysLog("check update");
        this.searchUrl = searchUrl;
        this.lastInfoNr = infoNr.get();

        if (!retrieveProgramInformation(progInfo)) {
            progInfo = null;
        }


        if (progInfo == null || progInfo.getProgVersion() < 0) {
            // dann hats nicht geklappt
            PLog.errorLog(978451203, "Das Suchen nach einem Programmupdate hat nicht geklappt!");

            if (showAlwaysInfo || showError) {
                // dann konnte die "Version" im xml nicht geparst werden
                Platform.runLater(() -> new PAlert().showErrorAlert(stage, "Fehler", UPDATE_SEARCH_TITLE, UPDATE_ERROR_MESSAGE));
            }
            return false;
        }


        // dann haben wir was gefunden -> auswerten
        newVersion = progInfo.getProgVersion() > progVersion;

        for (Infos i : progInfo.getInfos()) {
            if (i.getInfoNr() > lastInfoNr) {
                newInfo = true;
                infoNr.setValue(i.getInfoNr());
                newInfosList.add(i);
            }
        }

        if (newVersion || newInfo || showAlwaysInfo) {
            // wenn Version<0 hat was nicht geklappt
            displayNotification();
        }

        return newVersion;
    }

    private void displayNotification() {
        Platform.runLater(() -> new InfoUpdateAlert(stage, progInfo, newInfosList, newVersion, bPropShowUpdateInfo).showInfoAlert(
                "Programminfos", (newVersion ? "Neue Version verfügbar" : "Infos")));
    }

    /**
     * Load and parse the update information.
     *
     * @return parsed update info for further use when successful
     */
    private boolean retrieveProgramInformation(ProgInfo progInfo) {
        boolean ret;
        XMLStreamReader parser = null;

        final XMLInputFactory inFactory = XMLInputFactory.newInstance();
        inFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);

        try (InputStreamReader inReader = new InputStreamReader(connectToServer(), StandardCharsets.UTF_8)) {

            parser = inFactory.createXMLStreamReader(inReader);
            ret = getConfig(parser, progInfo);

        } catch (final Exception ex) {
            PLog.errorLog(951203214, ex);
            ret = false;
        } finally {
            try {
                if (parser != null) {
                    parser.close();
                }
            } catch (final Exception ignored) {
            }
        }

        return ret;
    }

    private InputStream connectToServer() throws IOException {
        final HttpURLConnection conn = (HttpURLConnection) new URL(searchUrl).openConnection();
        conn.setRequestProperty("User-Agent", PConst.userAgent);
        conn.setReadTimeout(TIMEOUT);
        conn.setConnectTimeout(TIMEOUT);

        return conn.getInputStream();
    }

    private boolean getConfig(XMLStreamReader parser, ProgInfo progInfo) {
        boolean ret = true;
        try {
            while (parser.hasNext()) {
                int event = parser.next();
                if (event != XMLStreamConstants.START_ELEMENT) {
                    continue;
                }
                if (event == XMLStreamConstants.END_ELEMENT) {
                    break;
                }
                switch (parser.getLocalName()) {
                    case ProgInfo.ParserTags.PROG_NAME:
                        progInfo.setProgName(parser.getElementText());
                        break;
                    case ProgInfo.ParserTags.PROG_URL:
                        progInfo.setProgUrl(parser.getElementText());
                        break;
                    case ProgInfo.ParserTags.PROG_DOWNLOAD_URL:
                        progInfo.setProgDownloadUrl(parser.getElementText());
                        break;
                    case ProgInfo.ParserTags.PROG_VERSION:
                        progInfo.setProgVersion(parser.getElementText());
                        break;
                    case ProgInfo.ParserTags.PROG_RELEASE_NOTES:
                        progInfo.setProgReleaseNotes(parser.getElementText());
                        break;
                    case ProgInfo.ParserTags.PROG_INFOS:

                        final int count = parser.getAttributeCount();
                        String no = "";
                        for (int i = 0; i < count; ++i) {
                            if (parser.getAttributeName(i).toString().equals(ProgInfo.ParserTags.PROG_INFOS_NUMBER)) {
                                no = parser.getAttributeValue(i);
                            }
                        }
                        final String info = parser.getElementText();
                        if (!no.isEmpty() && !info.isEmpty()) {
                            progInfo.addProgInfo(new Infos(info, no));
                        }
                        break;

                    default:
                        break;
                }
            }
        } catch (final Exception ex) {
            ret = false;
            PLog.errorLog(645120302, ex);
        }
        return ret;
    }
}
