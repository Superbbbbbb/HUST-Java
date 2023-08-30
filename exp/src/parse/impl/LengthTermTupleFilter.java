package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

/**
 * <pre>
 *     LengthTermTupleFilter是AbstractTermTupleFilter抽象类的具体实现.
 *     LengthTermTupleFilter过滤长度过长或过短的Term
 * </pre>
 */
public class LengthTermTupleFilter extends AbstractTermTupleFilter {

    /**
     * 构造函数
     * @param input : 输入流对象
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
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
        while (termTuple.term.getContent().length() > Config.TERM_FILTER_MAXLENGTH || termTuple.term.getContent().length()<Config.TERM_FILTER_MINLENGTH){
            termTuple = input.next();
            if (termTuple == null) return null;
        }
        return termTuple;
    }

}
