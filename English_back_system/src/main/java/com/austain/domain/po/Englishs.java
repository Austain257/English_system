package com.austain.domain.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Englishs {
    private String id;
    private String word;
    private String chinese;
    private String pronounce;
    private String times;
    private String bookname;
    private Long userId;
    private Date createTime;
    private Date updateTime;
}
