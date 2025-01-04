package org.app.zoo.programation;

import java.sql.Date;

public record ProgramationSearchCriteria(
    String searchField,
    Date minDate,
    Date maxDate,
    String minTime, // en formato HH:MM:SS
    String maxTime, // en formato HH:MM:SS
    int speciesId,
    int pageNumber,
    int itemsPerPage
) {
    
}
