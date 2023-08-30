package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.FileSerializable;

import java.io.*;

/**
 * <pre>
 *     Term是AbstractTerm抽象类的具体实现.
 *     Term对象表示文本文档里的一个单词.
 * </pre>
 */
public class Term extends AbstractTerm implements Comparable<AbstractTerm>, FileSerializable {

    /**
     * 缺省构造函数
     */
    public Term() {}

    /**
     * 构造函数
     * @param content Term内容
     */
    public Term(String content) {
        this.content = content;
    }

    /**
     * 判断二个Term内容是否相同
     * @param obj 要比较的另外一个Term
     * @return 如果内容相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Term) {
            return ((Term) obj).content.equals(this.content);
        }
        return false;
    }

    /**
     * 返回Term的字符串表示
     * @return 字符串
     */
    @Override
    public String toString() {
        return this.content;
    }

    /**
     * 返回Term内容
     * @return Term内容
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * 设置Term内容
     * @param content Term的内容
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 比较二个Term大小
     * @param o 要比较的Term对象
     * @return 返回二个Term对象的字典序差值
     */
    @Override
    public int compareTo(AbstractTerm o) {
        return this.getContent().compareTo(o.getContent());
    }

    /**
     * 写到二进制文件
     * @param out 输出流对象
     * @exception IOError 输出错误
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.content);
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
            this.content = (String)in.readObject();
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
        }
    }

}
