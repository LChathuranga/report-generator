package com.kingit.report_generator.exception;

public class NotFinishedReportException extends Exception {
    public NotFinishedReportException() {
        super("Report generation is not Finished or Failed ... Try again");
    }
}
