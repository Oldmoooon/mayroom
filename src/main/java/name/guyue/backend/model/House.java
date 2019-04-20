package name.guyue.backend.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import name.guyue.backend.enums.BedroomTypeEnum;
import name.guyue.backend.enums.ElectricTypeEnum;
import name.guyue.backend.enums.GasTypeEnum;
import name.guyue.backend.enums.GenderTypeEnum;
import name.guyue.backend.enums.HeatingTypeEnum;
import name.guyue.backend.enums.HouseStateTypeEnum;
import name.guyue.backend.enums.RentLiveTypeEnum;
import name.guyue.backend.enums.RentTypeEnum;
import name.guyue.backend.enums.WaterTypeEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author hujia
 * @date 2019-03-25
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@NoArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 房源提交者，删除房源，不影响提交者，不可以为空 */
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional=false)
    private User author;

    /** 房源状态 */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private HouseStateTypeEnum state = HouseStateTypeEnum.NotVerify;

    /** 创建时间 */
    @CreatedDate
    private Date createTime;

    /** 修改时间 */
    @LastModifiedDate
    private Date modifyTime;

    /** 备注信息 */
    private String comment;

    /** 小区 */
    private String community;

    /** 区域 */
    private String region;

    /** 环线 */
    private String loopLine;

    /** 合租方式 */
    private RentLiveTypeEnum rentLiveType;

    /** 卧室类型 */
    private BedroomTypeEnum bedroomType;

    /** 面积 */
    @Column(precision=12, scale=2)
    private BigDecimal area;

    /** 有无电梯 */
    private boolean elevator;

    /** 户型，一室一厅一卫之类的描述 */
    private String flatType;

    /** 朝向 */
    private String orientation;

    /** 楼层，形似"15/23"或其他 */
    private String floor;

    /** 供暖 */
    private HeatingTypeEnum heatingType;

    /** 供水 */
    private WaterTypeEnum waterType;

    /** 供电 */
    private ElectricTypeEnum electricType;

    /** 供燃气 */
    private GasTypeEnum gasType;

    /** 地铁 */
    private String subway;

    /** 公交 */
    private String bus;

    /** 公交距离说明，出门到地铁/公交得多久之类 */
    private String arrival;

    /** 租金类型 */
    private RentTypeEnum rentType;

    /** 平均月租金 */
    private Integer monthlyPrice;

    /** 看房时间 */
    private String lookHomeTime;

    /** 入住时间 */
    private String checkInTime;

    /** 性别要求 */
    private GenderTypeEnum gender;

    /** 联系人 */
    private String contact;

    /** 联系电话 */
    private String contactTel;

    /** 联系微信 */
    private String contactVx;

    /** 房屋标签 */
    private String houseTags;

    /** 房屋优点 */
    private String merits;

    /** 房屋缺点 */
    private String demerits;

    /** 对租客的要求 */
    private String requirement;

    /** 图片 */
    @ElementCollection
    @CollectionTable(name = "house_photos")
    private List<String> photos;
}
