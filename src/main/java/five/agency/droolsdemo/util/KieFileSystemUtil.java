package five.agency.droolsdemo.util;

import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KieFileSystemUtil {

    @Autowired
    private KieFileSystem kieFileSystem;
    @Autowired
    private KieServices kieServices;
    @Autowired
    private KieContainer kieContainer;

    public void addNewRuleToSession(String newRuleName) {
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/" + newRuleName));
        updateKieContainerVersion();
    }

    public void replaceRuleInSession(String nameOfTheRuleToBeReplaced, String replaceRuleName) {
        removeRuleFromSession(nameOfTheRuleToBeReplaced);
        addNewRuleToSession(replaceRuleName);
    }

    private void removeRuleFromSession(String ruleToBeDeleted) {
        kieContainer.getKieBase().removeRule("rules", ruleToBeDeleted);
        updateKieContainerVersion();
    }

    private void updateKieContainerVersion() {
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        kieContainer.updateToVersion(kieBuilder.getKieModule().getReleaseId());
    }

}
