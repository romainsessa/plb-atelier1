package fr.example.mono.controller;

import fr.example.mono.configuration.SecurityConfiguration;
import fr.example.mono.dto.FriendDto;
import fr.example.mono.dto.TransactionDto;
import fr.example.mono.model.Transaction;
import fr.example.mono.service.TransactionService;
import fr.example.mono.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static fr.example.mono.service.TransactionService.getSortDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;


    @GetMapping("/transfer")
    public String getPage(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "3") int size,
                          @RequestParam(defaultValue = "date,desc") String[] sort) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        Transaction transaction = new Transaction();
        List<FriendDto> friendList = userService.getFriendDtoList(email);
        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        Page<TransactionDto> pageTransactionsDto;
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        pageTransactionsDto = transactionService.getPage(email, pagingSort);
        int totalPages = pageTransactionsDto.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("transactions", pageTransactionsDto);
        model.addAttribute("transaction", transaction);
        model.addAttribute("friendList", friendList);
        return "/transfer";
    }

    @PostMapping("/addTransaction")
    public String addTransfer(@ModelAttribute("transaction") Transaction transaction, BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        if (transaction.getPrice() < 2) {
            result.rejectValue("price", "error.price",
                    "You must send more than 2€.");
            return "redirect:/transfer?error";
        }
        try {
            transactionService.addTransaction(email, transaction.getPaid(), transaction.getLabel(), transaction.getPrice());
        } catch (IllegalArgumentException e) {
            result.rejectValue("price", "error.price", e.getMessage());
            return "redirect:/transfer?error";
        }
        return "redirect:/transfer?success";
    }
}
