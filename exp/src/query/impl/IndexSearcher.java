package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

/**
 * <pre>
 *     IndexSearcher是IndexSearcher抽象类的具体实现.
 *     IndexSearcher实现对Index进行搜索的功能
 * </pre>
 */
public class IndexSearcher extends AbstractIndexSearcher {

    /**
     * 缺省构造函数
     */
    public IndexSearcher() {}

    /**
     * 从指定索引文件打开索引，加载到index对象里
     * @param indexFile 指定索引文件
     */
    @Override
    public void open(String indexFile) {
        //System.out.println("Loading...");
        this.index.load(new File(indexFile));
        this.index.optimize();
        //System.out.println(this.index);
        //System.out.println("Load Finished!\n");
    }

    /**
     * 根据单个检索词进行搜索
     * @param queryTerm 检索词
     * @param sorter 排序器
     * @return 命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList = this.index.search(queryTerm);//获得三元组列表
        if (postingList != null) {
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            AbstractHit[] hits = new AbstractHit[postingList.size()];
            for (int i = 0; i < postingList.size(); i++) {
                AbstractPosting posting = postingList.get(i);
                termPostingMapping.put(queryTerm, posting); //加入新的对应关系
                hits[i] = new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping);//生成hit对象
                hits[i].setScore(sorter.score(hits[i]));
                termPostingMapping.clear(); //清空哈希表
            }
            sorter.sort(Arrays.asList(hits));
            return hits;
        }
        return null;
    }

    /**
     * 根据二个检索词进行搜索
     * @param queryTerm1 第1个检索词
     * @param queryTerm2 第2个检索词
     * @param sorter 排序器
     * @param combine 多个检索词的逻辑组合方式
     * @return 命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postingList1 = this.index.search(queryTerm1);
        AbstractPostingList postingList2 = this.index.search(queryTerm2);
        Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
        ArrayList<AbstractHit> hits = new ArrayList<>();
        if (combine.equals(LogicalCombination.OR)) {    //或搜索
            if (postingList1 != null) {
                for (int i = 0; i < postingList1.size(); i++) {
                    AbstractPosting posting = postingList1.get(i);
                    termPostingMapping.put(queryTerm1, posting);
                    hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping));
                    hits.get(i).setScore(sorter.score(hits.get(i)));//设置得分
                    termPostingMapping.clear();
                }
            }
            if (postingList2 != null) {
                for (int i = 0; i < postingList2.size(); i++) {
                    boolean flag = false;
                    AbstractPosting posting = postingList2.get(i);
                    for (AbstractHit hit : hits) {
                        if (hit.getDocId() == posting.getDocId()) {//两个单词出现在同一文件中
                            hit.getTermPostingMapping().put(queryTerm2, posting);
                            hit.setScore(sorter.score(hit));
                            flag = true;
                        }
                    }
                    if (!flag) { //不在同一文件，构造新的hit对象
                        termPostingMapping.put(queryTerm2, posting);
                        hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping));
                        AbstractHit hit = hits.get(hits.size() - 1);
                        hit.setScore(sorter.score(hit));
                        termPostingMapping.clear();
                    }
                }
            }
        } else {
            if (postingList1 != null && postingList2 != null) {
                for (int i = 0; i < postingList1.size(); i++) {
                    AbstractPosting posting1 = postingList1.get(i);
                    for (int j = 0; j < postingList2.size(); j++) {
                        AbstractPosting posting2 = postingList2.get(j);
                        if (posting1.getDocId() == posting2.getDocId()) {//出现在同一文档中
                            double score = 0; //记录短语搜索的得分
                            if (combine.equals(LogicalCombination.PHR)) { //短语搜索
                                for (int position1 : posting1.getPositions()){
                                    for (int position2 : posting2.getPositions()){
                                        if (position1 + 1 == position2 ){ //单词连续，是短语
                                            score += 1.0;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (combine.equals(LogicalCombination.AND) || score != 0) {//与搜索或查找到短语
                                termPostingMapping.put(queryTerm1, posting1);
                                termPostingMapping.put(queryTerm2, posting2);
                                hits.add(new Hit(posting1.getDocId(), this.index.getDocName(posting1.getDocId()), termPostingMapping));
                                if (combine.equals(LogicalCombination.AND))
                                    hits.get(i).setScore(sorter.score(hits.get(i)));//与搜索得分
                                else hits.get(i).setScore(score);//短语搜索得分
                                termPostingMapping.clear();
                            }
                        }
                    }
                }
            }
        }
        sorter.sort(hits); //对命中的单词列表进行排序
        int size = hits.size();
        return hits.toArray(new AbstractHit[size]); //转换为数组
    }
}
