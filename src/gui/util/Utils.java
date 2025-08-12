package gui.util;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	// method tryParseToInt to convert to Integer if is not String return null
	public static Integer tryParseToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double tryParseToDouble(String str) {
		try {
		return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);
				
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}
	
	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%."+decimalPlaces+"f", item));
					}
				}
			};
			return cell;
		});
	}
	
	public static Date parseDate(String dateStr) {
		if (dateStr == null || dateStr.trim().isEmpty()) {
			throw new IllegalArgumentException("Date can't be empty or null!");
		}
		
		String[] formatDate = {
				"dd/MM/yyyy",
				"dd/MM/yyyy HH:mm:ss",
				"dd-MM-yyyy",
				"dd-MM-yyyy HH:mm:ss",
				"yyyy-MM-dd"
		};
		
		for(String checkFormatDates : formatDate) {
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern(checkFormatDates);
				LocalDateTime ldt;
				
				if (checkFormatDates.contains("HH:mm:ss")) {
					ldt = LocalDateTime.parse(dateStr, dtf);
				} else {
					ldt = LocalDateTime.parse(dateStr + "00:00:00",
							DateTimeFormatter.ofPattern(checkFormatDates + "HH:mm:ss"));
				}
				return Date.from(ldt.atZone(ZoneId.of("GMT")).toInstant());
			} catch (DateTimeParseException e) {
				continue;
			}
		}
		
		throw new IllegalArgumentException(String.format("Date '%s' is not in the knowleged format"
				+ "Format accept: dd/MM/yyyy, dd-MM-yyyy, yyyy-MM-dd"
				+ "(all format date can have or not HH:mm:ss", dateStr));
	}
}
