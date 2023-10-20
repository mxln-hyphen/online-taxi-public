package com.mxln.innercommon.dto;

import com.mxln.innercommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    int code;
    String message;
    private T data;

    /**
     * 成功并返回T类型参数信息和成功信息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getMessage())
                .setData(data);
    }

    /**
     * 成功并返回成功信息
     * @return
     */
    public static ResponseResult success(){
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getMessage());
    }

    /**
     * 失败并返回失败信息
     * @return
     */
    public static ResponseResult fail(){
        return new ResponseResult().setCode(CommonStatusEnum.FAIL.getCode())
                .setMessage(CommonStatusEnum.FAIL.getMessage());
    }

}
