
package org.fathens.wercker.deploy.googleplay

import java.io.File

import scala.collection.JavaConversions._

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.androidpublisher.{ AndroidPublisher, AndroidPublisherScopes }
import com.google.api.services.androidpublisher.model.Track
import com.typesafe.scalalogging.LazyLogging

object Main extends App with LazyLogging {
  val edits = {
    val trans = GoogleNetHttpTransport.newTrustedTransport
    val jsonFactory = JacksonFactory.getDefaultInstance

    logger info f"Authorizing using Service Account: ${Settings.SERVICE_ACCOUNT_EMAIL}"
    val credential = new GoogleCredential.Builder()
      .setTransport(trans)
      .setJsonFactory(jsonFactory)
      .setServiceAccountId(Settings.SERVICE_ACCOUNT_EMAIL)
      .setServiceAccountScopes(java.util.Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER))
      .setServiceAccountPrivateKeyFromP12File(Settings.SERVICE_ACCOUNT_KEY)
      .build

    new AndroidPublisher.Builder(trans, jsonFactory, credential)
      .setApplicationName(Settings.APPLICATION_NAME)
      .build.edits
  }

  val editId = edits.insert(Settings.PACKAGE_NAME,
    null // no content
  ).execute.getId
  logger info f"Created edit with id: ${editId}"

  val apk = {
    val apkFile = new FileContent(Settings.MIME_TYPE_APK, Settings.APK_FILE)
    edits.apks.upload(Settings.PACKAGE_NAME, editId, apkFile).execute
  }
  logger info f"Version code ${apk.getVersionCode} has been uploaded"

  val updatedTrack = edits.tracks.update(
    Settings.PACKAGE_NAME,
    editId,
    Settings.TRACK_NAME,
    new Track().setVersionCodes(List(apk.getVersionCode))
  ).execute
  logger info f"Track ${updatedTrack.getTrack} has been updated."

  val appEdit = edits.commit(Settings.PACKAGE_NAME, editId).execute
  logger info f"App edit with id ${appEdit.getId} has been comitted"
}
