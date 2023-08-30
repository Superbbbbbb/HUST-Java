package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 *     StopWordTermTupleFilter是AbstractTermTupleFilter抽象类的具体实现.
 *     StopWordTermTupleFilter过滤停顿词
 * </pre>
 */
public class StopWordTermTupleFilter extends AbstractTermTupleFilter {

    private final List<String> stopWord;

    /**
     * 构造函数
     * @param input 输入流
     */
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        this.stopWord = new ArrayList<>(Arrays.asList(StopWords.STOP_WORDS));//保存stop_words到字符串列表
    }

    /**
     * 获得下一个三元组,过滤停等词
     * @return 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null) return null;
        while (stopWord.contains(termTuple.term.getContent())) {
            termTuple = input.next();
            if (termTuple == null) return null;
        }
        return termTuple;
    }

}
