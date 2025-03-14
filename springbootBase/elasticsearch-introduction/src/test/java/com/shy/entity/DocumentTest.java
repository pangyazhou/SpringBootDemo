package com.shy.entity;

import com.shy.mapper.DocumentMapper;
import org.dromara.easyes.core.kernel.EsWrappers;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DocumentTest {
    @Resource
    private DocumentMapper documentMapper;

    @Test
    @Order(1)
    void testDeleteIndex(){
        // 删除索引并返回操作是否成功
        boolean success = documentMapper.deleteIndex("document");
        // 断言索引删除成功，以验证功能的正确性
        Assertions.assertTrue(success);
    }

    /**
     * 测试创建索引的功能
     * 此测试验证了DocumentMapper的createIndex方法是否能成功创建一个索引
     * 使用断言来确保createIndex方法返回成功，从而验证索引创建的功能是否正常
     */
    @Test
    @Order(2)
    void testCreateIndex(){
        // 创建索引并返回操作是否成功
        boolean success = documentMapper.createIndex();
        // 断言索引创建成功，以验证功能的正确性
        Assertions.assertTrue(success);
    }

    @Test
    @Order(3)
    void testInsert(){
        // 创建一个新的文档对象
        Document document = new Document();
        // 设置文档的唯一标识符
        document.setId("1");
        // 设置文档的标题
        document.setTitle("测试文档");
        // 设置文档的内容
        document.setContent("这是一个测试文档");
        // 调用Mapper的插入方法，将文档对象插入到数据源，并获取插入成功的数量
        int successCount = documentMapper.insert(document);
        // 打印插入成功的数量
        System.out.println(successCount);
    }

    @Test
    @Order(4)
    void testSelect(){
        String title = "测试文档";
        Document document = EsWrappers.lambdaChainQuery(documentMapper)
                .eq(Document::getTitle, title)
                .one();
        System.out.println(document);
        Assertions.assertNotNull(document);
        Assertions.assertEquals(title, document.getTitle());
    }

    @Test
    @Order(5)
    void testUpdate(){
        String title = "测试文档123";
        Document document = EsWrappers.lambdaChainQuery(documentMapper)
                .eq(Document::getTitle, "测试文档")
                .one();
        document.setTitle(title);
        Integer updateCount = documentMapper.updateById(document);
        Assertions.assertEquals(1, updateCount);
    }

}