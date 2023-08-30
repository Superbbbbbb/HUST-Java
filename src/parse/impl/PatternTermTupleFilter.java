package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

/**
 * <pre>
 *     PatternTermTupleFilter是AbstractTermTupleFilter抽象类的具体实现.
 *     PatternTermTupleFilter过滤不全由字母组成的Term
 * </pre>
 */
public class PatternTermTupleFilter extends AbstractTermTupleFilter {

    /**
     * 构造函数
     * @param input 输入流对象
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 获得下一个三元组
     * 过滤
     * @return 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null) return null;
        while (!termTuple.term.getContent().matches(Config.TERM_FILTER_PATTERN)) {
            termTuple = input.next();
            if (termTuple == null) return null;
        }
        return termTuple;
    }
}
