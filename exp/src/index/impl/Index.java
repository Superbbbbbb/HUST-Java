package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;
import java.io.*;
import java.util.*;

/**
 * <pre>
 *     Index是AbstractIndex的实现类.
 *     倒排索引对象是解析一个文档对象的结果，包含docId和docPath之间的映射关系和Term和PostingList的映射关系
 * </pre>
 */
public class Index extends AbstractIndex implements FileSerializable {

    /**
     * 缺省构造函数
     */
    public Index() {}

    /**
     * 添加文档到索引，更新索引内部的HashMap
     * @param document 文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        Map<AbstractTerm, Posting> map = new TreeMap<>();
        this.docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
        for (AbstractTermTuple tuple : document.getTuples()) {
            if (!map.containsKey(tuple.term)) { //不包含这个单词
                List<Integer> list = new ArrayList<>();
                list.add(tuple.curPos);
                Posting posting = new Posting(document.getDocId(), 1, list); //新建posting对象
                map.put(tuple.term, posting);
            } else { //已经包含
                Posting posting = map.get(tuple.term);
                List<Integer> list = posting.getPositions();
                posting.setFreq(posting.getFreq() + 1); //设置出现次数
                list.add(tuple.curPos);
                posting.setPositions(list); //设置位置列表
            }
        }
        map.forEach((key, value) -> {
            if (this.termToPostingListMapping.containsKey(key)) { //已经包含该单词
                this.termToPostingListMapping.get(key).add(value);
            } else { //不包含
                AbstractPostingList postinglist = new PostingList();
                postinglist.add(value);
                this.termToPostingListMapping.put(key, postinglist); //添加该单词的信息列表
            }
        });
    }

    /**
     * 从索引文件里加载已经构建好的索引
     * @param file 索引文件
     * @exception IOError 文件打开与读取错误
     */
    @Override
    public void load(File file) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            this.readObject(in);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject
     * @param file 写入的目标索引文件
     * @exception IOError 文件打开与读取错误
     */
    @Override
    public void save(File file) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            this.writeObject(outputStream);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList
     * @param term 指定的单词
     * @return 指定单词的PostingList，如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {
        return termToPostingListMapping.get(term);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     * @return 索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return termToPostingListMapping.keySet();
    }

    /**
     * 对索引里每个单词的PostingList按docId从小到大排序,再对每个Posting里的positions从小到大排序
     */
    @Override
    public void optimize() {
        for (AbstractPostingList list : this.termToPostingListMapping.values()) {
            list.sort();
            for (int i = 0; i < list.size(); i++)
                list.get(i).sort();
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     * @param docId 文档id
     * @return 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return this.docIdToDocPathMapping.get(docId);
    }

    /**
     * 写到二进制文件
     * @param out 输出流对象
     * @exception IOError 输出错误
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.termToPostingListMapping);
            out.writeObject(this.docIdToDocPathMapping);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     * @param in 输入流对象
     * @exception IOError 输入错误
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.termToPostingListMapping = (Map<AbstractTerm, AbstractPostingList>) (in.readObject());
            this.docIdToDocPathMapping = (Map<Integer, String>) (in.readObject());
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
        }
    }

    /**
     * 返回索引的内容
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        String s = "docIdToDocPath:\n";
        for(int i = 0 ; i < docIdToDocPathMapping.size() ; i++){
            s = s.concat("  " + i + "-->" + docIdToDocPathMapping.get(i) + '\n');
        }

        s = s.concat("\ntermToPostingList:\n");
        Iterator<AbstractTerm> iter_key = this.getDictionary().iterator();
        Collection<AbstractPostingList> values = termToPostingListMapping.values();//迭代生成列表
        Iterator<AbstractPostingList> iter_value = values.iterator();
        while (iter_key.hasNext() && iter_value.hasNext())
            s = s.concat("  " + iter_key.next() + "-->" + iter_value.next() + "\n");
        return s;
    }

}
