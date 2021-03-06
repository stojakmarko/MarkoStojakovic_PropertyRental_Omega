package com.example.propertyrental.repository;


import com.example.propertyrental.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    @Query("select p from Property p where p not in(select s.property from Submission s where s.status='PENDING' )")
    Page<Property> findAllByStatus(Pageable pageable);

    Optional<Property> findByIdAndOwner_UserName(UUID id, String username);

    @Query("select p from Property p where p not in(select s.property from Submission s where s.status='PENDING' ) " +
            "and concat(p.name,p.price,p.location) like %:search%  ")
    Page<Property> findAllSearch(Pageable pageable, @Param("search") String search);


}
