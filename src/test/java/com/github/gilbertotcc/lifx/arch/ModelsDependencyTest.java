package com.github.gilbertotcc.lifx.arch;

import static com.tngtech.archunit.core.domain.Dependency.Predicates.dependencyTarget;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.conditions.ArchConditions.onlyHaveDependenciesWhere;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.ClassesThat;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesConjunction;

@AnalyzeClasses(packages = "com.github.gilbertotcc.lifx")
class ModelsDependencyTest {

  private static final String MODELS_PACKAGE = "..lifx.models";

  private static ClassesThat<GivenClassesConjunction> notTestClassesThat() {
    return classes().that()
      .haveNameNotMatching(".*(Test|Spec).*")
      .and();
  }

  // ArchUnit 1.x also reports annotations (e.g. Jackson, lombok.Generated) as dependencies: keep checking code only.
  // Annotation dependencies are recognizable only by their description.
  private static DescribedPredicate<Dependency> annotationDependency() {
    return DescribedPredicate.describe("come from annotations",
      dependency -> dependency.getDescription().contains(" is annotated with ")
        || dependency.getDescription().contains(" has annotation member of type "));
  }

  @ArchTest
  private static ArchRule MODELS_SHOULD_NOT_DEPEND_ON_OTHER_CLASSES =
    notTestClassesThat()
      .resideInAPackage(MODELS_PACKAGE)
      .should(onlyHaveDependenciesWhere(annotationDependency().or(dependencyTarget(resideInAnyPackage(
        MODELS_PACKAGE,
        "java.lang", "java.time", "java.util..", "io.vavr..", "java.io",
        "" /* Avoid issue with enums. See: https://github.com/TNG/ArchUnit/issues/81#issuecomment-399688049 */
      )))));
}
