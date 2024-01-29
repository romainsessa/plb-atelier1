package fr.example.mono.service;

import fr.example.mono.dto.TransactionDto;
import fr.example.mono.model.Transaction;
import fr.example.mono.model.User;
import fr.example.mono.repository.TransactionRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TransactionService {
	private final UserService userService;
	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository, UserService userService) {
		this.transactionRepository = transactionRepository;
		this.userService = userService;
	}

	public List<Transaction> getTransactionsByUid(Integer uid) {
		List<Transaction> res = new ArrayList<>();
		res.addAll(transactionRepository.findByPayer(uid));
		res.addAll(transactionRepository.findByPaid(uid));
		return res;
	}

	public void addTransaction(String payerEmail, Integer paid, String label, double amount)
			throws IllegalArgumentException {
		Integer payer;
		boolean enoughFund;
		double truncatedAmount = truncateDouble(amount);
		double truncatedAmountWithFees = truncateDouble(amount * (1.005));
		payer = userService.findUserByEmail(payerEmail).getUid();
		enoughFund = userService.addMoney(truncatedAmountWithFees * (-1), payer);
		if (enoughFund) {
			Transaction transaction = new Transaction();
			transaction.setPayer(payer);
			transaction.setPaid(paid);
			transaction.setLabel(label);
			transaction.setDate(new Timestamp(System.currentTimeMillis()));
			transaction.setPrice(truncatedAmountWithFees);
			transactionRepository.save(transaction);
			userService.addMoney(truncatedAmount, paid);
		} else {
			throw new IllegalArgumentException("Insufficient funds");
		}
	}

	public static Double truncateDouble(double oldValue) {
		return BigDecimal.valueOf(oldValue).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public static Sort.Direction getSortDirection(String rawDir) {
		if ("asc".equalsIgnoreCase(rawDir)) {
			return Sort.Direction.ASC;
		} else if ("desc".equalsIgnoreCase(rawDir)) {
			return Sort.Direction.DESC;
		} else {
			throw new IllegalArgumentException("Wrong sort direction");
		}
	}

	public Page<TransactionDto> getPage(String email, Pageable pagingSort) {
		int uid = userService.getUidByEmail(email);
		Page<Transaction> pageTransactions;
		pageTransactions = transactionRepository.findByPayerOrPaid(uid, uid, pagingSort);

		List<TransactionDto> transactionsDto = new ArrayList<>();
		User user;
		if (!pageTransactions.getContent().isEmpty()) {
			for (Transaction t : pageTransactions.getContent()) {
				if (t.getPayer() == uid) {
					// Viewer is payer
					user = userService.findUserByUid(t.getPaid());
					transactionsDto.add(new TransactionDto(t.getPaid(), user.getFirstName(), user.getLastName(),
							t.getDate(), t.getPrice() * (-1), t.getLabel()));
				} else {
					// Viewer is paye
					user = userService.findUserByUid(t.getPayer());
					transactionsDto.add(new TransactionDto(t.getPayer(), user.getFirstName(), user.getLastName(),
							t.getDate(), t.getPrice(), t.getLabel()));
				}
			}
		}
		return new PageImpl<>(transactionsDto, PageRequest.of(pagingSort.getPageNumber(), pagingSort.getPageSize()),
				transactionRepository.countByPayerOrPaid(uid, uid));
	}
}
