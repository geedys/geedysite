package com.geedys.geedysite.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "PLATFORM_USER_TAB")
public class PlatformUserTab {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DEFALUT_LANG")
    private String defalutLang;

    @Column(name = "LAST_CHANGE_DATE")
    private Date lastChangeDate;

    @Column(name ="CREATE_DATE")
    private Date createDate;

    @Column(name = "CREATE_BY")
    private String createBy;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "SYS_ADMIN")
    private String sysAdmin;

    @Column(name = "COME_FROM")
    private String comeFrom;

    @Column(name = "ROWVERSION")
    private Date rowversion;

    @Column(name = "ROWKEY")
    private String rowkey;


}