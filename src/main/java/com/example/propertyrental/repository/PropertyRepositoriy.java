package com.example.propertyrental.repository;


import com.example.propertyrental.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyRepositoriy extends JpaRepository<Property, UUID> {
}
