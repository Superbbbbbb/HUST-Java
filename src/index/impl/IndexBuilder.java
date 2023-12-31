package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.FileUtil;
import static hust.cs.javacourse.search.util.FileUtil.write;

import java.io.File;


/**
 * <pre>
 * IndexBuilder完成索引构造的工作
 * </pre>
 */
public class IndexBuilder extends AbstractIndexBuilder {

    /**
     * 构造函数
     * @param docBuilder Document对象
     */
    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
    }

    /**
     * 构建指定目录下的所有文本文件的倒排索引.
     * 遍历和解析文本文件, 得到对应的Document对象，依次加入到索引，并将索引保存到文件.
     * @param rootDirectory 指定目录
     * @return 构建好的索引
     */
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        AbstractIndex index = new Index();
        for(String path : FileUtil.list(rootDirectory)) {
            AbstractDocument doc = this.docBuilder.build(docId++, path, new File(path)); //构造document对象
            index.addDocument(doc); //构造倒排索引
        }
        index.save(new File(Config.INDEX_DIR + "index.dat")); //生成序列化文件
        write(index.toString(),Config.INDEX_DIR + "index.txt"); //生成可读的倒排索引内容文件
        return index;
    }

}
