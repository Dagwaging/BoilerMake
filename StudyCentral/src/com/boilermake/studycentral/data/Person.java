package com.boilermake.studycentral.data;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.boilermake.studycentral.facebook.FacebookActivity;

@DynamoDBTable(tableName = "people")
public class Person {
	private String id;
	private String name;
	private static Person self = null;

	@DynamoDBHashKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static Person getSelf(Context context) {
		if(self == null) {
			SharedPreferences prefs = context.getSharedPreferences(FacebookActivity.SHARED_PREFERENCES, Activity.MODE_PRIVATE);
			String name = prefs.getString(FacebookActivity.PREFERENCE_NAME, null);
			String id = prefs.getString(FacebookActivity.PREFERENCE_ID, null);
			
			self = new Person();
			self.setName(name);
			self.setId(id);
		}
		
		return self;
	}

	public List<Group> getGroups(DynamoDBMapper mapper) {
		DynamoDBScanExpression expression = new DynamoDBScanExpression();
		expression.addFilterCondition("members", new Condition()
				.withComparisonOperator(ComparisonOperator.CONTAINS)
				.withAttributeValueList(new AttributeValue().withS(getId())));

		PaginatedScanList<Group> groups = mapper.scan(Group.class, expression);
		groups.loadAllResults();
		
		return groups;
	}
}
