package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.FileSerializable;

import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.List;

/**
 * <pre>
 *     Posting是AbstractPosting抽象类的具体实现.
 *     Posting对象代表倒排索引里每一项
 *     一个Posting对象包括:包含单词的文档id，出现的次数和位置列表
 *  </pre>
 */
public class Posting extends AbstractPosting implements Comparable<AbstractPosting>, FileSerializable {

    /**
     * 缺省构造函数
     */
    public Posting(){}

    /**
     * 构造函数
     * @param docId 包含单词的文档id
     * @param freq 单词在文档里出现的次数
     * @param positions 单词在文档里出现的位置
     */
    public Posting(int docId, int freq, List<Integer> positions) {
        this.docId = docId;
        this.freq = freq;
        this.positions = positions;
        this.sort();
    }

    /**
     * 判断两个Posting内容是否相同
     * @param obj 要比较的另外一个Posting
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Posting) {
            return ((Posting) obj).docId == this.docId
                    && ((Posting) obj).freq == this.freq
                    && ((Posting) obj).positions.equals(this.positions);
        }
        return false;
    }

    /**
     * 返回包含单词的文档id
     * @return 文档id
     */
    @Override
    public int getDocId() {
        return this.docId;
    }

    /**
     * 设置包含单词的文档id
     * @param docId 包含单词的文档id
     */
    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    /**
     * 返回单词在文档里出现的次数
     * @return 出现次数
     */
    @Override
    public int getFreq() {
        return this.freq;
    }

    /**
     * 设置单词在文档里出现的次数
     * @param freq 出现次数
     */
    @Override
    public void setFreq(int freq) {
        if (freq > 0)
            this.freq = freq;
    }

    /**
     * 返回单词在文档里出现的位置列表
     * @return 位置列表
     */
    @Override
    public List<Integer> getPositions() {
        return this.positions;
    }

    /**
     * 设置单词在文档里出现的位置列表
     * @param positions 单词在文档里出现的位置列表
     */
    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    /**
     * 比较二个Posting对象的大小（根据docId）
     * @param o 另一个Posting对象
     * @return 二个Posting对象的docId的差值
     */
    @Override
    public int compareTo(AbstractPosting o) {
        return this.getDocId() - o.getDocId();
    }

    /**
     * 对内部positions从小到大排序
     */
    @Override
    public void sort() {
        this.positions.sort(Comparator.naturalOrder());
    }

    /**
     * 写到二进制文件
     * @param out 输出流对象
     * @exception IOError 输出错误
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.docId);
            out.writeObject(this.freq);
            out.writeObject(this.positions);
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
            this.docId = (int) (in.readObject());
            this.freq = (int) (in.readObject());
            this.positions = (List<Integer>) (in.readObject());
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
        }
    }

    /**
     * 返回Posting的内容
     * @return Posting的字符串表示
     */
    @Override
    public String toString() {
        return "(docId=" + docId + ",freq=" + freq + ",positions=" + positions + ")";
    }
}
