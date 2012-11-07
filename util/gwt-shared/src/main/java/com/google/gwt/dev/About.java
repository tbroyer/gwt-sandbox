/*
 * Copyright 2006 Google Inc.
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * About information for GWT.
 */
public class About {

  private static final String gwtName = "Google Web Toolkit";
  private static final String gwtRevision;
  private static final GwtVersion gwtVersion;

  static {
    Properties props = new Properties();
    try {
      InputStream instream = About.class.getResourceAsStream("About.properties");
      props.load(instream);
    } catch (IOException iox) {
      // okay... we use default values, then.
    }

    gwtRevision = props.getProperty("gwt.revision", "unknown");

    String tmp = props.getProperty("gwt.version");
    if (tmp == null) {
      gwtVersion = new GwtVersion();
    } else {
      gwtVersion = new GwtVersion(tmp);
    }
  }

  /**
   * Returns the name of the product.
   */
  public static String getGwtName() {
    return gwtName;
  }

  /**
   * Returns the Git repository revision SHA1.
   * 
   * @return the git revision or 'unknown' if the value couldn't be
   *         determined at build time.
   * 
   * @deprecated Use {@link #getGwtRevision()} instead
   */
  @Deprecated
  public static String getGwtSvnRev() {
    return gwtRevision;
  }

  /**
   * Returns the Git repository revision SHA1.
   * 
   * @return the git revision or 'unknown' if the value couldn't be
   *         determined at build time.
   */
  public static String getGwtRevision() {
    return gwtRevision;
  }

  /**
   * Returns the product name and release number concatenated with a space.
   */
  public static String getGwtVersion() {
    return getGwtName() + " " + getGwtVersionNum();
  }

  /**
   * The Google Web Toolkit release number.
   * 
   * @return the release number or the array {0, 0, 0} if the value couldn't be
   *         determined at build time.
   */
  public static int[] getGwtVersionArray() {
    return gwtVersion.getComponents();
  }

  /**
   * The Google Web Toolkit release number.
   * 
   * @return the release number or the string '0.0.0' if the value couldn't be
   *         determined at build time.
   */
  public static String getGwtVersionNum() {
    return gwtVersion.toString();
  }

  /**
   * The Google Web Toolkit release number.
   * 
   * @return the release number or a version equivalent to "0.0.0" if the value
   *     couldn't be determined at build time.
   */
  public static GwtVersion getGwtVersionObject() {
    // This is public because CheckForUpdates and WebAppCreator need access.
    return gwtVersion;
  }

  private About() {
  }
}
