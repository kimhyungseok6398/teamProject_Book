package org.teamproject.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 메뉴 명칭과 이에 대한 URL
 */
@Data
@AllArgsConstructor // 여기 필드에 쓰인 생성자들을 만들어줌(매개변수로 받는 타입들)
public class MenuDetail {
    private String code;
    private String name;
    private String url;

    /**
     * Lombok은 getter, setter,toString등과
     * 반복되는 메서드등을 줄여주기 위한 라이브러리이며
     * 여기서 @Data 어노테이션은
     * getter, setter,toString, @EqualsAndHashCode,@RequiredArgsConstructor, @Value등을
     * 포함하고 있다
     *
     *
     */
}