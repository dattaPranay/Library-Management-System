package model;

import lombok.Data;

import java.util.Date;

@Data
public class History {
    private final String userId;
    private final Date dateIssued;
    private final Date expiryDate;
    private Date returnDate;
    private final String bookId;
}
