/**
 * file name : FileNameMatcher.java
 * created at : 5:02:38 PM Nov 29, 2015
 * created by 970655147
 */

package org.yoqu.cms.core.util;

// FileNameMatcher
public class FileNameMatcher {

	// 所支持的通配符, '?' 可以表示一个任意字符, '*' 表示任意个任意字符[具体的匹配由isGreedy进行约束]
	// 因为普通场景下面, 没有'?', '*' 的文件名, 因此这里便没有写'?', '*'本身的表示[如果 要写的话, 模拟转义吧][在搜索wildCard的时候, 不能搜索转义的'?', '*', 在匹配的时候, 将'\?', '\*'替换为真实的字符串表示的'?', '*' ]
	// 一个pattern中各个子pattern的分隔符, 各个子pattern之间的关系为 "短路或"
	// 这里 并没有约定"短路与"的场景[优先级问题 会导出很多问题], 请自行使用match进行实现吧
	public static final Character MATCH_ONE = '?';
	public static final Character MATCH_MULTI = '*';
	public static final char[] WILDCARDS = new char[]{MATCH_ONE, MATCH_MULTI };
	public static final int MATCH_ONE_IDX = 0;
	public static final int MATCH_MULTI_IDX = 1;

	public static final String PATTERN_SEP = "\\|";

