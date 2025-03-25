package com.shy.entity;

import com.shy.mapper.DocumentMapper;
import com.shy.util.EmbeddingsUtil;
import com.shy.util.EsQueryUtil;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.easyes.core.kernel.EsWrappers;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


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
        document.setTitleVector(EmbeddingsUtil.getEmbeddings("Java是世界上最好的语言"));
        document.setContentVector(EmbeddingsUtil.getEmbeddings("Java 语言的语法与 C 语言和 C++ 语言很接近，使得大多数程序员很容易学习和使用。另一方面，Java 丢弃了 C++ 中很少使用的、很难理解的、令人迷惑的那些特性，如操作符重载、多继承、自动的强制类型转换。特别地，Java 语言不使用指针，而是引用。并提供了自动分配和回收内存空间，使得程序员不必为内存管理而担忧"));
        // 调用Mapper的插入方法，将文档对象插入到数据源，并获取插入成功的数量
        int successCount = documentMapper.insert(document);
        // 打印插入成功的数量
        System.out.println(successCount);
    }

    @Test
    @Order(4)
    @Disabled
    void testSelect(){
        String title = "测试";
        Document document = EsWrappers.lambdaChainQuery(documentMapper)
                .eq(Document::getTitle, title)
                .one();
        System.out.println(document);
        Assertions.assertNotNull(document);
    }

    @Test
    @Order(5)
    @Disabled
    void testUpdate(){
        String title = "测试文档123";
        Document document = EsWrappers.lambdaChainQuery(documentMapper)
                .eq(Document::getTitle, "测试")
                .one();
        document.setTitle(title);
        Integer updateCount = documentMapper.updateById(document);
        Assertions.assertEquals(1, updateCount);
    }

    @Test
    @Order(6)
    void testMultiMatch() {
        String text = "测试文档";
        LambdaEsQueryWrapper<Document> wrapper = EsWrappers.lambdaQuery(Document.class).multiMatchQuery(text, Document::getTitle, Document::getContent);
        List<Document> documents = documentMapper.selectList(wrapper);
        System.out.println(documents);
    }

    @Test
    @Order(7)
    void testVectorQuery() {
        String text = "测试文档";
        double[] titleVector = EmbeddingsUtil.getEmbeddings("有没有什么容易学习的编程语言");
        double[] contentVector = EmbeddingsUtil.getEmbeddings("Java 语言的语法与 C 语言和 C++ 语言很接近");
        String field = "titleVector";
        String field2 = "contentVector";
        List<String> fields = Arrays.asList(field, field2);
        List<double[]> vectors = Arrays.asList(titleVector, contentVector);
        LambdaEsQueryWrapper<Document> wrapper = EsWrappers.lambdaQuery(Document.class);
        wrapper.setSearchSourceBuilder(new SearchSourceBuilder().query(EsQueryUtil.vectorsQuery(fields, vectors)));
        List<Document> documents = documentMapper.selectList(wrapper);
        System.out.println(documents);
    }
}