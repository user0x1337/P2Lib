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

package de.p2tools.p2lib.tools.date;

import java.time.LocalDateTime;

public class PLDateTimeFactory {

    private PLDateTimeFactory() {
    }

    public static LocalDateTime setDate(String strDate, String strTime) {
        LocalDateTime localDateTime;
        if (strDate.isEmpty()) {
            localDateTime = LocalDateTime.now();
            return localDateTime;
        }

        try {
            if (strTime.isEmpty()) {
                localDateTime = LocalDateTime.parse(strDate, DateFactory.DT_FORMATTER_dd_MM_yyyy);
            } else {
                localDateTime = LocalDateTime.parse(strDate + strTime, DateFactory.DT_FORMATTER_dd_MM_yyyy___HH__mm__ss);
            }
            return localDateTime;
        } catch (final Exception ex) {
        }

        localDateTime = LocalDateTime.MIN;
        return localDateTime;
    }

    public static LocalDateTime setDate(String strDateTime) {
        LocalDateTime localDateTime;
        if (strDateTime.isEmpty()) {
            localDateTime = LocalDateTime.now();
            return localDateTime;
        }

        try {
            localDateTime = LocalDateTime.parse(strDateTime, DateFactory.DT_FORMATTER_dd_MM_yyyy___HH__mm__ss);
            return localDateTime;

        } catch (final Exception ex) {
        }

        localDateTime = LocalDateTime.MIN;
        return localDateTime;
    }

    public static String toStringDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        } else if (localDateTime.isEqual(LocalDateTime.MIN)) {
            return "";
        } else {
            return localDateTime.format(DateFactory.DT_FORMATTER_dd_MM_yyyy);
        }
    }

    public static String toStringTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        } else if (localDateTime.isEqual(LocalDateTime.MIN)) {
            return "";
        } else {
            return localDateTime.format(DateFactory.DT_FORMATTER_HH__mm);
        }
    }

    public static LocalDateTime fromString(String strDate) {
        try {
            if (strDate.isEmpty()) {
                return LocalDateTime.MIN;
            } else {
                return LocalDateTime.parse(strDate, DateFactory.DT_FORMATTER_dd_MM_yyyy___HH__mm__ss);
            }
        } catch (Exception ex) {
            return LocalDateTime.MIN;
        }
    }

    public static LocalDateTime fromStringR(String strDate) {
        try {
            if (strDate.isEmpty()) {
                return LocalDateTime.MIN;
            } else {
                return LocalDateTime.parse(strDate, DateFactory.DT_FORMATTER_yyyy_MM_dd___HH__mm__ss);
            }
        } catch (Exception ex) {
            return LocalDateTime.MIN;
        }
    }

    public static String toString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        } else if (localDateTime.isEqual(LocalDateTime.MIN)) {
            return "";
        } else {
            return localDateTime.format(DateFactory.DT_FORMATTER_dd_MM_yyyy___HH__mm__ss);
        }
    }

    public static String toStringR(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        } else if (localDateTime.isEqual(LocalDateTime.MIN)) {
            return "";
        } else {
            return localDateTime.format(DateFactory.DT_FORMATTER_yyyy_MM_dd___HH__mm__ss);
        }
    }

}
