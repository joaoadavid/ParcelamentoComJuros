package services;

import entities.Contract;
import entities.Installment;
import java.time.LocalDate;

public class ContractService {

	private OnlinePaymentService onlynePaymentService;

	public ContractService(OnlinePaymentService onlynePaymentService) {
		this.onlynePaymentService = onlynePaymentService;
	}

	public void processContract(Contract contract, int months) {
		
		double basicQuota = contract.getTotalValue() / months;
		
		for (int i=1; i<=months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);
			
			double interest = onlynePaymentService.interest(basicQuota, i); 
			double fee = onlynePaymentService.paymentFee(basicQuota + interest);
			double quota = basicQuota + interest + fee;
			
			contract.getInstallment().add(new Installment(dueDate, quota));
			
		}
	}

}
