package com.dagbok.dagbok;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.transaction.Transactional;

public interface DagbokRepository extends CrudRepository<Dagbok, Integer> {
    
    @Query("SELECT d FROM Dagbok d WHERE d.raderad = 0")
    List<Dagbok> findByNotDeleted();

    @Query("SELECT d FROM Dagbok d WHERE d.raderad = 0 AND d.datum = :datum")
    List<Dagbok> findByNotDeletedAndDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum);

    @Query("SELECT d FROM Dagbok d WHERE d.raderad = 0 AND d.datum BETWEEN :datum1 AND :datum2")
    List<Dagbok> searchBetweenDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum1,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datum2);

    @Transactional
    @Modifying
    @Query("UPDATE Dagbok d SET d.raderad = 1 WHERE d.id = ?1")
    int raderadDagbok(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Dagbok d SET d.rubrik = ?2, d.datum = ?3, d.text = ?4 WHERE d.id = ?1 AND d.raderad = 0")
    int redigeraDagbok(int id, String nyRubrik, LocalDate nyDatum, String nyText);
}


