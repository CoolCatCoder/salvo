package com.salvo.salvo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource   // A Repository class is analogous to a table
public interface ShipRepository extends JpaRepository<Ship, Long> {
}
