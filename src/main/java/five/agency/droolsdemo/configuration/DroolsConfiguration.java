package five.agency.droolsdemo.configuration;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DroolsConfiguration {
    private final KieServices kieServices = KieServices.Factory.get();

    private KnowledgeBuilder knowledgeBuilder;

    @Bean
    public KieServices getKieServices() {
        return kieServices;
    }

    @Bean
    public KieContainer getKieContainer() throws IOException {
        getKieRepository();
        knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        KieBuilder kieBuilder = kieServices.newKieBuilder(getKieFileSystem());
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer;
    }

    @Bean
    public KieSession getKieSession() throws IOException {
        return getKieContainer().newKieSession();
    }

    @Bean
    public KnowledgeBuilder getKnowledgeBuilder() {
        return knowledgeBuilder;
    }

    @Bean
    public KieFileSystem getKieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        //kieFileSystem.write(ResourceFactory.newClassPathResource("rules/offer.drl"));
        //kieFileSystem.write(ResourceFactory.newClassPathResource("rules/person.drl"));
        //kieFileSystem.write(ResourceFactory.newClassPathResource("rules/global.drl"));
        //kieFileSystem.write(ResourceFactory.newClassPathResource("rules/stringrule.drl"));
        return kieFileSystem;
    }

    private void getKieRepository() {
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(new KieModule() {
            @Override
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });
    }

}
