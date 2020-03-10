package com.ueboot.core;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yangkui
 * createTime:2020/3/1012:29
 */
@Getter
@Setter
public class ReqBody {

    @NotNull(message = "ID不可以为空")
    private Long id;

    @NotBlank(message = "NAME不要空的")
    private String name;
}
