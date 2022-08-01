package five.agency.droolsdemo.rest;

import five.agency.droolsdemo.model.Address;
import five.agency.droolsdemo.model.Order;
import five.agency.droolsdemo.model.Person;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/offer")
@RequiredArgsConstructor
public class OfferController {
    private final KieSession kieSession;

    @PostMapping("/discount")
    public Order getDiscountForOrder(@RequestBody Order order) {
        kieSession.setGlobal("globalVariable", order);
        kieSession.fireAllRules();
        return order;
    }
}
