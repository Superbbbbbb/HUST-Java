package hust.cs.javacourse.search.query.impl;


import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.*;

import static java.util.Comparator.reverseOrder;

/**
 * <pre>
 *     SimpleSort定义了对搜索结果排序
 * </pre>
 */
public class SimpleSorter implements hust.cs.javacourse.search.query.Sort {

    /**
     * 对命中结果集合根据文档得分排序
     * @param hits 命中结果集合
     */
    @Override
    public void sort(List<AbstractHit> hits) {
        hits.sort(reverseOrder());
    }

    /**
     * 计算命中文档的得分, 作为命中结果排序的依据.
     * @param hit 命中文档
     * @return 命中文档的得分
     */
    @Override
    public double score(AbstractHit hit) {
        double score = 0;
        for (AbstractTerm key : hit.getTermPostingMapping().keySet())
            score += hit.getTermPostingMapping().get(key).getFreq(); //所有命中结果的出现次数相加
        return score;
    }
}
