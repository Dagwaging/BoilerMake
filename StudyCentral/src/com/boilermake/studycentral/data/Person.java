package com.boilermake.studycentral.data;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

@DynamoDBTable(tableName = "people")
public class Person {
	private String id;
	private String name;

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
