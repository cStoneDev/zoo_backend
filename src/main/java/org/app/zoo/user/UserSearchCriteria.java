package org.app.zoo.user;

public record UserSearchCriteria(
    String searchField,
    int roleId,
    int pageNumber,
    int itemsPerPage
) {
}
