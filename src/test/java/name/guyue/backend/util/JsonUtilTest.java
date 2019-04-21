package name.guyue.backend.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;
import name.guyue.backend.enums.HouseStateTypeEnum;
import name.guyue.backend.model.House;
import org.assertj.core.util.Maps;
import org.junit.Test;

public class JsonUtilTest {

    @Test public void merge() {
        House house = new House();
        house.setState(HouseStateTypeEnum.NotVerify);
        house.setArea(BigDecimal.TEN);
        HouseStateTypeEnum verified = HouseStateTypeEnum.Verified;
        Map<String, Object> map = Maps.newHashMap("state", verified);
        House merge = JsonUtil.merge(house, map, House.class);

        assertEquals(merge.getState(), verified);
        assertEquals(merge.getArea().intValue(), house.getArea().intValue());
    }
}