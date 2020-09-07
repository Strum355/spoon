/**
 * Copyright (C) 2006-2018 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.compiler.jdt;

import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Test;
import spoon.Launcher;
import spoon.MavenLauncher;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class JDTBasedSpoonCompilerTest {

	@Test
	public void testOrderCompilationUnits() {
		final Launcher launcher = new Launcher();
		launcher.addInputResource("./src/main/java");
		JDTBasedSpoonCompiler spoonCompiler = (JDTBasedSpoonCompiler) launcher.getModelBuilder();

		CompilationUnitDeclaration[] compilationUnitDeclarations = spoonCompiler.buildUnits(null, spoonCompiler.sources, spoonCompiler.getSourceClasspath(), "");

		List<CompilationUnitDeclaration> compilationUnitDeclarations1 = spoonCompiler.sortCompilationUnits(compilationUnitDeclarations);

		if (System.getenv("SPOON_SEED_CU_COMPARATOR") == null || "0".equals(System.getenv("SPOON_SEED_CU_COMPARATOR"))) {
			for (int i = 1; i < compilationUnitDeclarations1.size(); i++) {
				CompilationUnitDeclaration cu0 = compilationUnitDeclarations1.get(i - 1);
				CompilationUnitDeclaration cu1 = compilationUnitDeclarations1.get(i);

				String filenameCu0 = new String(cu0.getFileName());
				String filenameCu1 = new String(cu1.getFileName());

				assertTrue("There is a sort error: " + filenameCu0 + " should be before " + filenameCu1, filenameCu0.compareTo(filenameCu1) < 0);
			}
		}
	}

	@Test
	public void typeBindingNullNetty() {
		final Launcher spoon = new MavenLauncher("./src/test/resources/nulltypebinding/netty", MavenLauncher.SOURCE_TYPE.ALL_SOURCE);
		spoon.getEnvironment().setIgnoreDuplicateDeclarations(true);
        spoon.getEnvironment().setComplianceLevel(9);
        spoon.buildModel();
	}

	@Test
	public void typeBindingNullSpringboot() {
		final Launcher spoon = new MavenLauncher("./src/test/resources/nulltypebinding/spring-boot", MavenLauncher.SOURCE_TYPE.ALL_SOURCE);
		spoon.getEnvironment().setIgnoreDuplicateDeclarations(true);
        spoon.getEnvironment().setComplianceLevel(9);
        spoon.buildModel();
	}
}
