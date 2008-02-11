package net.chrisrichardson.bankingExample.domain;

import static org.hamcrest.Matchers.instanceOf;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.lib.legacy.ClassImposteriser;

public class AccountServiceProceduralImplMockTests extends
    MockObjectTestCase {

  private AccountDao accountDao;
  private BankingTransactionDao bankingTransactionDao;
  private AccountServiceProceduralImpl service;
  private Account fromAccount;
  private Account toAccount;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    setImposteriser(ClassImposteriser.INSTANCE);

    accountDao = mock(AccountDao.class);
    bankingTransactionDao = mock(BankingTransactionDao.class);

    fromAccount = AccountMother.makeNoOverdraftAllowedAccount(30);
    toAccount = AccountMother.makeNoOverdraftAllowedAccount(70);

    service = new AccountServiceProceduralImpl(accountDao,
        bankingTransactionDao);

  }

  public void testTransfer_normal()  {
    checking(new Expectations() {
      {
        one(accountDao).findAccountWithOverdraftPolicy("fromAccountId");
        will(returnValue(fromAccount));
        one(accountDao).findAccount("toAccountId");
        will(returnValue(toAccount));
        one(bankingTransactionDao).addTransaction(
            with(instanceOf(TransferTransaction.class)));
      }
    });

    TransferTransaction result = (TransferTransaction) service.transfer(
        "fromAccountId", "toAccountId", 15.0);

    assertEquals(15.0, fromAccount.getBalance());
    assertEquals(85.0, toAccount.getBalance());

    assertNotNull(result);
    assertSame(fromAccount, result.getFromAccount());
    assertSame(toAccount, result.getToAccount());
    assertEquals(15.0, result.getAmount());

    verify();

  }

}
