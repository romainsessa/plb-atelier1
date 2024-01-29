package fr.example.mono.repository;

import org.springframework.data.repository.CrudRepository;

import fr.example.mono.model.Account;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    public List<Account> findAllByUid(Integer uid);
    public Account findByAid(Integer aid);
}
