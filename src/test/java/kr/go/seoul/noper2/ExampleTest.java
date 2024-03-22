package kr.go.seoul.noper2;


import kr.go.seoul.noper2.util.DTOConverter;
import kr.go.seoul.noper2.util.annotations.ExcelOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

//@Getter
//@Setter
//class TestDTO {
//    @ExcelOrder(order = 1, headerName="adsf")
//    private String fff;
//    @ExcelOrder(order = 2, headerName="대장번호")
//    private Integer ddd;
//}

@Slf4j
@SpringBootTest
public class ExampleTest {
//    @Test
//    void find() throws Exception {
//        TestDTO dto = new TestDTO();
//        dto.setFff("ㅇ강ㅁ");
//        dto.setDdd(123412);
//        HashMap<String, Object> map = DTOConverter.convertToHashMap(dto);
//        System.out.println(map);
//    }

//    @Autowired
//    BoardRepository boardRepository;
////    @Autowired
////    CustomUserDetailsService userDetailsService;
//    @Autowired
//    PasswordEncoder encoder;
//
//    @Test
//    void find() throws Exception {
//        Optional<TestBoardDTO> findBoard = boardRepository.findTestById(1L);
//        findBoard.orElseThrow(() -> new Exception("해당 객체가 존재하지 않습니다."));
//        log.debug("\n" +
//                findBoard.get().getNoticeSubject());
//    }
//    @Test
//    void save() {
//
//    }
//    @Test
//    void update() {
//
//    }
//    @Test
//    void delete() {
//
//    }
//    @Test
//    void testMapperCasting() {
//        Bjdong dto = Bjdong.builder().build();
//        var ss = changeType(dto, BjdongDTO.class);
//        System.out.println(ss);
//    }
//    @Test
//    void userDetailsServiceTestCase() {
////        UserDetails userDetails = userDetailsService.loadUserByUsername("A1");
////        System.out.println(userDetails.getAuthorities());
//    }
//    @Test
//    void createUserTestCase() {
//        String test = "1111";
//    }
}

