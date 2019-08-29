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


import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;

public class PTableFactory {

    public static void invertSelection_(TableView tableView) {
        for (int i = 0; i < tableView.getItems().size(); ++i) {
            if (tableView.getSelectionModel().isSelected(i)) {
                tableView.getSelectionModel().clearSelection(i);
            } else {
                tableView.getSelectionModel().select(i);
            }
        }
    }

    public static void invertSelection(TableView tableView) {
        final List<Integer> selArr = tableView.getSelectionModel().getSelectedIndices();
        final HashSet<Integer> selHash = new HashSet<>(selArr);

        tableView.getSelectionModel().selectAll();
        int sum = tableView.getItems().size();

        int sel = -1;
        for (int i = 0; i < sum; ++i) {
            if (selHash.contains(i)) {
                tableView.getSelectionModel().clearSelection(i);
            } else if (sel < 0) {
                sel = i;
            }
        }

        if (sel >= 0) {
            // damits auch gemeldet wird
            tableView.getSelectionModel().select(sel);
        }

    }

    public static <S> void addAutoScroll(final TableView<S> view) {
        if (view == null) {
            throw new NullPointerException();
        }
        ObservableList<S> list = view.getItems();
        list.addListener((ListChangeListener<S>) (c -> {
            c.next();
            final int size = view.getItems().size();
            if (size > 0 && c.wasAdded()) {
                S element = list.get(c.getFrom());

                view.getSelectionModel().clearSelection();
                view.scrollTo(element);
                view.getSelectionModel().select(element);
            }
        }));
    }

    static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    public static class PCellMoney<A, V> extends TableCell<A, V> {
        boolean showNull = true;

        public PCellMoney() {
            setAlignment(Pos.CENTER_RIGHT);
            setPadding(new Insets(0, 5, 0, 0));
        }

        public PCellMoney(boolean showNull) {
            this.showNull = showNull;
            setAlignment(Pos.CENTER_RIGHT);
            setPadding(new Insets(0, 5, 0, 0));
        }

        @Override
        protected void updateItem(V value, boolean empty) {
            super.updateItem(value, empty);
            if (empty) {
                setText(null);
            } else {
                if (value.getClass().equals(Long.class)) {
                    double d = (Long) value;
                    if (!showNull && d == 0) {
                        setText("");
                    } else {
                        setText(currencyFormat.format(d / 100));
                    }
                } else {
                    setText(currencyFormat.format(value)); // todo
                }
            }
        }
    }
}
