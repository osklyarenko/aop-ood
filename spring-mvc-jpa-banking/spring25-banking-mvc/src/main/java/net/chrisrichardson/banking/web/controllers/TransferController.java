package net.chrisrichardson.banking.web.controllers;

import java.util.List;

import net.chrisrichardson.bankingExample.domain.Account;
import net.chrisrichardson.bankingExample.domain.MoneyTransferException;
import net.chrisrichardson.bankingExample.domain.MoneyTransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/transfer.html")
public class TransferController {

	@Autowired
	private MoneyTransferService moneyTransferService;

	@ModelAttribute("command")
	public TransferCommand getCommand() {
		return new TransferCommand();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(ModelMap model) {
		List<Account> accounts = moneyTransferService.findAccounts();
		model.addAttribute("accounts", accounts);
		return "transferForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("command")
	TransferCommand command, BindingResult result) {
		try {
      moneyTransferService.transfer(command.getFromAccount(), command.getToAccount(), command.getAmount());
    } catch (MoneyTransferException e) {
      // TODO handle an overdraft
      e.printStackTrace();
    }
		return "redirect:/transfer.html";
	}
}
