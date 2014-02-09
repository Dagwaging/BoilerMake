package com.boilermake.studycentral;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;

public class Group {
	public String name;
	public String location;
	public String size;
	
	public Group(String name, String location, String size) {
		this.name = name;
		this.location = location;
		this.size = size;
	}
}
