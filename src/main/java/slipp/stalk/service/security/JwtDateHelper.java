package slipp.stalk.service.security;

import org.joda.time.DateTime;

import java.util.Date;

public class JwtDateHelper {

    private final int expirationHour;

    public JwtDateHelper(int expirationHour) {
        this.expirationHour = expirationHour;
    }

    public Date expirationDate() {
        DateTime dateTime = new DateTime();
        return dateTime.plusHours(expirationHour).toDate();
    }

    public Date issuedDate() {
        return now();
    }

    private Date now() {
        return new Date();
    }
}