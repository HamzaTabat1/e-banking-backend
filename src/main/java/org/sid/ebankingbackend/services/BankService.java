package org.sid.ebankingbackend.services;

import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("1c1591c0-1a42-4a99-8def-0a53b0ed7d7d").orElse(null);
        if(bankAccount != null) {
            System.out.println("*****************************");
            System.out.println(bankAccount.getId());
            System.out.println(String.valueOf(bankAccount.getBalance()));
            System.out.println(String.valueOf(bankAccount.getStatus()));
            System.out.println(String.valueOf(bankAccount.getCreatedAt()));
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Overdraft: " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Interest rate: " + ((SavingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(accountOperation -> {
                System.out.println(accountOperation.getType() + "\t" + accountOperation.getAmount() + "\t" + accountOperation.getOperationDate());
            });
        }
    }
}
