package five.agency.droolsdemo.service;

import five.agency.droolsdemo.model.DroolRule;
import five.agency.droolsdemo.util.DroolRuleFileManager;
import five.agency.droolsdemo.util.KieFileSystemUtil;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DroolRuleServiceTest {

    @Autowired
    private DroolRuleService droolRuleService;

    @Autowired
    private DroolRuleFileManager droolRuleFileManager;

    @Autowired
    private KieSession kieSession;

    @Autowired
    private KieFileSystemUtil kieFileSystemUtil;

    @Test
    public void createOneRuleFile() throws Exception {
        droolRuleService.createNRandomRules(1);
        assertThat(droolRuleFileManager.getDroolRules().size()).isEqualTo(1);

        droolRuleFileManager.deleteAllGeneratedFiles();
        assertThat(new File(System.getProperty("user.dir") + "/rules").listFiles().length).isEqualTo(0);
    }

    @Test
    public void create1000RuleFiles() throws Exception {
        droolRuleService.createNRandomRules(1000);
        assertThat(droolRuleFileManager.getDroolRules().size()).isEqualTo(1000);

        droolRuleFileManager.deleteAllGeneratedFiles();
        assertThat(new File(System.getProperty("user.dir") + "/rules").listFiles().length).isEqualTo(0);
    }

    @Test
    public void insert10000FactsInSession() throws Exception {
        droolRuleService.createNRandomRules(1);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        for (int i = 0; i < 10000; i++) {
            kieSession.insert("" + i);
        }

        kieSession.fireAllRules();
        droolRuleFileManager.deleteAllGeneratedFiles();
    }

    @Test
    public void insert100000FactsInSession() throws Exception {
        droolRuleService.createNRandomRules(1);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        for (int i = 0; i < 100000; i++) {
            kieSession.insert("" + i);
        }

        kieSession.fireAllRules();
        droolRuleFileManager.deleteAllGeneratedFiles();
    }

    @Test
    public void insert1000000FactsInSession() throws Exception {
        droolRuleService.createNRandomRules(1);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        for (int i = 0; i < 1000000; i++) {
            kieSession.insert("" + i);
        }

        kieSession.fireAllRules();
        droolRuleFileManager.deleteAllGeneratedFiles();
    }

    @Test
    public void insert10000000FactsInSession() throws Exception {
        droolRuleService.createNRandomRules(1);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        for (int i = 0; i < 10000000; i++) {
            kieSession.insert("" + i);
        }

        kieSession.fireAllRules();
        droolRuleFileManager.deleteAllGeneratedFiles();
    }

    @Test
    public void inserting100050RulesInSession() throws Exception {
        droolRuleService.createNRandomRules(100050);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        kieSession.insert("BOK");
        kieSession.fireAllRules();

        droolRuleFileManager.deleteAllGeneratedFiles();
        assertThat(new File(System.getProperty("user.dir") + "/rules").listFiles().length).isEqualTo(0);
    }

    @Test
    public void inserting100000RulesInSession() throws Exception {
        droolRuleService.createNRandomRules(100000);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        kieSession.insert("BOK");
        kieSession.fireAllRules();

        droolRuleFileManager.deleteAllGeneratedFiles();
        assertThat(new File(System.getProperty("user.dir") + "/rules").listFiles().length).isEqualTo(0);
    }

    @Test
    public void inserting10000RulesInSession() throws Exception {
        droolRuleService.createNRandomRules(10000);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        kieSession.insert("BOK");
        kieSession.fireAllRules();

        droolRuleFileManager.deleteAllGeneratedFiles();
        assertThat(new File(System.getProperty("user.dir") + "/rules").listFiles().length).isEqualTo(0);
    }

    @Test
    public void inserting1000RulesInSession() throws Exception {
        droolRuleService.createNRandomRules(1000);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        kieSession.insert("BOK");
        kieSession.fireAllRules();

        droolRuleFileManager.deleteAllGeneratedFiles();
        assertThat(new File(System.getProperty("user.dir") + "/rules").listFiles().length).isEqualTo(0);
    }

    @Test
    public void insert1000FactsAnd10000RulesInSession() throws Exception {
        droolRuleService.createNRandomRules(10000);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        for(int i = 0; i < 1000; i++) {
            kieSession.insert("" + i);
        }

        kieSession.fireAllRules();
        droolRuleFileManager.deleteAllGeneratedFiles();
    }

    @Test
    public void insert10000FactsAnd10000RulesInSession() throws Exception {
        droolRuleService.createNRandomRules(10000);
        kieFileSystemUtil.addBatchOfRules(
                droolRuleFileManager.getDroolRules()
                        .stream()
                        .map(DroolRule::getRulePath)
                        .collect(Collectors.toList()));
        for(int i = 0; i < 10000; i++) {
            kieSession.insert("" + i);
        }

        kieSession.fireAllRules();
        droolRuleFileManager.deleteAllGeneratedFiles();
    }
}
