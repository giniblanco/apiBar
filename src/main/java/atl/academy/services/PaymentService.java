package atl.academy.services;

import atl.academy.models.PaymentEntity;
import atl.academy.repositories.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    public PaymentEntity save(PaymentEntity paymentEntity){
        if(!(paymentEntity.getDatePay() == LocalDate.now())){
            paymentEntity.setDatePay(LocalDate.now());
        }
        paymentEntity.setStatus(false);
        return paymentRepository.save(paymentEntity);
    }

    public PaymentEntity update(Long id, PaymentEntity paymentEntity){
        if(!(paymentEntity.isStatus() == false)){
            paymentEntity.setStatus(true);
        }

        PaymentEntity existingPayment = paymentRepository.findById(id).orElse(null);

        if(existingPayment != null){
            return paymentRepository.save(paymentEntity);
        }

        return null;
    }

    public List<PaymentEntity> getAll(){
        return paymentRepository.findAll();
    }

    public PaymentEntity getForId(Long id){
        return paymentRepository.findById(id).orElse(null);
    }
}
