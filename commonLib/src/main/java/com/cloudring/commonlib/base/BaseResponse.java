package com.cloudring.commonlib.base;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by zjq on 2016/7/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseResponse implements Serializable {
    @JsonProperty
    public String result;

    public boolean isResult() {
        if (!TextUtils.isEmpty(result)) {
            return "true".equals(result);
        }
        return false;
    }
}

