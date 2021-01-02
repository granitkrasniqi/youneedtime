package dev.granitkrasniqi.createtodo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.granitkrasniqi.yntcommoncode.domain.ToDo;

import java.io.IOException;
import java.util.UUID;

public class CreateToDoLambda {
    private final ObjectMapper objectMapper =
            new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    private final String tableName = System.getenv("TODO_TABLE");

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) throws IOException {
        final ToDo toDo = objectMapper.readValue(request.getBody(), ToDo.class);
        Item savedItem = saveToDoItem(toDo);

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(savedItem.getString(ToDo.ID_FIELD));
    }

    private Item saveToDoItem(ToDo toDo) {
        final Table table = dynamoDB.getTable(tableName);

        final Item item = new Item()
                .withPrimaryKey(ToDo.ID_FIELD, UUID.randomUUID().toString())
                .withString(ToDo.TEXT_FIELD, toDo.getText())
                .withLong(ToDo.CREATED_AT_FIELD, toDo.getCreatedAt())
                .withString(ToDo.STATUS_FIELD, toDo.getStatus().getValue())
                .withDouble(ToDo.EXPECTED_DONE_HOURS_FIELD, toDo.getExpectedDoneHours());
        table.putItem(item);
        return item;
    }
}
