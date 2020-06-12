//package test;
//
//import com.leyou.item.pojo.Category;
//import com.zy.item.service.CategoryService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Arrays;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MapperTest {
//
//    @Autowired
//    private CategoryService categoryService;
//    @Test
//    public  void test(){
//        List<Category> list = categoryService.queryByIds(Arrays.asList(74L, 75L, 76L));
//        for (Category category : list) {
//            System.out.println("category = " + category);
//        }
//    }
//
//}
