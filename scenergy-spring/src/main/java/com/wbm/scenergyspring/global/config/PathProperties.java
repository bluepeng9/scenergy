package com.wbm.scenergyspring.global.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "upload")
@RequiredArgsConstructor
public class PathProperties {

    private final String videoPath;

}
