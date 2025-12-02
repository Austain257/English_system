package com.austain.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRequest {
    private Long id;
    private String word;
    private String chinese;
    private String pronounce;
    private String times;
    private String bookname;
}
