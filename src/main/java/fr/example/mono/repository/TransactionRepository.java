package fr.example.mono.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import fr.example.mono.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {
    public List<Transaction> findByPayer(Integer payer);

    public List<Transaction> findByPaid(Integer paid);

    public Page<Transaction> findByPayerOrPaid(@NonNull Integer payer, @NonNull Integer paid, Pageable pageable);

    public List<Transaction> findByPayerOrPaidOrderByDateDesc(@NonNull Integer payer, @NonNull Integer paid);

    public Transaction save(Transaction transaction);

    public long countByPayerOrPaid(Integer payer, Integer paid);
}
