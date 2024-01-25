package com.sec.gen.next.backend.internal.common.address;

import com.sec.gen.next.backend.internal.api.internal.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
