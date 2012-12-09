/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.dev;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.dev.javac.CompilationProblemReporter;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;

/**
 * Used to run compiler tasks with the appropriate logger.
 */
public class CompileTaskRunner {

  /**
   * A task to run with a logger based on options.
   */
  public interface CompileTask {
    boolean run(TreeLogger logger) throws UnableToCompleteException;
  }

  /**
   * Runs the main action with an appropriate logger. If a gui-based TreeLogger
   * is used, this method will not return until its window is closed by the
   * user.
   */
  public static boolean runWithAppropriateLogger(CompileTaskOptions options,
      final CompileTask task) {
    // Set any platform specific system properties.
    BootStrapPlatform.applyPlatformHacks();

    // TODO(jat): add support for GUI logger back

    // Compile tasks without -treeLogger should run headless.
    if (System.getProperty("java.awt.headless") == null) {
      System.setProperty("java.awt.headless", "true");
    }
    PrintWriterTreeLogger logger = new PrintWriterTreeLogger();
    logger.setMaxDetail(options.getLogLevel());
    return doRun(logger, task);
  }

  private static boolean doRun(TreeLogger logger, CompileTask task) {
    try {
      return task.run(logger);
    } catch (UnableToCompleteException e) {
      // Assume logged.
    } catch (Throwable e) {
      CompilationProblemReporter.logAndTranslateException(logger, e);
    }
    return false;
  }
}
