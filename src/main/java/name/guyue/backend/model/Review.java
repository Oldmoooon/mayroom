package name.guyue.backend.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import name.guyue.backend.enums.ReviewType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 审核的实体，入群审核/房源审核等
 *
 * @author hujia
 * @date 2019-03-31
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewType type;

    /**
     * 内容，用来存放审核目标的id
     */
    private String content;

    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    private Date modifyTime;

    /**
     * 提交人
     */
    @CreatedBy
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    private User committer;

    /**
     * 审核人
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    private User auditor;
}
