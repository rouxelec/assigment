package com.assignment.trinimbus;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class DynamodbLoader {
	static DynamoDB dynamoDB;
	static AmazonDynamoDB dynamodbClient;
	static DynamoDBMapper mapper;

	static {
		try {
			dynamodbClient = getDynamoDb();
			dynamoDB = new DynamoDB(dynamodbClient);
			mapper = new DynamoDBMapper(dynamodbClient);
			createTable(mapper, TrinimbusStuff.class, 25l, 25l);
			System.out.println("waiting " + 15 + "s...");
			Thread.sleep(15 * 1000);
			TrinimbusStuff trinimbusStuff = new TrinimbusStuff("Trinimbus is awesome");
			TrinimbusStuff trinimbusStuff2 = new TrinimbusStuff("I want to work for Trinimbus");
			TrinimbusStuff trinimbusStuff3 = new TrinimbusStuff("Those values are from dynamodb...");
			mapper.save(trinimbusStuff);
			mapper.save(trinimbusStuff2);
			mapper.save(trinimbusStuff3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TrinimbusStuff trinimbusStuff = new TrinimbusStuff("Trinimbus is awesome");
		TrinimbusStuff trinimbusStuff2 = new TrinimbusStuff("I want to work for Trinimbus");
		TrinimbusStuff trinimbusStuff3 = new TrinimbusStuff("Those values are from dynamodb...");
		mapper.save(trinimbusStuff);
		mapper.save(trinimbusStuff2);
		mapper.save(trinimbusStuff3);
		System.out.println(getDescriptions());
	}

	public static AmazonDynamoDB getDynamoDb() {
		// AWSCredentials credentials = getCredentials();
		dynamodbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.CA_CENTRAL_1).build();
		return dynamodbClient;
	}

	public static AWSCredentials getCredentials() {
		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider("default").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. ", e);
		}
		return credentials;
	}

	public static CreateTableRequest createTable(DynamoDBMapper mapper, Class clazz, long readCapacityUnits, long writeCapacityUnits) {
		System.out.println("Creating table for :" + clazz.getName());
		CreateTableRequest createTableRequest = null;
		try {
			createTableRequest = mapper.generateCreateTableRequest(clazz);
			createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits));
			CreateTableResult createTableResult = dynamodbClient.createTable(createTableRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return createTableRequest;
	}

	public static String getDescriptions() {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		PaginatedScanList<TrinimbusStuff> list = mapper.scan(TrinimbusStuff.class, scanExpression);
		return list.toString();
	}

}
