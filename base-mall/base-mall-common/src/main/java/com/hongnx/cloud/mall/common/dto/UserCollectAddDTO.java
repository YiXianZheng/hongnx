package com.hongnx.cloud.mall.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @date 2019-08-12 16:25:10
 */
@Data
public class UserCollectAddDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    private String type;
    private List<String> relationIds;

}
