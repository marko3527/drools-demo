package five.agency.droolsdemo;

import five.agency.droolsdemo.model.Address;
import five.agency.droolsdemo.model.Person;
import five.agency.droolsdemo.util.KieFileSystemUtil;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.core.base.RuleNameMatchesAgendaFilter;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DroolsDemoApplicationTests {

	@Autowired
	private KieSession kieSession;

	@Autowired
	private KieFileSystemUtil kieFileSystemUtil;

	private Address croatianAddress = new Address("Zagreb", "test", "test", "Croatia");
	private Address frenchAddress = new Address("Paris", "test", "test", "France");
	private Person personWithCroatianAddress = new Person("Marko", "Grozaj", croatianAddress, 1);
	private Person personWithFrenchAddress = new Person("Marko", "Grozaj", frenchAddress, 1);

	@Test
	public void shouldCount4PersonLivingInCroatia() {
		List<FactHandle> factHandles = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(i+1)));
		}
		for(int i = 0; i < 2; i++) {
			factHandles.add(kieSession.insert(personWithFrenchAddress));
		}

		QueryResults results = kieSession.getQueryResults("person with address in Croatia");
		runSpecificRule("oldest Croatian");
		assertThat(results.size()).isEqualTo(4);
		retractHandles(factHandles);
	}

	@Test
	public void shouldReturnOldestPersonLivingInCroatia() {
		List<FactHandle> factHandles = new ArrayList<>();
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(23)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(25)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(97)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(56)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(58)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(12)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(11)));

		kieSession.fireAllRules();
		QueryResults results = kieSession.getQueryResults("oldest person in Croatia");
		assertThat(results.size()).isEqualTo(1);
		assertThat(results.toList("$age").get(0)).isEqualTo(97);
		retractHandles(factHandles);
	}

	@Test
	public void shouldFireRuleForAddingNewPersonFirst() {
		List<Person> persons = new ArrayList<>();
		kieSession.setGlobal("globalList", persons);

		kieSession.getAgenda().getAgendaGroup("global").setFocus();
		kieSession.fireAllRules();
		assertThat(persons.size()).isEqualTo(1);
		assertThat(persons.get(0).getAddress().getCountry()).isEqualTo("USA");
		assertThat(persons.get(0).getAge()).isEqualTo(12);
	}

	@Test
	public void printAllCombinations() {
		List<FactHandle> factHandles = new ArrayList<>();
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(23)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(25)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(97)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(56)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(58)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(12)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(11)));

		kieSession.getAgenda().getAgendaGroup("print all combinations").setFocus();
		runSpecificRule("print age");
		retractHandles(factHandles);
	}

	@Test
	public void testPropertyReactivity() {
		List<FactHandle> factHandles = new ArrayList<>();
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(23)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(25)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(97)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(56)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(58)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(12)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(11)));

		kieSession.getAgenda().getAgendaGroup("reactivity").setFocus();
		runSpecificRule("property reactivity");
		retractHandles(factHandles);
	}

	@Test
	public void shouldFireJustOneRule() {
		List<FactHandle> factHandles = new ArrayList<>();
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(23)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(25)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(97)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(56)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(58)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(12)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(11)));

		runSpecificRule("oldest croatian");
		retractHandles(factHandles);
	}

	@Test
	public void testingFiringRulesAfterAddingNewFact() {
		List<FactHandle> factHandles = new ArrayList<>();
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(23)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(25)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(97)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(56)));
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(58)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(12)));
		factHandles.add(kieSession.insert(personWithFrenchAddress.changeAge(11)));

		kieSession.getAgenda().getAgendaGroup("print all combinations").setFocus();
		kieSession.fireAllRules(new RuleNameMatchesAgendaFilter("print age"));

		System.out.println("\nAdding new FACT!----------------------------");
		factHandles.add(kieSession.insert(personWithCroatianAddress.changeAge(7)));
		kieSession.getAgenda().getAgendaGroup("print all combinations").setFocus();
		runSpecificRule("print age");

		retractHandles(factHandles);
	}

	@Test
	public void addingNewRuleToKnowledgeBase() {
		createNewRuleAndAddItToExistingSession();
	}

	private void createNewRuleAndAddItToExistingSession() {
		kieSession.insert("String prije dodavanja novog pravila");
		kieSession.fireAllRules();

		kieFileSystemUtil.addNewRuleToSession("newrule.drl");

		kieSession.fireAllRules();
		System.out.println("STANJE NAKON PROMJENE PRAVILA----------------------------------------");

		kieFileSystemUtil.replaceRuleInSession("always true", "ruleafterdelete.drl");
		kieSession.insert("Novi string objekt nad kojim bi se trebala okinuta oba pravila");
		kieSession.fireAllRules();
	}

	private void runSpecificRule(String ruleName) {
		kieSession.fireAllRules(new RuleNameMatchesAgendaFilter(ruleName));
	}

	private void retractHandles(List<FactHandle> factHandles) {
		factHandles.forEach(handle -> kieSession.delete(handle));
	}

}
