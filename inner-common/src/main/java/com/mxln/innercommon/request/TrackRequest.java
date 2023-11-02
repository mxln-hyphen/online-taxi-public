package com.mxln.innercommon.request;

import com.mxln.innercommon.dto.LocateDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TrackRequest {

    private String key;

    private Integer sid;

    private Integer tid;

    private String name;

    private Integer trid;

    private String trname;

    private LocateDTO[] points;

}
