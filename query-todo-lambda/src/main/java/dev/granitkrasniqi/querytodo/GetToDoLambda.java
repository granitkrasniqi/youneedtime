package dev.granitkrasniqi.querytodo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class GetToDoLambda {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    private final String tableName = System.getenv("TODO_TABLE");

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) throws IOException {
        final String toDoId = request.getPathParameters().get("id");
        Table toDoTable = dynamoDB.getTable(tableName);
        Item item = toDoTable.getItem("id", toDoId);

        if (item == null) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(404);
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(item.toJSON());
    }
}
