package newton.FXCustomClasses;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.LocalDateTime;


//because there is no class in javafx that gets the hour and minute precisely
public class PreciseTimePicker extends HBox {

    private final DatePicker datePicker;
    private final TextField hourField;
    private final TextField minuteField;
    private final ToggleButton amPmToggle;
    private final CheckBox specialOptionCheckbox;

    private final BooleanProperty useCurrentTime = new SimpleBooleanProperty(false);
    private String specialOption;

    public PreciseTimePicker(String specialOption) {
        // Initialize components
        datePicker = new DatePicker();
        hourField = new TextField();
        minuteField = new TextField();
        amPmToggle = new ToggleButton("AM");
        this.specialOption = specialOption;
        specialOptionCheckbox = new CheckBox("Set to" + specialOption);


        // Set up layout
        setSpacing(10);
        setPadding(new Insets(10));

        // Configure TextField properties
        hourField.setPromptText("HH");
        minuteField.setPromptText("MM");

        // Add components to the HBox
        getChildren().addAll(
                datePicker,
                new Label("Hour (1-12):"), hourField,
                new Label("Minute (0-59):"), minuteField,
                amPmToggle,
                specialOptionCheckbox
        );

        // Add event listeners
        specialOptionCheckbox.selectedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                setToCurrentTime();
            }
        });

        amPmToggle.setOnAction(event -> {
            if (!amPmToggle.isSelected()) {
                amPmToggle.setText("PM");
            } else {
                amPmToggle.setText("AM");
            }
        });
    }

    /**
     * Sets the control to the current date and time.
     */
    private void setToCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        datePicker.setValue(now.toLocalDate());
        hourField.setText(String.format("%02d", now.getHour() % 12 == 0 ? 12 : now.getHour() % 12));
        minuteField.setText(String.format("%02d", now.getMinute()));
        amPmToggle.setText(now.getHour() < 12 ? "AM" : "PM");
        amPmToggle.setSelected(now.getHour() < 12);
    }

    /**
     * Validates the input and returns the selected LocalDateTime.
     *
     * @return The selected LocalDateTime, or null if invalid.
     */
    public LocalDateTime getSelectedDateTime() throws IllegalArgumentException{
        try {

            //check the speical option first :
            if(specialOptionCheckbox.isSelected()) {

                if(specialOption.equals("NOW"))
                    return LocalDateTime.now();

                if (specialOption.equals("INFINITY"))
                    return LocalDateTime.MAX;

            }

            // Validate date
            if (datePicker.getValue() == null) {
                throw new IllegalArgumentException("Date is not selected.");
            }

            // Validate hour
            int hour = Integer.parseInt(hourField.getText());
            if (hour < 1 || hour > 12) {
                throw new IllegalArgumentException("Hour must be between 1 and 12.");
            }

            // Validate minute
            int minute = Integer.parseInt(minuteField.getText());
            if (minute < 0 || minute > 59) {
                throw new IllegalArgumentException("Minute must be between 0 and 59.");
            }

            // Determine period (AM/PM)
            boolean isAm = amPmToggle.isSelected();
            int finalHour = (hour == 12 ? 0 : hour) + (isAm ? 0 : 12);

            // Combine into LocalDateTime

            return LocalDateTime.of(
                    datePicker.getValue().getYear(),
                    datePicker.getValue().getMonth(),
                    datePicker.getValue().getDayOfMonth(),
                    finalHour,
                    minute
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Resets the control to its default state.
     */
    public void reset() {
        datePicker.setValue(null);
        hourField.clear();
        minuteField.clear();
        amPmToggle.setText("AM");
        amPmToggle.setSelected(true);
        specialOptionCheckbox.setSelected(false);
    }

    /**
     * Sets the control to a specific LocalDateTime.
     *
     * @param dateTime The LocalDateTime to set.
     */
    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            datePicker.setValue(dateTime.toLocalDate());
            int hour = dateTime.getHour() % 12 == 0 ? 12 : dateTime.getHour() % 12;
            hourField.setText(String.format("%02d", hour));
            minuteField.setText(String.format("%02d", dateTime.getMinute()));
            amPmToggle.setText(dateTime.getHour() < 12 ? "AM" : "PM");
            amPmToggle.setSelected(dateTime.getHour() < 12);
        }
    }
}