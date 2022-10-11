package com.homenumber.print_2.vo;


import com.homenumber.print_2.retrofit2.RetrofitItemVo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 쇼핑몰정보
 */
@Data                       // @Getter, @Setter, @RequiredArgsConstructor, @EqualsAndHashCode, @ToString를 모두 선언한다.
@NoArgsConstructor          // 멤버 필드들이 모두 파라미터로 지정된 생성자와 빈 생성자를 만들어 줍니다.
@AllArgsConstructor         // 멤버 필드들이 모두 파라미터로 지정된 생성자와 빈 생성자를 만들어 줍니다.
public class MyHomeNumberVo implements Serializable {

    /**
     * type
     * 0이면 헤더, 1이면 자식
     */
    public int type;

    // itemVo
    public RetrofitItemVo.MyHomeNumberVo itemVo;
}
