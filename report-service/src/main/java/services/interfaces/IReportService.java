package services.interfaces;

import dto.ExampleObjDTO;
import dto.TransactionDTO;
import messaging.Event;

public interface IReportService {
    String hello();
    ExampleObjDTO readExample();
    Event requestAllTransactions() throws Exception;
    TransactionDTO[] displayAllTransactions(TransactionDTO[] transactions);
}
