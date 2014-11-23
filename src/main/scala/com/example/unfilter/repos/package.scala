package com.example.unfilter

import com.amazonaws.services.sns.{AmazonSNSClient, AmazonSNS}
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.auth.BasicSessionCredentials

package object repos {

  def sns: AmazonSNS = {
    val awsAccessKey: String = System.getenv("AWS_ACCESS_KEY_ID")
    val awsSecretKey: String = System.getenv("AWS_SECRET_ACCESS_KEY")
    val sessionToken: String = System.getenv("AWS_SESSION_TOKEN")

    val creds = new BasicSessionCredentials(awsAccessKey, awsSecretKey, sessionToken)
    val sns: AmazonSNS = new AmazonSNSClient(creds)
    sns.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2))

    sns
  }
}
