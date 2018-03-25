package com.assignment.trinimbus;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@ViewScoped
@Named("myTrinimbusView")
@Default
public class MyTrinimbusView implements Serializable {

	static DynamoDB dynamoDB;
	static AmazonDynamoDB dynamodbClient;
	static DynamoDBMapper mapper;

	static {
		dynamodbClient = DynamodbLoader.getDynamoDb();
		dynamoDB = new DynamoDB(dynamodbClient);
		mapper = new DynamoDBMapper(dynamodbClient);
		TrinimbusStuff trinimbusStuff = new TrinimbusStuff("Trinimbus is awesome");
		TrinimbusStuff trinimbusStuff2 = new TrinimbusStuff("I want to work for Trinimbus");
		TrinimbusStuff trinimbusStuff3 = new TrinimbusStuff("Those values are from dynamodb...");
		mapper.save(trinimbusStuff);
		mapper.save(trinimbusStuff2);
		mapper.save(trinimbusStuff3);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2647689930424250613L;

	private List<TrinimbusStuff> listTrinimbus;

	public MyTrinimbusView() {

	}

	@PostConstruct
	public void init() {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		listTrinimbus = mapper.scan(TrinimbusStuff.class, scanExpression);
		System.out.println(listTrinimbus.toString());
	}

	public List<TrinimbusStuff> getListTrinimbus() {
		return listTrinimbus;
	}

	public void setListTrinimbus(List<TrinimbusStuff> list) {
		this.listTrinimbus = list;
	}

}