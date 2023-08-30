package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.FileSerializable;

import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.List;
import java.lang.ClassNotFoundException;

/**
 * <pre>
 *     PostingList是AbstractPostingList抽象类的具体实现.
 *     PostingList对象包含一个单词的Posting列表.
 * </pre>
 */
public class PostingList extends AbstractPostingList implements FileSerializable {

    /**
     * 缺省构造函数
     */
    public PostingList() {}

    /**
     * 获得PosingList的内容
     * @return PosingList的字符串表示
     */
    @Override
    public String toString() {
        return list.toString();
    }

    /**
     * 添加Posting,要求不能有内容重复的posting
     * @param posting Posting对象
     */
    @Override
    public void add(AbstractPosting posting) {
        if (!this.contains(posting))
            this.list.add(posting);
    }

    /**
     * 添加Posting列表,,要求不能有内容重复的posting
     * @param postings Posting列表
     */
    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting : postings)
            if (!this.list.contains(posting))
                this.list.add(posting);
    }

    /**
     * 返回指定下标位置的Posting
     * @param index 下标
     * @return 指定下标位置的Posting
     */
    @Override
    public AbstractPosting get(int index) {
        return list.get(index);
    }

    /**
     * 返回指定Posting对象的下标
     * @param posting 指定的Posting对象
     * @return 如果找到返回对应下标；否则返回-1
     */
    @Override
    public int indexOf(AbstractPosting posting) {
        return list.indexOf(posting);
    }

    /**
     * 返回指定文档id的Posting对象的下标
     * @param docId 文档id
     * @return 如果找到返回对应下标；否则返回-1
     */
    @Override
    public int indexOf(int docId) {
        for (int i = 0; i < this.list.size(); i++)
            if (this.list.get(i).getDocId() == docId)
                return i;
        return -1;
    }

    /**
     * 是否包含指定Posting对象
     * @param posting 指定的Posting对象
     * @return 如果包含返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractPosting posting) {
        for (AbstractPosting p : this.list)
            if (p.getDocId() == posting.getDocId())
                return p.equals(posting);
        return false;
    }

    /**
     * 删除指定下标的Posting对象
     * @param index 下标
     */
    @Override
    public void remove(int index) {
        if (index >= 0 && index < this.list.size())
            this.list.remove(index);
    }

    /**
     * 删除指定的Posting对象
     * @param posting 定的Posting对象
     */
    @Override
    public void remove(AbstractPosting posting) {
        this.list.remove(posting);
    }

    /**
     * 返回PostingList的大小，即包含的Posting的个数
     * @return PostingList的大小
     */
    @Override
    public int size() {
        return this.list.size();
    }

    /**
     * 清除PostingList
     */
    @Override
    public void clear() {
        this.list.clear();
    }

    /**
     * PostingList是否为空
     * @return 为空返回true，否则返回false
     */
    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    /**
     * 根据文档id的大小对PostingList进行从小到大的排序
     */
    @Override
    public void sort() {
        this.list.sort(Comparator.naturalOrder());
    }

    /**
     * 写到二进制文件
     * @param out 输出流对象
     * @exception IOError 输出错误
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.list);
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
            this.list = (List<AbstractPosting>) (in.readObject());
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
        }
    }

}
