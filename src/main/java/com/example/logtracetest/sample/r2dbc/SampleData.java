package com.example.logtracetest.sample.r2dbc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("SITE_INFO")
public class SampleData {
  @Id
  private int siteId;
  private String siteName;
  private String siteAddress;
  private String siteType;
  private String inScheduleYmd;
  private String startYmd;
  private String completionYmd;
  private String installState;
  private String accessIp;
  private String developerCorp;
  private String builderCorp;
  private String homepageDomain;
  private String etcDesc;
  private LocalDateTime ipUpdateTime;
  private String poscoYn;
}
