package com.sec.next.gen.userservice.repository;

import com.sec.next.gen.userservice.api.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
