package com.app.mdc.config.whiteList;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "mdc")
public class WhiteListProperties {

    private List<String> blankList;

    public List<String> getBlankList() {
        return blankList;
    }

    public void setBlankList(List<String> blankList) {
        this.blankList = blankList;
    }
}
