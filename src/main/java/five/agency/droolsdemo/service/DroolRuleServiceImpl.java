package five.agency.droolsdemo.service;

import five.agency.droolsdemo.model.DroolRule;
import five.agency.droolsdemo.util.DroolRuleFileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DroolRuleServiceImpl implements DroolRuleService {

    private final DroolRuleFileManager droolRuleFileManager;

    private String ruleContent =
            "when\n" +
                    "   $s: String()\n" +
                    "then\n" +
                    "   System.out.println($s);";

    @Override
    public DroolRule createDroolRule(String ruleName) {
        DroolRule droolRule = new DroolRule();
        droolRule.setRuleName(ruleName);
        droolRule.setRuleContent(ruleContent);
        droolRule.setPackageName("rules");
        return droolRule;
    }

    public void createNRandomRules(int numberOfRandomRules) throws Exception {
        List<DroolRule> droolRules = new ArrayList<>();
        for (int i = 0; i < numberOfRandomRules; i++) {
            DroolRule droolRule = createDroolRule(String.valueOf(UUID.randomUUID()));
            droolRules.add(droolRule);
            droolRuleFileManager.saveRuleFile(droolRule);
        }
    }
}
