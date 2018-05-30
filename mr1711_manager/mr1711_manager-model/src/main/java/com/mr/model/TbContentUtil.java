package com.mr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by czl on 2018/5/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TbContentUtil implements Serializable {
    private String srcB;
    private String src;
    private String height;
    private String width;
    private String heightB;
    private String widthB;
    private String href;
    private String alt;
}
