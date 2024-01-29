package fr.example.mono.service;

import fr.example.mono.model.Account;
import fr.example.mono.model.CardTypeProvider;
import fr.example.mono.repository.AccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AccountService {
	
    private final AccountRepository accountRepository;
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }
    
    public void addAccount(Account account, String email) {
        Integer uid = userService.getUidByEmail(email);
        if(uid == null) throw new IllegalArgumentException("Invalid user id");
        account.setCardType(null);
        switch (Integer.parseInt(account.getCardNumber().substring(0, 1))) {
            case 4:
                account.setCardType(CardTypeProvider.Visa);
                break;
            case 5:
                account.setCardType(CardTypeProvider.MasterCard);
                break;
            case 3:
                account.setCardType(CardTypeProvider.AmericanExpress);
                break;
            default: throw new IllegalArgumentException("Invalid card number");
        }
        account.setUid(uid);
        account.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        accountRepository.save(account);
    }

    public List<Account> getAccountsByEmail(String email) {
        Integer uid = userService.getUidByEmail(email);
        return new ArrayList<>(accountRepository.findAllByUid(uid));
    }

    public void supplyAccount(double amount, int aid) {
        Account account = accountRepository.findByAid(aid);
        userService.addMoney(amount, account.getUid());
    }

    public void removeAccount(int aid) {
        accountRepository.deleteById(aid);
    }
}
