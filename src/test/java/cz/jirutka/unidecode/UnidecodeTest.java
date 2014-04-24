/*
 * Copyright 2013 徐晨阳 <xuender@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.jirutka.unidecode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UnidecodeTest {

    Unidecode unidecode = Unidecode.withCharset("ASCII");

    @Test
    public void testDecode() {
        assertEquals("hello world", "Hello world.",
                unidecode.decode("Hello world."));
        assertEquals("南无阿弥陀佛", "Nan Wu A Mi Tuo Fo", unidecode.decode("南无阿弥陀佛"));
        assertEquals("Κνωσός", "Knosos", unidecode.decode("Κνωσός"));
        assertEquals("あみだにょらい", "amidaniyorai", unidecode.decode("あみだにょらい"));
    }

    @Test
    public void testDecodeException() {
        assertEquals("", "", unidecode.decode(""));
        assertEquals("NULL", "", unidecode.decode(null));
    }

    @Test
    public void testInitials() {
        assertEquals("hello world", "Hw", unidecode.toInitials("Hello world."));
        assertEquals("南无阿弥陀佛", "NWAMTF", unidecode.toInitials("南无阿弥陀佛"));
        assertEquals("Κνωσός", "K", unidecode.toInitials("Κνωσός"));
        assertEquals("あみだにょらい", "a", unidecode.toInitials("あみだにょらい"));
        assertEquals("enter", "XXGN\nQZQC\n\rTZHL\tSSC",
                unidecode.toInitials("小小姑娘\n清早起床\n\r提着花篮\t上市场。"));
    }

    @Test
    public void testInitialsException() {
        assertEquals("", "", unidecode.toInitials(""));
        assertEquals("NULL", "", unidecode.toInitials(null));
    }

    /**
     * 2013-09-06 17:57
     *
     * 你好，最近在项目中使用Unidecode，把用户名转拼音后的声母取出来，发现在转换“一”这个字的时候结果有问题 String pinyin =
     * Unidecode.decode("一条会走路的鱼"); System.out.print(pinyin.charAt(0)); 输出结果为：[
     * 怎么办？
     */
    @Test
    public void testDecodeYi() {
        assertEquals("何清宝提出的bug", "Yi Tiao Hui Zou Lu De Yu",
                unidecode.decode("一条会走路的鱼"));
    }

    /**
     * 检查全部汉字是否还有[开头的情况
     */
    @Test
    public void testAll() {
        int a = (int) (4 * Math.pow(16, 3) + 14 * Math.pow(16, 2)); // 汉字ASCII码最小值
        int b = (int) (9 * Math.pow(16, 3) + 15 * Math.pow(16, 2) + 10 * Math
                .pow(16, 1)) + 5; // 汉字ASCII码最大值
        for (int i = a; i <= b; i++) {
            assertNotEquals("不是[开头", "[", unidecode.decode((char) i + "")
                    .charAt(0));
        }
    }

    /**
     * ArrayIndexOutOfBound exception
     *
     * Schweigi opened this issue
     *
     * If decode() is used with e.g. an emoticon character there is a
     * ArrayIndexOutOfBound exception thrown.
     *
     * Example text: http://www.scarfboy.com/coding/unicode-tool?s=U%2b1F61C
     *
     * String[] ret = cache[section]; (Line: 63)
     *
     * The reason is that on Line 52 int section = codepoint >> 8; the section
     * will be bigger than 255 and therefore out of the cache area.
     *
     * In my opinion there are two solution:
     *
     * Either the cache is made bigger to contain all value up to 0xEFFFF or
     * Characters with a value bigger (> 0xFFFF) than the cache should not be
     * cached.
     */
    @Test
    public void testIssues2() {
        assertEquals("", unidecode.decode("😜"));
        assertEquals("H", unidecode.decode("Ｈ"));
    }
}
