package org.app.zoo.provider;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Repository
@Schema(description = "Provider interface to interact with the DB")
public interface ProviderRepository extends JpaRepository<Provider, Integer>, JpaSpecificationExecutor<Provider> {
    boolean existsByNameAndProvinceIdAndServiceTypeIdAndProviderTypeIdAndEmailAndPhoneAndAddress(String name, int provinceId, int serviceTypeId, int providerTypeId, String email, String phone, String address);
}
