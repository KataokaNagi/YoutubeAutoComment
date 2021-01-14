package com.github.kataokanagi.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NONE)
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserInfoPlus {

    public String id;

    public String name;

    public String picture;

}
