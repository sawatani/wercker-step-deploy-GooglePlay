package org.fathens.wercker.deploy.googleplay

import java.io.File

object Settings {
  private def get(name: String) = System.getenv("WERCKER_ANDROID_GOOGLEPLAY_" + name)

  /**
   * Suggested format is "MyCompany-Application/1.0".
   */
  lazy val APPLICATION_NAME = get("APPLICATION_NAME")

  lazy val PACKAGE_NAME = get("PACKAGE_NAME")

  lazy val SERVICE_ACCOUNT_EMAIL = get("ACCOUNT_EMAIL")

  lazy val SERVICE_ACCOUNT_KEY = new File(get("ACCOUNT_KEY_FILE_PATH"))

  lazy val APK_FILE = new File(get("APK_FILE_PATH"))

  lazy val TRACK_NAME = get("TRACK_NAME")

  lazy val MIME_TYPE_APK = "application/vnd.android.package-archive"
}
