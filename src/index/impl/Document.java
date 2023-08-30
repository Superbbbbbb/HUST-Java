package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.List;

/**
 * <pre>
 *     Document是AbstractDocument抽象类的具体实现.
 *     文档对象是解析一个文本文件的结果，包含文档id，文档的绝对路径和文档包含的三元组对象列表
 * </pre>
 */
public class Document extends AbstractDocument {

    /**
     * 缺省构造函数
     */
    public Document() {}

    /**
     * 构造函数
     * @param docId 文档id
     * @param docPath 文档绝对路径
     * @param tuples 解析得到的单词三元组
     */
    public Document(int docId, String docPath, List<AbstractTermTuple> tuples) {
        super(docId, docPath, tuples);
    }

    /**
     * 构造函数
     * @param docId 文档id
     * @param docPath 文档绝对路径
     */
    public Document(int docId, String docPath) {
        super(docId, docPath);
    }

    /**
     * 获得文档id
     * @return 文档id
     */
    @Override
    public int getDocId() {
        return this.docId;
    }

    /**
     * 设置文档id
     * @param docId 文档id
     */
    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    /**
     * 获得文档绝对路径
     * @return 文档绝对路径
     */
    @Override
    public String getDocPath() {
        return this.docPath;
    }

    /**
     * 设置文档绝对路径
     * @param docPath 文档绝对路径
     */
    @Override
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    /**
     * 获得文档包含的三元组列表
     * @return 单词三元组
     */
    @Override
    public List<AbstractTermTuple> getTuples() {
        return this.tuples;
    }

    /**
     * 向文档对象里添加三元组, 要求不能有内容重复的三元组
     * @param tuple 要添加的三元组
     */
    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if (!contains(tuple))
            this.tuples.add(tuple);
    }

    /**
     * 判断是否包含指定的三元组
     * @param tuple 指定的三元组
     * @return 如果包含指定的三元组，返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return this.tuples.contains(tuple);
    }

    /**
     * 获得指定下标位置的三元组
     * @param index 指定下标位置
     * @return 三元组
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        return this.tuples.get(index);
    }

    /**
     * 返回文档对象包含的三元组的个数
     * @return 文档对象包含的三元组的个数
     */
    @Override
    public int getTupleSize() {
        return this.tuples.size();
    }

    /**
     * 获得Document的内容
     * @return Document的字符串表示
     */
    @Override
    public String toString() {
        return "Document:\n" +
                "docId=" + docId + "\n" +
                "docPath=" + docPath + "\n" +
                "tuples=" + tuples + "\n";
    }

}