package ee.mihkel.veebipood.service;

import ee.mihkel.veebipood.entity.Order;
import ee.mihkel.veebipood.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class CronService {

    // * - sekund
    // * * - minut
    // * * * - tund
    // * * * * - kuupäev 1-31
    // * * * * * - kuu 1-12
    // * * * * * * - nädalapäev 0,7 P   1-6 E-L

//    @Scheduled(cron = "* * * * * *")
//    public void logEverySecond() {
//        Date current = new Date();
//        System.out.println(current.getMinutes() + ":" + current.getSeconds());
//    }

//    @Scheduled(cron = "0 * 9-17 * * 1-5")
//    public void logEveryMinuteOnWorkHours() {
//        Date current = new Date();
//        System.out.println(current.getMinutes() + ":" + current.getSeconds());
//    }
//
//    @Autowired
//    OrderRepository orderRepository;
//
//    @Autowired
//    EmailService emailService;
//
//    @Scheduled(cron = "0 * 8-18 * * 1-5")
//    public void logEveryHourOnWorkHours() {
//        Date startDate = new GregorianCalendar(Year.now().getValue(), Calendar.MARCH, 20).getTime();
//        Date current = new Date();
//
//        List<Order> orders = orderRepository.findByCreatedBetween(startDate, current);
//        StringBuilder content = new StringBuilder();
//        content.append("Valmis tellimused: ");
//        for (Order o: orders) {
//            content.append(o.getId()).append(" ");
//        }
//        emailService.sendEmail("Sinu tellimused on valmis", String.valueOf(content));
//    }


}
