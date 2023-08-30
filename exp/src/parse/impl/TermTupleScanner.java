package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     TermTupleScanner是AbstractTermTupleScanner抽象类的具体实现.
 * </pre>
 */

public class TermTupleScanner extends AbstractTermTupleScanner {

    /**
     * 记录位置
     */
    private int curPos = 0;

    /**
     * TermTuple的集合
     */
    private List<TermTuple> tuples = new ArrayList<>();

    /**
     * 构造函数
     * @param input BufferedReader对象
     * @exception IOError 输入错误
     */
    public TermTupleScanner(BufferedReader input) {
        super(input);
        try {
            String str = input.readLine();
            while (str != null) {
                StringSplitter splitter = new StringSplitter();
                splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
                List<String> parts = splitter.splitByRegex(str);
                for (String s : parts)
                    if (!s.equals(""))
                        this.tuples.add(new TermTuple(new Term(s.toLowerCase()), this.curPos++));
                str = input.readLine();
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * 获得下一个三元组
     * @return 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        if (this.tuples.size()!=0){
            AbstractTermTuple tuple = this.tuples.get(0);
            tuples.remove(0);
            return tuple;
        } else {
            return null;
        }
    }

    /**
     * 关闭流
     */
    @Override
    public void close() {
        super.close();
    }
}
