package org.sid.ebankingbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter() {
        BankAccount bankAccount =
                bankAccountRepository.findById("0b36be78-8d5d-446b-9f20-37eadc9d3c3b")
                        .orElse(null);

        if (bankAccount != null) {
            log.info("*****************************");
            log.info("Account ID       : {}", bankAccount.getId());
            log.info("Balance          : {}", bankAccount.getBalance());
            log.info("Status           : {}", bankAccount.getStatus());
            log.info("Created At       : {}", bankAccount.getCreatedAt());
            log.info("Customer Name    : {}", bankAccount.getCustomer().getName());
            log.info("Account Type     : {}", bankAccount.getClass().getSimpleName());

            if (bankAccount instanceof CurrentAccount currentAccount) {
                log.info("OverDraft        : {}", currentAccount.getOverDraft());
            } else if (bankAccount instanceof SavingAccount savingAccount) {
                log.info("Interest Rate    : {}", savingAccount.getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(op ->
                    log.info("Operation => Type: {}, Date: {}, Amount: {}",
                            op.getType(),
                            op.getOperationDate(),
                            op.getAmount())
            );
        } else {
            log.warn("Bank account not found");
        }
    }
}
