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
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateToDoLambda {
    private final ObjectMapper objectMapper =
            new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
    private final String tableName = System.getenv("TODO_TABLE");

    public APIGatewayProxyResponseEvent handler(APIGatewayProxyRequestEvent request) throws IOException {
        final ToDo toDo = objectMapper.readValue(request.getBody(), ToDo.class);
        final Item savedItem = prepareAndSaveToDoItem(toDo);

        Map<String, String> responseJsonPayload = new HashMap<>();
        responseJsonPayload.put(ToDo.ID_FIELD, savedItem.getString(ToDo.ID_FIELD));

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(201)
                .withBody(objectMapper.writeValueAsString(responseJsonPayload));
    }

    private Item prepareAndSaveToDoItem(final ToDo toDo) {
        final Table table = dynamoDB.getTable(tableName);

        ToDo toDoWithId = establishId(toDo);
        Instant now = Instant.now();
        toDoWithId.setCreatedAt(now);

        final Item item = new Item()
                .withPrimaryKey(ToDo.ID_FIELD, toDoWithId.getId())
                .withString(ToDo.TEXT_FIELD, toDoWithId.getText())
                .withString(ToDo.CREATED_AT_FIELD, toDoWithId.toString())
                .withString(ToDo.STATUS_FIELD, toDoWithId.getStatus().getValue())
                .withDouble(ToDo.EXPECTED_DONE_HOURS_FIELD, toDoWithId.getExpectedDoneHours());
        table.putItem(item);
        return item;
    }

    private ToDo establishId(ToDo toDo) {
        if (toDo.getId() == null || toDo.getId().isEmpty()) {
            toDo.setId(UUID.randomUUID().toString());
        }
        return toDo;
    }
}
