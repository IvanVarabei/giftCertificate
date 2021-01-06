package com.epam.esm.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GiftCertificate {
    //id
    private Long giftCertificateId;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
   // private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;
 //   private LocalDateTime updatedDate;
    private List<Tag> tags;
}
