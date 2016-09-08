package support.upload

import java.io.File

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{CannedAccessControlList, PutObjectRequest}
import com.google.inject.Inject

class S3Uploader @Inject() (configuration: play.api.Configuration) extends Uploader {
  private val accessKey = configuration.underlying.getString("s3.accessKey")
  private val secretKey = configuration.underlying.getString("s3.secretKey")
  private val bucket = configuration.underlying.getString("s3.bucket")

  private val s3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey))

  override def upload(key: String, file: File): String = {
    s3Client.putObject(new PutObjectRequest(bucket, key, file).withCannedAcl(CannedAccessControlList.PublicRead))
    s3Client.getUrl(bucket, key).toString
  }
}
