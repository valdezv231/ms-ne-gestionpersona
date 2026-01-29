package com.example.msnegestionpersonas.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("applications")
@Data
public class Application {

    @Id
    private Long id;

    @Column("application_code")
    private String applicationCode;

    @Column("application_name")
    private String applicationName;

    @Column("consumer_id")
    private String consumerId;
}
