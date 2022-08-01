package five.agency.droolsdemo.service;

import five.agency.droolsdemo.model.DroolRule;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface DroolRuleService {

    public DroolRule createDroolRule(String ruleName);
    public void createNRandomRules(int numberOfRandomRules) throws Exception;
}
