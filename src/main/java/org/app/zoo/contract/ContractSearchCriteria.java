package org.app.zoo.contract;

public record ContractSearchCriteria(
    String searchField,
    int providerTypeId,
    int contractState, // 1 activos, 2 no activos , 3 futuros , 0 no aplica
    double minBasePrice,
    double maxBasePrice,
    int pageNumber,
    int itemsPerPage
    ){
}

