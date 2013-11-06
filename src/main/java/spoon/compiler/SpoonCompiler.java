/* 
 * Spoon - http://spoon.gforge.inria.fr/
 * Copyright (C) 2006 INRIA Futurs <renaud.pawlak@inria.fr>
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

package spoon.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import spoon.Spoon;
import spoon.processing.FactoryAccessor;
import spoon.reflect.Factory;

/**
 * This interface defines the Spoon Java compiler and code processor.
 * 
 * <p>
 * The Spoon model (see {@link Factory} is built from input sources given as
 * files. Use {@link #build(Factory))} and {@link #build(Factory, List)))} to
 * create the Spoon model. Once the model is built and stored in the factory, it
 * can be processed by using a {@link spoon.processing.ProcessingManager}.
 * </p>
 * 
 * <p>
 * Create an instance of the default implementation of the Spoon compiler by
 * using {@link Spoon#createCompiler()}. For example:
 * </p>
 * 
 * <pre>
 * SpoonCompiler compiler = Spoon.createCompiler();
 * Factory factory = Spoon.createFactory();
 * List&lt;SpoonFile&gt; files = SpoonResourceHelper.files(&quot;myFile.java&quot;);
 * compiler.build(factory, files);
 * </pre>
 * 
 * @see Spoon#createCompiler()
 */
public interface SpoonCompiler extends FactoryAccessor {

	/**
	 * Adds a file/directory to be built. By default, the files could be Java
	 * source files or Jar files. Directories are processed recursively.
	 * 
	 * @param source
	 *            file or directory to add
	 */
	void addInputSource(File source) throws IOException;

	/**
	 * Sets the destination directory for the class files.
	 * 
	 * @param destinationDirectory
	 *            destination directory
	 */
	void setDestinationDirectory(File destinationDirectory) throws IOException;

	/**
	 * Gets the output directory of this compiler.
	 */
	File getDestinationDirectory();

	/**
	 * Sets the output directory for the source files.
	 * 
	 * @param outputDirectory
	 *            output directory
	 */
	void setOutputDirectory(File outputDirectory) throws IOException;

	/**
	 * Gets the output directory of this compiler.
	 */
	File getOutputDirectory();

	/**
	 * Adds a file/directory (as a CtResource) to be built. By default, the
	 * files could be Java source files or Jar files. Directories are processed
	 * recursively.
	 * 
	 * @param source
	 *            file or directory to add
	 */
	void addInputSource(SpoonResource source) throws IOException;

	/**
	 * Gets all the files/directories given as input sources to this builder
	 * (see {@link #addInputSource(File)}).
	 */
	Set<File> getInputSources();

	/**
	 * Adds a file/directory to be used to build templates. By default, the
	 * files should be Java source files or Jar files containing the sources.
	 * Directories are processed recursively. Templates are set apart from the
	 * program to be processed for logical reasons. However, if a template was
	 * needed to be processed, it could be added as an input source.
	 * 
	 * @param source
	 *            file or directory to add
	 */
	void addTemplateSource(File source) throws IOException;

	/**
	 * Adds a file/directory (as a CtResource) to be used to build templates. By
	 * default, the files should be Java source files or Jar files containing
	 * the sources. Directories are processed recursively. Templates are set
	 * apart from the program to be processed for logical reasons. However, if a
	 * template was needed to be processed, it could be added as an input
	 * source.
	 * 
	 * @param source
	 *            file or directory to add
	 */
	void addTemplateSource(SpoonResource source) throws IOException;

	/**
	 * Gets all the files/directories given as template sources to this builder
	 * (see {@link #addTemplateSource(File)}).
	 */
	Set<File> getTemplateSources();

	/**
	 * Builds the program's model with this compiler's factory and stores the
	 * result into this factory. Note that this method should only be used once
	 * on a given factory.
	 * 
	 * @return true if the Java was successfully compiled with the core Java
	 *         compiler, false if some errors were encountered while compiling
	 * 
	 * @exception Exception
	 *                when a building problem occurs
	 */
	boolean build() throws Exception;

	/**
	 * Builds the program's model corresponding to the given files with this
	 * compiler's factory and stores the result into this factory. Note that
	 * this method should only be used once on a given factory.
	 * 
	 * @return true if the Java was successfully compiled with the core Java
	 *         compiler, false if some errors were encountered while compiling
	 * 
	 * @exception Exception
	 *                when a building problem occurs
	 */
	boolean build(List<SpoonFile> files) throws Exception;

	boolean buildTemplates(List<SpoonFile> files) throws Exception;

	/**
	 * Generates the source code associated to the classes stored in this
	 * compiler's factory. The source code is generated in the directory given
	 * by {@link #getOutputDirectory()}.
	 */
	void generateProcessedSourceFiles();

	/**
	 * Generates the bytecode associated to the classes stored in this
	 * compiler's factory. The bytecode is generated in the directory given by
	 * {@link #getDestinationDirectory()}.
	 */
	boolean compile();

	/**
	 * Generates the bytecode by compiling the input sources. The bytecode is
	 * generated in the directory given by {@link #getDestinationDirectory()}.
	 * 
	 * @param addDestinationDirectoryToClasspath
	 *            set this flag to true to automatically add the bytecode
	 *            destination directory to the classpath
	 */
	boolean compileInputSources() throws Exception;

}
