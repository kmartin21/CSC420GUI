package models;

import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;
import utils.ImageUtils;
import utils.PublicationsService;
import views.subviews.PublicationContributeButtonCellRenderer;

import java.awt.image.BufferedImage;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by lwdthe1 on 9/26/16.
 */
public class Publication {
    public static final PrettyTime p = new PrettyTime();

    private String id;
    private String name;
    private String description;
    private String url;

    private String imageUrl;
    private String contributorRole;
    private String contributorUsername;
    private boolean belongsToCurrentUser;

    private String createDate;
    private String createdAgo;
    private String lastModified;

    private int pubIdTotalVisits;
    private int pubIdTotalVisitsByCurrentUser;
    private int pubIdTotalContributionRequests;

    private BufferedImage image;
    //store client specific data
    private HashMap<String, Object> virtuals = new HashMap<>();
    private Boolean currentUserRequested;
    private Boolean currentUserRequestWasRejected;


    public Publication(JSONObject jsonPublication) {
        id = jsonPublication.getString("id");
        name = jsonPublication.getString("name");
        description = jsonPublication.getString("description");
        url = jsonPublication.getString("url");
        url.replace("c/200/200", "c/400/400");
        imageUrl = jsonPublication.getString("imageUrl");
        contributorRole = jsonPublication.getString("contributorRole");
        contributorUsername = jsonPublication.getString("contributorUsername");


        belongsToCurrentUser = jsonPublication.getBoolean("belongsToCurrentUser");
        pubIdTotalContributionRequests = jsonPublication.getInt("pubIdTotalContributionRequests");
        pubIdTotalVisits = jsonPublication.getInt("pubIdTotalVisits");
        pubIdTotalVisitsByCurrentUser = jsonPublication.getInt("pubIdTotalVisitsByCurrentUser");
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContributorRole() {
        return contributorRole;
    }

    public String getContributorUsername() {
        return contributorUsername;
    }

    public Boolean getBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }

    public int getPubIdTotalVisits() {
        return pubIdTotalVisits;
    }

    public int getPubIdTotalVisitsByCurrentUser() {
        return pubIdTotalVisitsByCurrentUser;
    }

    public int getPubIdTotalContributionRequests() {
        return pubIdTotalContributionRequests;
    }

    public BufferedImage getImage(int width, int height) {
        if(image == null) image = ImageUtils.loadImage(imageUrl);
        ImageUtils.resizeBufferedImage(image, width, height);
        return image;
    }

    public BufferedImage getImage() {
        if(image == null) image = ImageUtils.loadImage(imageUrl);
        return image;
    }

    public Boolean currentUserRequestWasRejected() {
        if (virtuals.containsKey("currentUserRequestWasRejected")) {
            return (Boolean) virtuals.get("currentUserRequestWasRejected");
        }
        return false;
    }

    public Boolean currentUserIsContributor() {
        if (virtuals.containsKey("currentUserIsContributor")) {
            return (Boolean) virtuals.get("currentUserIsContributor");
        }
        return false;
    }

    public Boolean currentUserRequested() {
        if (virtuals.containsKey("currentUserRequested")) {
            return (Boolean) virtuals.get("currentUserRequested");
        }
        return false;
    }

    public Boolean currentUserRetractedRequest() {
        if (virtuals.containsKey("currentUserRetractedRequest")) {
            return (Boolean) virtuals.get("currentUserRetractedRequest");
        }
        return false;
    }

    public void setCurrentUserRequestWasRejected(Boolean val) {
        this.virtuals.put("currentUserRequestWasRejected", val != null? val : false);
    }

    public void setCurrentUserIsContributor(Boolean val) {
        this.virtuals.put("currentUserIsContributor", val != null? val : false);
    }

    public void setCurrentUserRequested(Boolean val) {
        this.virtuals.put("currentUserRequested", val != null? val : false);
    }

    public void setCurrentUserRetractedRequested(Boolean val) {
        if (val != null) {
            this.setCurrentUserRequested(!val);
        }
    }

    public void setHomeFeedTableCell(PublicationContributeButtonCellRenderer homeFeedTableCell) {
        this.virtuals.put("homeFeedTableCell", homeFeedTableCell);
    }

    public PublicationContributeButtonCellRenderer getHomeFeedTableCell() {
        Object value = this.virtuals.get("homeFeedTableCell");
        return value instanceof PublicationContributeButtonCellRenderer?
                (PublicationContributeButtonCellRenderer) value : null;
    }
}
