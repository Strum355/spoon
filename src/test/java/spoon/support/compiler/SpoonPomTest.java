package spoon.support.compiler;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Test;
import spoon.MavenLauncher;
import spoon.support.StandardEnvironment;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class SpoonPomTest {

	@Test
	public void getSourceVersion() throws IOException, XmlPullParserException {
		checkVersion("src/test/resources/maven-launcher/null-build/pom.xml", 11);
		checkVersion("src/test/resources/maven-launcher/java-11/pom.xml", 11);
		checkVersion("src/test/resources/maven-launcher/pac4j/pom.xml", 8);
		checkVersion("src/test/resources/maven-launcher/source-directory/pom.xml", 8);
		checkVersion("src/test/resources/maven-launcher/very-simple/pom.xml", 8);
		checkVersion("pom.xml", 8);

	}

	public void checkVersion(String path, int expected) throws IOException, XmlPullParserException {
		SpoonPom pomModel = new SpoonPom(path, null, MavenLauncher.SOURCE_TYPE.APP_SOURCE, new StandardEnvironment());
		int version = pomModel.getSourceVersion();

		//contract: Java version is read accurately from pom and does not trigger exceptions
		assertEquals(expected, version);
	}

	@Test
	public void getModuleNames() throws IOException, XmlPullParserException {
		String[] expected = {"always"};
		checkProfilesModules("src/test/resources/maven-launcher/profiles", expected, Pattern.compile("^$"));
		String[] expected1 = {"profile-only", "always"};
		checkProfilesModules("src/test/resources/maven-launcher/profiles", expected1, Pattern.compile(".+"));
	}

	public void checkProfilesModules(String path, String[] expected, Pattern profileFilter) throws IOException, XmlPullParserException {
		SpoonPom pomModel = new SpoonPom(path, null, MavenLauncher.SOURCE_TYPE.APP_SOURCE, new StandardEnvironment(), profileFilter);
		List<SpoonPom> modules = pomModel.getModules();

		assertEquals(expected.length, modules.size());

		// contract: modules declared in profiles that don't match the profileFilter are not included
		for (int i = 0; i < modules.size(); i++) {
			assertEquals(expected[i], modules.get(i).getName());
		}
	}
}