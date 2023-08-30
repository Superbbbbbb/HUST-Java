package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.IndexBuilder;
import hust.cs.javacourse.search.util.Config;

import java.io.File;

/**
 * 测试索引构建
 */
public class TestBuildIndex {
    /**
     * 索引构建程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args){
        System.out.println("Building...");
        AbstractIndex index = new IndexBuilder(new DocumentBuilder()).buildIndex(Config.DOC_DIR);
        index.optimize();
        index.save(new File(Config.INDEX_DIR + "index.dat")); //索引保存到文件
        System.out.println(index);
        System.out.println("Build Finished!");
    }
}
