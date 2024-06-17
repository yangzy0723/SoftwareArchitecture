package batch.service;

import batch.model.Review;
import batch.translate.Translator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ReviewProcessor implements ItemProcessor<Review, Review> {

    @Override
    public Review process(Review item) throws Exception {
        item.setText(Translator.translate("en", "zh-CN", item.getText()));
        return item;
    }
}
