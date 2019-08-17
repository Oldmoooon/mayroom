package name.guyue.backend.config;

import java.math.BigDecimal;
import java.util.Optional;
import name.guyue.backend.db.HouseRepository;
import name.guyue.backend.db.UserRepository;
import name.guyue.backend.enums.BedroomTypeEnum;
import name.guyue.backend.enums.GroupEnum;
import name.guyue.backend.enums.HouseStateTypeEnum;
import name.guyue.backend.enums.UserStateTypeEnum;
import name.guyue.backend.model.House;
import name.guyue.backend.model.User;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 用来造一些假数据
 *
 * @author hujia
 * @date 2019-04-20
 */
@Component
@Order(value = 1)
public class AutoRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public AutoRunner(UserRepository repository, HouseRepository houseRepository) {
        this.userRepository = repository;
        this.houseRepository = houseRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        // May账号
        Optional<User> may = userRepository.findById(1L);
        User user;
        if (may.isEmpty()) {
            user = new User();
            user.setState(UserStateTypeEnum.Verified);
            user.setGroup(GroupEnum.May);
            user.setPassword("password");
            userRepository.save(user);
        } else {
            user = may.get();
        }

        // 两个房源
        House house1 = new House();
        House house2 = new House();
        house1.setAuthor(user);
        house2.setAuthor(user);
        house1.setArea(BigDecimal.ONE);
        house2.setArea(BigDecimal.TEN);
        house1.setArrival("十分钟到公交站");
        house2.setArrival("半小时到地铁站");
        house1.setBedroomType(BedroomTypeEnum.First);
        house2.setBedroomType(BedroomTypeEnum.Second);
        house1.setLoopLine("东三环");
        house2.setLoopLine("北四环");
        house1.setCommunity("古月家园");
        house2.setCommunity("香格里拉");
        house1.setRegion("朝阳区");
        house2.setRegion("宣武区");
        house1.setContact("张先生");
        house2.setContact("李小姐");
        house1.setContactTel("+86-12345678910");
        house1.setContactTel("13991348525");
        house1.setState(HouseStateTypeEnum.Verified);
        house2.setState(HouseStateTypeEnum.NotVerify);
        houseRepository.save(house1);
        houseRepository.save(house2);
    }
}
