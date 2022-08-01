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

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class KieFileSystemUtil {

    @Autowired
    private KieFileSystem kieFileSystem;
    @Autowired
    private KieServices kieServices;
    @Autowired
    private KieContainer kieContainer;

    public void addBatchOfRules(List<Path> rulePaths) {
        rulePaths.forEach(rulePath -> kieFileSystem.write(ResourceFactory.newFileResource(rulePath.toFile())));
        updateKieContainerVersion();
    }

    public void addNewRuleToSession(Path rulePath) {
        kieFileSystem.write(ResourceFactory.newFileResource(rulePath.toFile()));
        updateKieContainerVersion();
    }

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
        if(kieBuilder.buildAll().getResults().hasMessages()) {
            System.out.println("NISAM USPIO BUILDAT");
        }
        kieBuilder.buildAll();
        kieContainer.updateToVersion(kieBuilder.getKieModule().getReleaseId());
    }

}
