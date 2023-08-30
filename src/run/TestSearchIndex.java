package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.impl.IndexSearcher;
import hust.cs.javacourse.search.query.impl.SimpleSorter;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StopWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 测试搜索
 */
public class TestSearchIndex {

    /**
     * 搜索程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args){
        AbstractIndexSearcher indexSearcher = new IndexSearcher();
        indexSearcher.open(Config.INDEX_DIR + "index.dat");
        SimpleSorter sorter = new SimpleSorter();
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        AbstractTerm queryTerm1;
        AbstractTerm queryTerm2;
        AbstractHit[] Hits;
        while (true){
            System.out.println("1. search articles with one word");
            System.out.println("2. search articles with two words(AND)");
            System.out.println("3. search articles with two words(OR)");
            System.out.println("4. search articles with phrase");
            System.out.println("0. exit");
            System.out.print("Input your option: ");
            switch (scanner1.nextInt()) {
                case 1:
                    System.out.print("Input The Word: ");
                    queryTerm1 = new Term(scanner2.next());
                    if (legal(queryTerm1)){
                        Hits = indexSearcher.search(queryTerm1, sorter);
                        Print(Hits);
                    }
                    break;
                case 2 :
                    System.out.print("Input The Two Words: ");
                    queryTerm1 = new Term(scanner2.next());
                    queryTerm2 = new Term(scanner2.next());
                    if (legal(queryTerm1) && legal(queryTerm2)) {
                        Hits = indexSearcher.search(queryTerm1, queryTerm2, sorter, AbstractIndexSearcher.LogicalCombination.AND);
                        Print(Hits);
                    }
                    break;
                case 3 :
                    System.out.print("Input The Two Words: ");
                    queryTerm1 = new Term(scanner2.next());
                    queryTerm2 = new Term(scanner2.next());
                    if (legal(queryTerm1) && legal(queryTerm2)) {
                        Hits = indexSearcher.search(queryTerm1, queryTerm2, sorter, AbstractIndexSearcher.LogicalCombination.OR);
                        Print(Hits);
                    }
                    break;
                case 4 :
                    System.out.print("Input The phrase: ");
                    queryTerm1 = new Term(scanner2.next());
                    queryTerm2 = new Term(scanner2.next());
                    if (legal(queryTerm1) && legal(queryTerm2)) {
                        Hits = indexSearcher.search(queryTerm1, queryTerm2, sorter, AbstractIndexSearcher.LogicalCombination.PHR);
                        Print(Hits);
                    }
                    break;
                case 0 : return;
                default: System.out.println("Input Error!");
            }
        }
    }
    /**
     * 输出结果
     * @param Hits 查找到的内容
     */
    public static void Print(AbstractHit[] Hits) {
        if (Hits.length != 0) {
            for (AbstractHit hit : Hits) {
                System.out.println("=================================================================================================================================================");
                System.out.println(hit);
            }
        } else {
            System.out.println("Not Found!");
        }
        System.out.println("=================================================================================================================================================");
    }

    /**
     * 判断该term是否非法
     * @param term 输入的term
     * @return 该term是否非法
     */
    public static boolean legal(AbstractTerm term) {
        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList(StopWords.STOP_WORDS));
        String t = term.getContent();
        boolean flag = stopWords.contains(t)
                || t.length() > Config.TERM_FILTER_MAXLENGTH
                || t.length() < Config.TERM_FILTER_MINLENGTH
                || !t.matches(Config.TERM_FILTER_PATTERN);
        if (flag) {
            System.out.println("\"" + t + "\" is Illegal");
        }
        return !flag;
    }

}
