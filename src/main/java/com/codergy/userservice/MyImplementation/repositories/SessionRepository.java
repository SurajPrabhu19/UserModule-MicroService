package com.codergy.userservice.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codergy.userservice.models.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    // @Query("SELECT s FROM Session s WHERE s.id = :uuid")
    // List<Session> findAllByUUID(@Param("uuid") UUID uuid);

    List<Session> findByUser_Id(UUID userId);

    void deleteByUser_Id(UUID id);

    @Modifying
    @Query("DELETE FROM Session s WHERE s.id = FUNCTION('UUID_TO_BIN', :uuid)")
    void deleteBySessionUUID(String uuid);

    void deleteByToken(String token);
}