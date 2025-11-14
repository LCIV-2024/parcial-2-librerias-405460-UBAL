package com.example.libreria.repository;

import com.example.libreria.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByStatus(Reservation.ReservationStatus status);

    @Query("""
       SELECT r FROM Reservation r
       WHERE r.status = :status
       AND r.expectedReturnDate < :today
       """)
    List<Reservation> findOverdueReservations(
            @Param("status") Reservation.ReservationStatus status,
            @Param("today") LocalDate today
    );

    default List<Reservation> findOverdueReservations() {
        return findOverdueReservations(
                Reservation.ReservationStatus.ACTIVE,
                LocalDate.now()
        );
    }
}

