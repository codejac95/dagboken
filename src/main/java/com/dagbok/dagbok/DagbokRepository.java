package com.dagbok.dagbok;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.transaction.Transactional;

public interface DagbokRepository extends CrudRepository<Dagbok, Integer> {
    
    @Query("SELECT d FROM Dagbok d WHERE d.deleted = 0")
    List<Dagbok> findByNotDeleted();

    @Query("SELECT d FROM Dagbok d WHERE d.deleted = 0 AND d.date = :date")
    List<Dagbok> findByNotDeletedAndDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @Query("SELECT d FROM Dagbok d WHERE d.deleted = 0 AND d.date BETWEEN :date1 AND :date2")
    List<Dagbok> searchBetweenDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2);

    @Transactional
    @Modifying
    @Query("UPDATE Dagbok d SET d.deleted = 1 WHERE d.id = ?1")
    int deletedDiary(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Dagbok d SET d.header = ?2, d.date = ?3, d.text = ?4 WHERE d.id = ?1 AND d.deleted = 0")
    int editDiary(int id, String newHeader, LocalDate newDate, String newText);
}


