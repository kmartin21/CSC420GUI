package models;

import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;
import utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by lwdthe1 on 12/4/16.
 */
public class ChatMessage {
    private final String publicationId;
    private final String userId;
    private final String text;
    private String imageUrl;
    private String userName;
    private String contributorRole;
    private BufferedImage image;
    private String timeAgo;
    private PrettyTime prettyTime = new PrettyTime();

    public ChatMessage(JSONObject payload) {
        if (payload.has("message")) payload = (JSONObject) payload.get("message");
        this.publicationId = payload.getString("publicationId");
        this.userId = payload.getString("userId");
        this.text = payload.getString("text");
        if (payload.has("username")) this.userName = payload.getString("username");
        if (payload.has("contributorRole")) {
            this.contributorRole = payload.getString("contributorRole").substring(0, 1).toUpperCase() + payload.getString("contributorRole").substring(1);
        } else {
            this.contributorRole = "Writer";
        }
        String createdDate = payload.getString("createDate");
        //this.timeAgo = createTimeAgoString(createdDate);
        this.imageUrl = payload.getString("userImageUrl");
    }

    public ChatMessage(String publicationId, String userId, String text) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.text = text;
    }

    private String createTimeAgoString(String createdDate) {
        try {
            createdDate = createdDate.replace("T", " ");
            createdDate = createdDate.replace("Z", "");
            String dateStr = createdDate;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            Date createdDateStamp = simpleDateFormat.parse(dateStr);
            return prettyTime.format(createdDateStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONObject createJSONPayload(String publicationId, String userId, String text) {
        JSONObject payload = new JSONObject();
        payload.put("publicationId", publicationId);
        payload.put("userId", userId);
        payload.put("text", text);
        return payload;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public String getUserName() { return userName; }

    public String getContributorRole() { return contributorRole; }

    public String getTimeAgo() { return timeAgo; }

    public BufferedImage getImage() {
        if(image == null) image = ImageUtils.loadImage(imageUrl);
        return image;
    }
}
