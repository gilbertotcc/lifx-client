package com.github.gilbertotcc.lifx.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesThat;
import org.junit.runner.RunWith;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.github.gilbertotcc.lifx")
public class ModelsDependencyTest {

    private static final String MODELS_PACKAGE = "..lifx.models";

    private static GivenClassesThat notTestClassesThat() {
        return classes().that()
                .haveNameNotMatching(".*Test.*")
                .and();
    }

    @ArchTest
    private static ArchRule MODELS_SHOULD_NOT_DEPEND_ON_OTHER_CLASSES =
            notTestClassesThat()
                    .resideInAPackage(MODELS_PACKAGE)
                    .should()
                    .onlyDependOnClassesThat()
                    .resideInAnyPackage(
                            MODELS_PACKAGE,
                            "java.lang", "java.time", "java.util..",
                            "" /* Avoid issue with enums. See: https://github.com/TNG/ArchUnit/issues/81#issuecomment-399688049 */
                    );
}
