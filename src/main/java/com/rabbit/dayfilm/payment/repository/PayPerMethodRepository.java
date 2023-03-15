package com.rabbit.dayfilm.payment.repository;

import com.rabbit.dayfilm.payment.entity.CancelPayment;
import com.rabbit.dayfilm.payment.entity.PayInformation;
import com.rabbit.dayfilm.payment.entity.VirtualAccountPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Repository
public class PayPerMethodRepository<T extends PayInformation> {
    @PersistenceContext
    private final EntityManager em;

    public void save(T object) {
        em.persist(object);
    }

    public void saveCancelPayment(CancelPayment object) {
        em.persist(object);
    }

    public VirtualAccountPayment findVirtual(String orderId) {
        return em.find(VirtualAccountPayment.class, orderId);
    }
}
