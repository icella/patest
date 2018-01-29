/**
 * @(#)MailCfg.java
 *
 * @author xuji
 * @version 1.0 2013-7-18
 *
 * Copyright (C) 2012,2013 , PING' AN, Inc.
 */
package ice.utils.hbase;

public class HbaseCfg
{
    public final static String zookeeperQuorum ="hbase.zookeeper.quorum";
    //ip = hdp-nn-1.trustutn.org,hdp-nn-2.trustutn.org,hdp-dn-1.trustutn.org
    public final static  String ip = "hdp-nn-1,hdp-dn-1,hdp-dn-2";

    /**
     * hbase.rpc.timeout
     * 一次RPC请求的时间，超时客户端将主动关闭socket.该值默认大小为60000ms，即1min
     * 异常经常发生在大量高并发读写业务或者服务器端发生了比较严重的Full GC等场景下，
     * 导致某些请求无法得到及时处理，超过了时间间隔
     */
    public final static  String rcp_timeout = "60000";

    /**
     * hbase.client.operation.timeout
     * hbase客户端发起一次数据操作直至得到响应之间的总超时时间。
     * 数据操作类型包括get, append, increment, delete,put等。
     * 可能包含多个rpc请求
     */
    public final static  String operation_timeout = "60000";

    /**
     * hbase.client.scanner.timeout.period
     * 对scan操作的超时
     * 如果一次scan操作请求大量数据，hbase会讲操作分为多个RPC请求，
     * 每次只返回规定数量的结果
     */
    public  final static String scanner_timeout_period = "60000";

    public  final static String retries_number = "3";
    public  final static int corePoolSize = 10;
    public  final static int connection_max_thread_size = 200;

    public  final static int method_timeout = 5;

    /**
     * 贷款分类接口　/b/loanClassify
     */
    public final static String TABLE_MnImsiLoan             = "biz_4:tb_mn_loan";
    public final static String TABLE_MnIdCardLoan           = "biz:tb_idcard_mn_loan";
    public final static String TABLE_PpdaiData              = "p2p:ppdai";

    /**
     * 逾期分类接口　/b/overdueClassify
     */
    public  final static String TABLE_MnImsiOverdue          = "biz_4:tb_mn_overdue";
    public  final static String TABLE_MnIdCardOverdue        = "biz:tb_idcard_mn_overdue";


    /**
     *  银行卡实名验证接口 /bankcardauth
     */
    public final static  String TABLE_BankAccountAuth        = "user_info:tb_bank_account_auth";

    /**
     * 电话信息接口　/b/phoneinfo
     */
    public  final static String TABLE_ContactPhoneOutline    = "user_info_4:tb_digest_phone_outline";

    /**
     * 逾期，贷款，黑名单,手机号设备号匹配，　imsi/imei查询手机号接口
     */
    public  final static String TABLE_ImeiOutline            = "user_info_4:tb_digest_imei_outline";

    /**
     * imsi/imei查询手机号接口
     */
    public  final static String TABLE_ImsiOutline            = "user_info_4:tb_digest_imsi_outline";

    /**
     * 各接口必用
     */
    public final static  String TABLE_PhoneOutline           = "user_info_4:tb_digest_phone_outline";
//    public String TABLE_PhoneOutline;

    /**
     * 染黑度
     */
    public  final static String TABLE_ContactBlackDyeing     = "biz:tb_black_dyeing_2";

    /**
     * 授权文件接口　
     */
    public  final static String TABLE_HbaseAuthFile          = "biz:tb_auth_file";
    /**
     * 身份证验证
     */
    public final static  String TABLE_HbaseIdCard            = "biz:tb_id_card";

    /**
     * 贷款意向，贷款分类
     */
    public  final static String TABLE_OrgLogStatistics       = "biz:tb_org_log";

    /**
     * 快递接口
     */
    public  final static String TABLE_ExpressData            = "qihu_sms_4:tb_express_data";

    /**
     * 黑名单，变量模型
     */
    public final static  String TABLE_ImsiBlackList          = "qihu_sms_4:tb_imsi_black_list";

    /**
     * 黑名单
     */
    public final static  String TABLE_ImsiBlackOrg           = "qihu_sms_4:tb_imsi_org_black_list";

    /**
     * 电话信息，小号，暂未使用
     */
    public  final static String TABLE_SubPhoneNumber         = "qihu_sms_4:tb_sub_phone_number";
}



/**
 * $Log: MailCfg.java,v $
 * 
 * @version 1.0 2013-7-18 
 *
 */
