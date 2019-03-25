package name.guyue.backend.model;

import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import name.guyue.backend.enums.GroupEnum;
import name.guyue.backend.enums.UserStateTypeEnum;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hujia
 * @date 2019-03-25
 */
@Entity
@Table(indexes = {
    @Index(columnList = "openId"),
    @Index(columnList = "group"),
    @Index(columnList = "password"),
    @Index(columnList = "state"),
})
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String openId;

    /** 用户组，用来做权限管理 */
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    GroupEnum group = GroupEnum.Normal;

    @Column(length = 20)
    private String phone;

    /** 密码，此处应该是密码的哈希 */
    @UpdateTimestamp
    private String password;

    /** 提交的房源，删除用户，他提交的所有房源都会被删掉 */
    @OneToMany(mappedBy = "author", cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private List<House> houses;

    /** 用户的状态 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStateTypeEnum state = UserStateTypeEnum.NotVerify;

    /** 创建时间 */
    @CreatedDate
    private Date createTime;

    /** 修改时间 */
    @LastModifiedDate
    private Date modifyTime;
}