	/**
	 * @Name: match
	 * @Description: 判断给定的fileName是否匹配给定的pattern
	 * @param fileName
	 * @param pattern
	 * @param isGreedy 表示是否采用贪婪匹配的模式[也就是通配符'*'是否贪婪]
	 * @return
	 * @Create at 2016年8月6日 下午3:15:28 by '970655147'
	 */
	public static boolean match(String fileName, String pattern, boolean isGreedy) {
		String[] subPatterns = pattern.split(PATTERN_SEP);
		for(int i=0; i<subPatterns.length; i++) {
			if(match0(fileName, subPatterns[i], isGreedy) ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @Name: match
	 * @Description: isGreedy 默认为false
	 * @param fileName
	 * @param pattern
	 * @return
	 * @Create at 2016年8月6日 下午3:16:51 by '970655147'
	 */
	public static boolean match(String fileName, String pattern) {
		return match(fileName, pattern, false);
	}

	/**
	 * @Name: equalsIgnoreCase
	 * @Description: 判定str01, str02是否相等[忽略大小写]
	 * @param str01
	 * @param str02
	 * @return
	 * @Create at 2016年8月6日 下午3:20:51 by '970655147'
	 */
	public static boolean equalsIgnoreCase(String str01, String str02) {
		return str01.equalsIgnoreCase(str02);
	}

	/**
	 * @Name: isEmpty
	 * @Description: 判定给定的字符串是否为空字符串
	 * @param str
	 * @return
	 * @Create at 2016年8月6日 下午3:21:01 by '970655147'
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || "".equals(str.trim());
	}

	/**
	 * @Name: match0
	 * @Description: match的核心业务方法
	 * @param fileName
	 * @param pattern
	 * @param isGreedy
	 * @return
	 * @Create at 2016年8月6日 下午3:28:45 by '970655147'
	 */
	private static boolean match0(String fileName, String pattern, boolean isGreedy) {
		pattern = preparePattern(pattern);

		int[] nextWildCards = newWildCards();
		initWildCards(pattern, nextWildCards);
		WildCardAndIdx wildCardAndIdx = new WildCardAndIdx();

		int fileNameIdx = 0, patternIdx = 0;
		while(hasNextWildCards(nextWildCards) ) {
			// 如果没有匹配的pattern的字符串, 直接返回false
			if((fileNameIdx < 0) || (fileNameIdx >= fileName.length()) ) {
				return false;
			}

			nextWildCard(pattern, nextWildCards, wildCardAndIdx);
			int len = wildCardAndIdx.pos - patternIdx;
			if(len != 0) {
				// 如果fileName不够长, 或者到下一个通配符之间的字符串和pattern不匹配, 直接返回false
				if(fileNameIdx+len >= fileName.length()) {
					return false;
				}
				if(! equalsIgnoreCase(fileName.substring(fileNameIdx, fileNameIdx+len), pattern.substring(patternIdx, patternIdx+len)) ) {
					return false;
				}
			}

			// 处理各个通配符的场景
			switch(wildCardAndIdx.wildCardIdx) {
				// 对于'?', 索引增加 : 精确匹配的字符串的长度+1
				case MATCH_ONE_IDX :
				{
					fileNameIdx += (len + 1);
					patternIdx += (len + 1);
					break ;
				}
				// 对于'*', 分为贪婪 和非贪婪进行处理
				case MATCH_MULTI_IDX :
				{
					int curPos = wildCardAndIdx.pos;
					peekNextWildCard(fileName, nextWildCards, wildCardAndIdx);
					String strBetweenNextWildCard = null;
					// 如果下一个字符也为通配符, 则strBetweenNextWildCard为"", isEmpty(strBetweenNextWildCard), 直接返回了true, 以及后面的fileNameIdx的更新造成影响  构成错误
					// 所以 需要预处理pattern, 防止类似的情况发生 "**", "*?"
					if(wildCardAndIdx.pos != -1) {
						strBetweenNextWildCard = pattern.substring(curPos+1, wildCardAndIdx.pos);
					} else {
						strBetweenNextWildCard = pattern.substring(curPos+1);
					}
					// 处理pattern的最后一个字符为*的场景
					if(isEmpty(strBetweenNextWildCard) ) {
						return true;
					}

					if(isGreedy) {
						fileNameIdx = fileName.lastIndexOf(strBetweenNextWildCard);
					} else {
						fileNameIdx = fileName.indexOf(strBetweenNextWildCard, fileNameIdx+1);
					}
					patternIdx += (len + 1);
					break ;
				}
				// Other ??  can't be there in normal case
				default :
					throw new RuntimeException("unsupported wildcard !");
			}
		}

		return equalsIgnoreCase(fileName.substring(fileNameIdx), pattern.substring(patternIdx) );
	}

	// 预处理pattern
	// 1. 防止类似的情况发生 "**", "*?"
	private static String preparePattern(String pattern) {
		StringBuilder sb = new StringBuilder();
		for(int i=0, len=pattern.length(); i<len; i++) {
			char ch = pattern.charAt(i);
			sb.append(ch);
			// '*XX'
			if(ch == MATCH_MULTI) {
				int nextI = i+1;
				while((nextI < len) && (contains(WILDCARDS, pattern.charAt(nextI))) ) {
					nextI ++;
				}
				i = nextI - 1;
			}
		}

		return sb.toString();
	}
	// 创建一个通配符的索引的数组
	private static int[] newWildCards() {
		return new int[WILDCARDS.length];
	}
	// 初始化通配符的位置
	private static void initWildCards(String pattern, int[] nextWildCards) {
		for(int i=0; i<nextWildCards.length; i++) {
			nextWildCards[i] = pattern.indexOf(WILDCARDS[i] );
		}
	}
	// 判断是否还有下一个通配符
	private static boolean hasNextWildCards(int[] nextWildCards) {
		for(int i=0; i<nextWildCards.length; i++) {
			if(nextWildCards[i] >= 0) {
				return true;
			}
		}

		return false;
	}
	// 获取pattern中下一个通配符的位置, 并更新该通配符的下一个位置
	private static void nextWildCard(String pattern, int[] nextWildCards, WildCardAndIdx wildCardAndIdx) {
		peekNextWildCard(pattern, nextWildCards, wildCardAndIdx);

		nextWildCards[wildCardAndIdx.wildCardIdx] = pattern.indexOf(WILDCARDS[wildCardAndIdx.wildCardIdx], wildCardAndIdx.pos+1);
	}
	// 获取pattern中下一个通配符的数据, 放到wildCardAndIdx中
	private static void peekNextWildCard(String fileName, int[] nextWildCards, WildCardAndIdx wildCardAndIdx) {
		int next = getMinIdx(nextWildCards);
		wildCardAndIdx.wildCardIdx = next;

		if(next != -1) {
			wildCardAndIdx.pos = nextWildCards[next];
		} else {
			wildCardAndIdx.pos = -1;
		}
	}
	// 获取pattern中下一个的通配符的索引
	private static int getMinIdx(int[] nextWildCards) {
		int min = Integer.MAX_VALUE, idx = -1;
		for(int i=0; i<nextWildCards.length; i++) {
			// '>= 0' for check valid
			if((nextWildCards[i] >= 0) && (nextWildCards[i] < min) ) {
				idx = i;
				min = nextWildCards[i];
			}
		}

		return idx;
	}
	// 判断给定的字符数组中是否包含给定的字符
	private static boolean contains(char[] chars, char ch) {
		for(int i=0; i<chars.length; i++) {
			if(chars[i] == ch) {
				return true;
			}
		}

		return false;
	}

	// 通配符的索引, 以及其当前位置
	static class WildCardAndIdx {
		public int wildCardIdx;
		public int pos;

		public String toString() {
			return WILDCARDS[wildCardIdx] + " -> " + pos;
		}
	}

}
