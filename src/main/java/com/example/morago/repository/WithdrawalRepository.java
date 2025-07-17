package com.example.morago.repository;

import com.example.morago.model.entity.Withdrawal;
import com.example.morago.model.enums.PaymentStatusEnum;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    Optional<Withdrawal> findFirstByTranslatorIdAndStatusOrderByCreatedAtDesc(
        Long translatorId,
        PaymentStatusEnum status
    );

    Optional<Withdrawal> findWithdrawalById(Long id);

    Page<Withdrawal> findByTranslatorId(Long translatorId, Pageable pageable);
}
