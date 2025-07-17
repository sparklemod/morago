package com.example.morago.repository;

import com.example.morago.model.entity.Deposit;
import com.example.morago.model.enums.PaymentStatusEnum;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Optional<Deposit> findFirstByUserIdAndStatusOrderByCreatedAtDesc(Long userId, PaymentStatusEnum status);

    Optional<Deposit> findDepositById(Long id);

    Page<Deposit> findByUserId(Long userId, Pageable pageable);
}
