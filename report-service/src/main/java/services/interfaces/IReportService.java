package services.interfaces;

import dto.ExampleObjDTO;
import dto.TransactionDTO;
import messaging.EventSender;

public interface IReportService {
    String hello();
    ExampleObjDTO readExample();
    void requestAllTransactions(EventSender eventSender) throws Exception;

    TransactionDTO[] displayAllTransactions(TransactionDTO[] transactions);
}
