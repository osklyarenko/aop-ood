package net.chrisrichardson.bankingExample.domain;

import static org.hamcrest.Matchers.instanceOf;
import net.chrisrichardson.bankingExample.infrastructure.AuditingManager;
import net.chrisrichardson.bankingExample.infrastructure.BankingSecurityManagerWrapper;
import net.chrisrichardson.bankingExample.infrastructure.TransactionManager;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.lib.legacy.ClassImposteriser;
public class AccountServiceProceduralImplMockTests extends MockObjectTestCase {
  
  private AccountDao accountDao;
  private BankingTransactionDao bankingTransactionDao;
  private TransactionManager transactionManager;
  private AuditingManager auditingManager;
  private BankingSecurityManagerWrapper bankingSecurityWrapper;
  private AccountServiceProceduralImpl service;
  private Account fromAccount;
  private Account toAccount;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    
    setImposteriser(ClassImposteriser.INSTANCE);
    
    accountDao = mock(AccountDao.class);
    bankingTransactionDao = mock(BankingTransactionDao.class);
    transactionManager = mock(TransactionManager.class);
    auditingManager = mock(AuditingManager.class);
    bankingSecurityWrapper = mock(BankingSecurityManagerWrapper.class);
    
    fromAccount = AccountMother.makeNoOverdraftAllowedAccount(30);
    toAccount = AccountMother.makeNoOverdraftAllowedAccount(70);
    
    service = new AccountServiceProceduralImpl(accountDao, bankingTransactionDao, transactionManager, auditingManager, bankingSecurityWrapper);
      
  }
  
  public void testTransfer_normal()  {
    checking(new Expectations() {{
      one(bankingSecurityWrapper).verifyCallerAuthorized(AccountService.class, "transfer");
      one(transactionManager).begin();
      one(auditingManager).audit(AccountService.class, "transfer", new Object[]{"fromAccountId", "toAccountId", 15.0});
      one(accountDao).findAccount("fromAccountId"); will(returnValue(fromAccount));
      one(accountDao).findAccount("toAccountId"); will(returnValue(toAccount));
      one(accountDao).saveAccount(fromAccount);
      one(accountDao).saveAccount(toAccount);
      one(bankingTransactionDao).addTransaction(with(instanceOf(TransferTransaction.class)));
      one(transactionManager).commit();
      one(transactionManager).rollbackIfNecessary();
    }}
    );

    TransferTransaction result = (TransferTransaction) service.transfer("fromAccountId", "toAccountId", 15.0);

    assertEquals(15.0, fromAccount.getBalance());
    assertEquals(85.0, toAccount.getBalance());
    
    assertNotNull(result);
    assertSame(fromAccount, result.getFromAccount());
    assertSame(toAccount, result.getToAccount());
    assertEquals(15.0, result.getAmount());
    
    verify();
    
  }

}
