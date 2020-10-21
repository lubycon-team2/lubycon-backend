package com.rubycon.rubyconteam2.domain.review.dto.request;

import com.rubycon.rubyconteam2.domain.review.domain.Content;
import com.rubycon.rubyconteam2.domain.review.domain.Rating;
import com.rubycon.rubyconteam2.domain.review.domain.Review;
import com.rubycon.rubyconteam2.global.common.anotation.ListOfEnum;
import com.rubycon.rubyconteam2.global.common.anotation.ValueOfEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReviewRequest {

    @NotEmpty(message = "리뷰 내용을 입력해주세요 \n['COMPLIMENT_1','COMPLIMENT_2'] | ['IMPROVEMENTS_1','IMPROVEMENTS_2']")
    @ApiModelProperty(value = "리뷰 내용 - Multi Select 가능", required = true, example = "Array('COMPLIMENT_1','COMPLIMENT_2') | Array('IMPROVEMENTS_1','IMPROVEMENTS_2')")
    @ListOfEnum(enumClass = Content.class)
    private List<String> contents;

    public List<Rating> getRatingList(Review review) {
        return this.contents.stream()
                .map(Content::valueOf)
                .map(content -> Rating.of(content, review))
                .collect(Collectors.toList());
    }
}
