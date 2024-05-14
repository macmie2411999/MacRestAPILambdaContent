package devop;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import entities.Content;

import java.util.List;
import java.util.Map;

public class ContentService {
    private DynamoDBMapper dynamoDBMapper;
    private static  String jsonBody = null;

    public APIGatewayProxyResponseEvent saveContent(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        Content content = Utility.convertStringToObj(apiGatewayRequest.getBody(),context);
        dynamoDBMapper.save(content);
        jsonBody = Utility.convertObjToString(content,context) ;
        context.getLogger().log("data saved successfully to dynamodb:::" + jsonBody);
        return createAPIResponse(jsonBody,201,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent getContentById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String contentId = apiGatewayRequest.getPathParameters().get("contentId");
        Content content =   dynamoDBMapper.load(Content.class,contentId)  ;
        if(content!=null) {
            jsonBody = Utility.convertObjToString(content, context);
            context.getLogger().log("fetch content By ID:::" + jsonBody);
            return createAPIResponse(jsonBody,200,Utility.createHeaders());
        }else{
            jsonBody = "Content Not Found Exception :" + contentId;
            return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }

    }

    public APIGatewayProxyResponseEvent getContents(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        List<Content> contents = dynamoDBMapper.scan(Content.class,new DynamoDBScanExpression());
        jsonBody =  Utility.convertListOfObjToString(contents,context);
        context.getLogger().log("fetch content List:::" + jsonBody);
        return createAPIResponse(jsonBody,200,Utility.createHeaders());
    }
    public APIGatewayProxyResponseEvent deleteContentById(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        initDynamoDB();
        String contentId = apiGatewayRequest.getPathParameters().get("contentId");
        Content content =  dynamoDBMapper.load(Content.class,contentId)  ;
        if(content!=null) {
            dynamoDBMapper.delete(content);
            context.getLogger().log("data deleted successfully :::" + contentId);
            return createAPIResponse("data deleted successfully." + contentId,200,Utility.createHeaders());
        }else{
            jsonBody = "Content Not Found Exception :" + contentId;
            return createAPIResponse(jsonBody,400,Utility.createHeaders());
        }
    }


    private void initDynamoDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }
    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }

}
