package com.github.gilbertotcc.lifx.arch;

import static com.tngtech.archunit.core.domain.Dependency.Predicates.dependencyTarget;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.lang.conditions.ArchConditions.onlyHaveDependenciesWhere;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.github.gilbertotcc.lifx", importOptions = ImportOption.DoNotIncludeTests.class)
class VavrDependencyTest {

  private static final String VAVR_PACKAGE = "io.vavr..";

  // Allowed vavr types, including their nested classes (e.g. Either$Left, Validation$Builder).
  private static final String ALLOWED_VAVR_CLASSES = "io\\.vavr\\..*\\.(Either|Seq|Try|Validation)(\\$.*)?";

  // Applies to every class, but only constrains dependencies on vavr: classes that don't use vavr pass trivially.
  @ArchTest
  static final ArchRule ALL_CLASSES_SHOULD_ACCESS_ALLOWED_VAVR_CLASSES =
    classes()
      .should(onlyHaveDependenciesWhere(
        dependencyTarget(resideInAPackage(VAVR_PACKAGE).negate())
          .or(dependencyTarget(nameMatching(ALLOWED_VAVR_CLASSES)))));
}
