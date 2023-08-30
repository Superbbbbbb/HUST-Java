package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;

/**
 * <pre>
 *     DocumentBuilder是AbstractDocumentBuilder的实现类.
 *     DocumentBuilder的功能是由解析文本文档得到的TermTupleStream，文档id和绝对路径产生Document对象.
 * </pre>
 */
public class DocumentBuilder extends AbstractDocumentBuilder {

    /**
     * 缺省构造函数
     */
    public DocumentBuilder() {}

    /**
     * 由解析文本文档得到的TermTupleStream,构造Document对象.
     * @param docId 文档id
     * @param docPath 文档绝对路
     * @param termTupleStream 文档对应的TermTupleStream
     * @return Document对象
     */
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        AbstractDocument doc = new Document(docId, docPath);
        AbstractTermTuple termTuple = termTupleStream.next();
        while (termTuple != null) {
            doc.addTuple(termTuple);
            termTuple = termTupleStream.next();
        }
        termTupleStream.close();
        return doc;
    }

    /**
     * 利用输入参数file构造出AbstractTermTupleStream子类对象
     * 调用build(int docId, String docPath, AbstractTermTupleStream termTupleStream)构造document对象
     * @param docId 文档id
     * @param docPath 文档绝对路径
     * @param file 文档对应File对象
     * @return Document对象
     * @exception java.nio.file.FileSystemNotFoundException 文档未找到
     */
    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        AbstractTermTupleStream termTupleStream = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            termTupleStream = new StopWordTermTupleFilter(new LengthTermTupleFilter(new PatternTermTupleFilter(new TermTupleScanner(reader))));//过滤
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        }
        assert termTupleStream != null;
        return this.build(docId, docPath, termTupleStream);
    }
}
