package com.sec.gen.next.backend.common.address;

import com.sec.gen.next.backend.api.internal.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
