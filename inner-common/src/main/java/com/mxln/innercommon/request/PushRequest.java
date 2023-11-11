package com.mxln.innercommon.request;

import lombok.Data;

@Data
public class PushRequest {

    private String userId;

    private String identity;

    private String content;
}
