package name.guyue.backend.enums;

/**
 * 用户状态
 * @author hujia
 * @date 2019-03-25
 */
public enum UserStateTypeEnum {
    /** 待审核 */
    NotVerify,
    /** 审核通过 */
    Verified,
    /** 审核未通过 */
    Pass,
    /** 正常用户 */
    Normal,
}
