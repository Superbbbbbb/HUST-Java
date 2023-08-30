package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Map;

/**
 * <pre>
 *     Hit是AbstractHit抽象类的具体实现.
 *     命中对象是一个单词索引命中的结果
 * </pre>
 */
public class Hit extends AbstractHit {

    /**
     * 缺省构造函数
     */
    public Hit() {

    }

    /**
     * 构造函数
     * @param docId 文档id
     * @param docPath 文档绝对路径
     */
    public Hit(int docId, String docPath) {
        super(docId, docPath);
    }

    /**
     * 构造函数
     * @param docId 文档id
     * @param docPath 文档绝对路径
     * @param termPostingMapping 命中的三元组列表
     */
    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping) {
        super(docId, docPath, termPostingMapping);
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
     * 获得文档绝对路径
     * @return 文档绝对路径
     */
    @Override
    public String getDocPath() {
        return this.docPath;
    }

    /**
     * 获得文档内容
     * @return 文档内容
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * 设置文档内容
     * @param content 文档内容
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获得文档得分
     * @return 文档得分
     */
    @Override
    public double getScore() {
        return this.score;
    }

    /**
     * 设置文档得分
     * @param score 文档得分
     */
    @Override
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * 获得命中的单词和对应的Posting键值对
     * @return 命中的单词和对应的Posting键值对
     */
    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return this.termPostingMapping;
    }

    /**
     * 获得命中结果, 用于显示搜索结果
     * @return 命中结果的字符串表示
     */
    @Override
    public String toString() {
        return "docId=" + docId +
                "\ndocPath=" + docPath +
                "\nscore=" + score +
                "\ntermPostingMapping=" + termPostingMapping +
                "\ncontent=" + content;
    }

    /**
     * 比较二个命中结果的大小，根据score比较
     * @param o 要比较的名字结果
     * @return 两个命中结果得分的差值
     */
    @Override
    public int compareTo(AbstractHit o) {
        double dif = this.getScore() - o.getScore();
        if (dif != 0)
            return (int)dif;
        else
            return this.getDocId() - o.getDocId();
    }

}
