package com.kingit.report_generator.validation;

import org.apache.coyote.BadRequestException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CustomDataValidator {
    public static void validateReportDates(String startDateString, String endDateString) throws BadRequestException, IllegalArgumentException {
        if(startDateString.isBlank() || endDateString.isBlank()){
            throw new BadRequestException("Start date and end date can not be null");
        }

        try {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);

            if(endDate.isBefore(startDate)){
                throw new IllegalArgumentException("End date can not be before start date");
            }
        } catch (DateTimeParseException e){
            throw new BadRequestException("Invalid date format. Please use YYYY-MM-DD");
        }
    }

    public static void validateReportId(String reportId) throws BadRequestException {
        if(reportId == null){
            throw new BadRequestException("Report id can not be null");
        }
    }
}
