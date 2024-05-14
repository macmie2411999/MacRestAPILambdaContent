package entities;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="macContentTable")
public class Content {

    @DynamoDBHashKey(attributeName = "contentId")
    private String contentId;

    @DynamoDBAttribute(attributeName = "contentTitle")
    private String contentTitle;

    @DynamoDBAttribute(attributeName = "contentBrief")
    private String contentBrief;

    @DynamoDBAttribute(attributeName = "contentMain")
    private String contentMain;

    @DynamoDBAttribute(attributeName = "contentDate")
    private String contentDate;

    @DynamoDBAttribute(attributeName = "contentUserId")
    private Integer contentUserId;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentBrief() {
        return contentBrief;
    }

    public void setContentBrief(String contentBrief) {
        this.contentBrief = contentBrief;
    }

    public String getContentMain() {
        return contentMain;
    }

    public void setContentMain(String contentMain) {
        this.contentMain = contentMain;
    }

    public String getContentDate() {
        return contentDate;
    }

    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public Integer getContentUserId() {
        return contentUserId;
    }

    public void setContentUserId(Integer contentUserId) {
        this.contentUserId = contentUserId;
    }
}

