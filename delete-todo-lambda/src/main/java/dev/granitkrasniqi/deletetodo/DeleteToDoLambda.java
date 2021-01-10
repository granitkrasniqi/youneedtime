package dev.granitkrasniqi.deletetodo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteToDoLambda {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    private final String tableName = System.getenv("TODO_TABLE");

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) {
        String toDoId = request.getPathParameters().get("id");
        if (toDoId == null) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(404);
        }

        Table toDoTable = dynamoDB.getTable(tableName);
        try {
            toDoTable.deleteItem("id", toDoId);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(204);
        } catch (AmazonServiceException exception) {
            System.err.println(exception.getErrorMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500);
        }
    }
}
