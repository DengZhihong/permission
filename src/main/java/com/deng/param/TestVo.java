package com.deng.param;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TestVo {
    @NotBlank
    private String msg;

    @NotNull
    @Max(10)
    @Min(0)
    private Integer id;

//    @NotEmpty
//    private List<String> str;
}
