package batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "reviews")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("rating")
    private float rating;

    @JsonProperty("title")
    private String title;

    @Lob
    @JsonProperty("text")
    private String text;

    @JsonProperty("images")
    @ElementCollection
    private List<String> images;

    @JsonProperty("asin")
    private String asin;

    @JsonProperty("parent_asin")
    private String parentAsin;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("helpful_vote")
    private int helpfulVote;

    @JsonProperty("verified_purchase")
    private boolean verifiedPurchase;
}