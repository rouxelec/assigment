package com.assignment.trinimbus;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "TrinimbusStuff")
public class TrinimbusStuff {
	@DynamoDBHashKey
	private String theHashKey = UUID.randomUUID().toString();

	public String getTheHashKey() {
		return theHashKey;
	}

	public void setTheHashKey(String theHashKey) {
		this.theHashKey = theHashKey;
	}

	@DynamoDBAttribute
	private String description;

	public TrinimbusStuff() {
	}

	public TrinimbusStuff(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
