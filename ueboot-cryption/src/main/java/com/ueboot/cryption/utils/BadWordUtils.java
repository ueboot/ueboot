package com.ueboot.cryption.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 参考DFA算法demo:http://blog.csdn.net/chenssy/article/details/26961957
 */
public class BadWordUtils {
    public static String filePath = "dictionary.txt";//敏感词库文件路径
    public static Set<String> words;
    public static Map wordMap;
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则

    static {
        BadWordUtils.words = readTxtByLine(filePath);
        addBadWordToHashMap(BadWordUtils.words);
    }

    public static Set<String> readTxtByLine(String path) {
        Set<String> keyWordSet = Sets.newHashSet();
        Resource resource = new ClassPathResource(path);
        File file;
        try {
            file = resource.getFile();
            if (!file.exists()) {      //文件流是否存在
                return keyWordSet;
            }
        } catch (Exception e) {
            return keyWordSet;
        }
        BufferedReader reader = null;
        String temp;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while ((temp = reader.readLine()) != null) {
                keyWordSet.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：
     *
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    public static int checkBadWord(String txt, int beginIndex, int matchType) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word;
        Map nowMap = wordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if (nowMap != null) {     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1 
                if ("1".equals(nowMap.get("isEnd"))) { //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true   
                    if (minMatchTYpe == matchType) {    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        if (!flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     */
    public static boolean isContainBadWord(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = checkBadWord(txt, i, matchType); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文字
     * @param matchType   匹配规则 1：最小匹配规则，2：最大匹配规则
     * @param replaceChar 替换字符，默认*
     */
    public static String replaceBadWord(String txt, int matchType, String replaceChar) {
        Set<String> set = getBadWord(txt, matchType);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word;
        String replaceString;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            txt = txt.replaceAll(word, replaceString);
        }
        return txt;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     */
    public static Set<String> getBadWord(String txt, int matchType) {
        Set<String> sensitiveWordList = Sets.newHashSet();
        for (int i = 0; i < txt.length(); i++) {
            int length = checkBadWord(txt, i, matchType);    //判断是否包含敏感字符
            if (length > 0) {    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }
        return sensitiveWordList;
    }

    /**
     * 获取替换字符串
     */
    private static String getReplaceChars(String replaceChar, int length) {
        StringBuilder resultReplace = new StringBuilder(replaceChar);
        for (int i = 1; i < length; i++) {
            resultReplace.append(replaceChar);
        }

        return resultReplace.toString();
    }

    /**
     * TODO 将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
     *
     * @param keyWordSet 敏感词库
     */
    private static void addBadWordToHashMap(Set<String> keyWordSet) {
        wordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key;
        Map nowMap;
        Map newWorMap;
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();    //关键字
            nowMap = wordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if (wordMap != null) {        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = Maps.newHashMap();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }


    public static void main(String[] args) {
        Set<String> s = BadWordUtils.words;
        Map map = BadWordUtils.wordMap;

        System.out.println("敏感词的数量：" + BadWordUtils.wordMap.size());
        String string = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。" + "然后法轮功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，" + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";
        System.out.println("待检测语句字数：" + string.length());
        long beginTime = System.currentTimeMillis();
        Set<String> set = BadWordUtils.getBadWord(string, maxMatchType);
        Boolean i = BadWordUtils.isContainBadWord(string, maxMatchType);
        Boolean i2 = BadWordUtils.isContainBadWord("粉饰太平", maxMatchType);
        Boolean i22 = BadWordUtils.isContainBadWord("粉饰太平", maxMatchType);
        Boolean i3 = BadWordUtils.isContainBadWord("粉饰", maxMatchType);
        Boolean i33 = BadWordUtils.isContainBadWord("粉饰", maxMatchType);
        Boolean i4 = BadWordUtils.isContainBadWord("太平", maxMatchType);
        Boolean i44 = BadWordUtils.isContainBadWord("太平", maxMatchType);
        Boolean i5 = BadWordUtils.isContainBadWord("个人崇拜", maxMatchType);
        Boolean i55 = BadWordUtils.isContainBadWord("个人崇拜", maxMatchType);
        Boolean i6 = BadWordUtils.isContainBadWord("个人", maxMatchType);
        Boolean i66 = BadWordUtils.isContainBadWord("个人", maxMatchType);
        Boolean i7 = BadWordUtils.isContainBadWord("崇拜", maxMatchType);
        Boolean i77 = BadWordUtils.isContainBadWord("崇拜", maxMatchType);
        String str = "monkey是个大傻X";
        Boolean s1 = BadWordUtils.isContainBadWord(str, maxMatchType);
        System.out.println(s1);
        long endTime = System.currentTimeMillis();
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
        System.out.println("总共消耗时间为：" + (endTime - beginTime) + "毫秒");
    }
}
