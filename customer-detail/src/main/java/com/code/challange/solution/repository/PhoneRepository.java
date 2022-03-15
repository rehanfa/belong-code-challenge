package com.code.challange.solution.repository;


import com.code.challange.solution.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Phone findByPhoneId(Long id);

}
