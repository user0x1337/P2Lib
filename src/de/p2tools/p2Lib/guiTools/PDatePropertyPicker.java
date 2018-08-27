/*
 * P2tools Copyright (C) 2018 W. Xaver W.Xaver[at]googlemail.com
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


package de.p2tools.p2Lib.guiTools;

import de.p2tools.p2Lib.tools.PDate;
import de.p2tools.p2Lib.tools.PDateProperty;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDatePropertyPicker extends DatePicker {
    private PDateProperty pDateProperty = new PDateProperty();
    private final String pattern = "dd.MM.yyyy";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

    public PDatePropertyPicker() {
        setDatePickerConverter();
        setDate(null);
    }

    public PDatePropertyPicker(PDateProperty pDateProperty) {
        this.pDateProperty = pDateProperty;
        setDatePickerConverter();
    }

    public void setpDateProperty(PDateProperty pDateProperty) {
        this.pDateProperty = pDateProperty;
        setDate(pDateProperty == null ? null : pDateProperty.toString());
    }

    private void setDate() {
        PDate pDate = pDateProperty.getValue();
        setDate(pDate == null ? null : pDate.toString());
    }

    public void setDate(String stringDate) {
        if (stringDate == null || stringDate.isEmpty()) {
            this.setValue(null);
        } else {
            this.setValue(LocalDate.parse(stringDate, dateFormatter));
        }
    }

    public void clearDate() {
        this.pDateProperty = null;
        this.setValue(null);
    }

    public String getDate() {
        String ret = "";

        LocalDate date = getValue();
        if (date != null) {
            ret = dateFormatter.format(date);
        }

        return ret;
    }

    private void setDatePickerConverter() {
        getStyleClass().add("PDatePropertyPicker");
        final String CSS_FILE = "de/p2tools/p2Lib/p2Lib.css";
        getStylesheets().add(CSS_FILE);


        setPromptText(pattern.toLowerCase());
        StringConverter converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
//                System.out.println("");
//                System.out.println("LocalDate: " + (date == null ? "null" : date.toString()));
                if (pDateProperty == null) {
                    return "";
                }
                if (date != null) {

                    final String str = dateFormatter.format(date);
                    pDateProperty.setPDate(str);

                    if (pDateProperty.getValue().getTime() == 0) {
                        return "";
                    }

                    return str;
                } else {
                    pDateProperty.clearPDate();
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
//                System.out.println("String: " + (string == null ? "null" : string));
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        setConverter(converter);


//        setOnAction(event -> {
//            LocalDate date = getValue();
//            System.out.println("Selected date: " + date);
//            System.out.println("         date: " + date.toString());
//        });
    }
}
