package five.agency.droolsdemo.model;

import lombok.Data;

import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class DroolRule {

    private String packageName;
    private String ruleName;
    private String ruleContent;

    public Path getRulePath() {
        return Paths.get(System.getProperty("user.dir") + "/" + packageName + "/" + ruleName + ".drl");
    }

    @Override
    public String toString() {
        return "package " + packageName + ";\n" +
                "dialect  \"mvel\";\n" +
                "\n" +
                "rule \"" + ruleName + "\"\n" +
                ruleContent +
                "\nend";
    }
}
