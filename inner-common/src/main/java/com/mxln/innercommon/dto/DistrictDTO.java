package com.mxln.innercommon.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dic_district")
public class DistrictDTO {

    @TableId
    private String addressCode;

    @TableField("address_name")
    private String addressName;

    @TableField("parent_address_code")
    private String parentAddressCode;

    @TableField("level")
    private Integer level;
}
