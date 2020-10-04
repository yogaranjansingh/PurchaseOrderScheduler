package com.example.purchaseOrderScheduler.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@Slf4j
public class Slot {

    Date start;
    Date end;

    public Slot(String slotStartDate, String slotEndDate) {
        this.start = parseAndFormatDate(slotStartDate);
        this.end = parseAndFormatDate(slotEndDate);
    }

    private Date parseAndFormatDate(String dateTime) {
        if (dateTime == null)
            return null;

        LocalDateTime ldt = null;
        Date date = null;
        if (dateTime.contains("T"))
            dateTime = dateTime.replace("T", " ");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            if (dateTime.length() > 5) {
                ldt = LocalDateTime.parse(dateTime, df);
                date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return date;
    }
}
