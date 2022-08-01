package five.agency.droolsdemo.util;

import five.agency.droolsdemo.model.DroolRule;
import lombok.Data;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class DroolRuleFileManager {

    private List<DroolRule> droolRules = new ArrayList<>();

    public void saveRuleFile(DroolRule droolRule) throws IOException {
        droolRules.add(droolRule);
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir + "/" + droolRule.getPackageName());
        if(!file.exists()) {
            file.mkdir();
        }
        FileWriter fileWriter = new FileWriter(userDir + "/" + droolRule.getPackageName() + "/" + droolRule.getRuleName() + ".drl");
        fileWriter.write(droolRule.toString());
        fileWriter.close();
    }


    public void deleteAllGeneratedFiles() {
        droolRules.forEach(droolRule -> {
            File file = new File(System.getProperty("user.dir") + "/" + droolRule.getPackageName() + "/" + droolRule.getRuleName() + ".drl");
            try{
                file.delete();
            } catch (Exception ex) {
                System.out.println("Couldn't delete file " + droolRule.getRuleName());
            }
        });
    }
}
