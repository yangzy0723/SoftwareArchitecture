package batch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @ElementCollection： 此注解用于指定一个集合是一个元素集合，这意味着集合中的元素将被JPA持久化到一个单独的表中；
 * @CollectionTable： 注解用来指定存储集合元素的表的详细信息。它允许你定义该表的名字以及如何通过外键与包含实体连接。具体来说：
 * name 属性定义了用来存储集合元素的表的名称。在这个例子中，表名被设为"product_features"。
 * joinColumns 属性定义了用于连接集合表和主实体表的外键。@JoinColumn(name = "product_id")指明了product_features表中用于参照Product表的主键的外键列名是product_id。
 * @Embeddable: 注解用于定义一个类，其实例可以被嵌入到其他实体中。嵌入式类VideosItem在这里表示的是包含userId、title和url的复合数据结构。
 * 由于VideosItem是用@Embeddable标记的，所以它没有自己的实体表；它的属性将被嵌入到使用它的实体的表中
 */

@Data
@Entity
@Table(name = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("main_category")
    private String mainCategory;

    @JsonProperty("title")
    private String title;

    @JsonProperty("average_rating")
    private double averageRating;

    @JsonProperty("rating_number")
    private int ratingNumber;

    @JsonProperty("features")
    @ElementCollection
    @CollectionTable(name = "product_features", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> features;

    @JsonProperty("description")
    @ElementCollection
    @Lob
    @CollectionTable(name = "product_descriptions", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> description;

    @JsonProperty("price")
    private double price;

    @JsonProperty("images")
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    private List<ImagesItem> images;

    @JsonProperty("videos")
    @ElementCollection
    @CollectionTable(name = "product_videos", joinColumns = @JoinColumn(name = "product_id"))
    private List<VideosItem> videos;

    @JsonProperty("store")
    private String store;

    @JsonProperty("categories")
    @ElementCollection
    @CollectionTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> categories;

//	@JsonProperty("details")
//	@ElementCollection
//	@CollectionTable(name = "product_details", joinColumns = @JoinColumn(name = "product_id"))
//	@MapKeyColumn(name = "detail_key")
//	@Column(name = "detail_value")
//	private Map<String, Object> details;

    @Column(name = "details", columnDefinition = "TEXT")
    @JsonProperty("details")
    private String detailsJson;  // 存储 JSON 字符串

    @JsonProperty("parent_asin")
    private String parentAsin;

    @JsonProperty("bought_together")
    @ElementCollection
    @CollectionTable(name = "product_bought_together", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> boughtTogether;

    // 将 JSON 数据转换为 Java 对象时自定义属性的设置逻辑；当 price 出错时设置默认值
    @JsonSetter("price")
    public void setJsonPrice(String value) {
        try {
            this.price = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            this.price = 0.0;
        }
    }

    // Custom setter for 'details' field
    @JsonSetter("details")
    public void setJsonDetails(Object value) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.detailsJson = objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            this.detailsJson = "{}"; // 设置默认值为空 JSON 对象
        }
    }

    // json 反序列化的时候忽略常规的 setter 方法
    @JsonIgnore
    public void setDetailsJson(String detailsJson) {
        this.detailsJson = detailsJson;
    }

    @Data
    @Embeddable
    public static class ImagesItem {

        @JsonProperty("hi_res")
        private String hiRes;

        @JsonProperty("thumb")
        private String thumb;
        @JsonProperty("large")
        private String large;

        @JsonProperty("variant")
        private String variant;
    }

    @Data
    @Embeddable
    public static class VideosItem {
        @JsonProperty("user_id")
        String userId;
        @JsonProperty("title")
        String title;
        @JsonProperty("url")
        String url;
    }
}