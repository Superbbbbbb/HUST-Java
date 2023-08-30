package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;

/**
 * <pre>
 *     TermTuple是AbstractTermTuple抽象类的具体实现.
 *     一个TermTuple对象为三元组(单词，出现频率，出现的当前位置).
 *     当解析一个文档时，每解析到一个单词，应该产生一个三元组，其中freq始终为1(因为单词出现了一次).
 * </pre>
 */
public class TermTuple extends AbstractTermTuple {

    /**
     * 缺省构造函数
     */
    public TermTuple() {}

    /**
     * 构造函数
     * @param term Term
     * @param pos position
     */
    public TermTuple(Term term, int pos) {
        this.term = term;
        this.curPos = pos;
    }

    /**
     * 比较obj是否等于实例
     * @param obj 要比较的另外一个三元组
     * @return 如果等于返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermTuple) {
            return ((TermTuple) obj).curPos == this.curPos
                    && ((TermTuple) obj).term.getContent().equals(this.term.getContent());
        }
        return false;
    }

    /**
     * 获取TermTuple的内容
     * @return TermTuple的字符串表示
     */
    @Override
    public String toString() {
        return "TermTuple: " +
                "term=" + term +
                ", freq=" + freq +
                ", curPos=" + curPos + '\n';
    }

}
