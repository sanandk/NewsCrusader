/**
 * 
 */
package edu.buffalo.cse.irf14.analysis.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.buffalo.cse.irf14.analysis.TokenFilterType;
import edu.buffalo.cse.irf14.analysis.TokenizerException;

/**
 * @author nikhillo
 * 
 */
public class DateRuleTest extends TFRuleBaseTest {

	@Test
	public void testRule() {
		
		try{
				assertArrayEquals(
						new String[] { "Vidya", "Balan", "born",
								"19780101", "is", "an", "Indian",
								"actress." },
						runTest(TokenFilterType.DATE, "Vidya Balan born 1 January "
								+ "1978 is an Indian actress."));
				assertArrayEquals(
						new String[] { "President", "Franklin", "D.",
								"Roosevelt", "to", "proclaim", "19411207,",
								"'a", "date", "which", "will", "live",
								"in", "infamy'" },
						runTest(TokenFilterType.DATE, "Corn sales gained 2,494,900 tonnes in the week ended February 26, the highest weekly total since August 1984 and two and three-quarter times the prior week's level, the U.S. Agriculture Department said. In comments on its Export Sales Report, the department said sales of 1.0 mln tonnes to the USSR -- previously reported under the daily reporting system -- were the first sales for delivery to the USSR under the fourth year of the U.S.-USSR Grains Supply Agreement, which began October 1. Japan added 689,700 tonnes to previous purchases and sales to unknown destinations rose by 429,800 tonnes. Wheat sales of 362,400 tonnes for the current season and 151,000 for the 1987/88 season were down by more than half from the previous week's combined sales, it said. Egypt, Japan and Iraq were the major wheat buyers for delivery in the current year, while sales to China decreased by 30,000 tonnes for the current season, but increased by 90,000 tonnes for the 1987/88 season, which begins June 1. Net sales of soybeans totalling 274,200 tonnes equaled the preceding week, but were nearly a third below the four week average. Major increases were for Belgium, South Korea, Mexico and Italy, it said. Soybean cake and meal sales of 103,700 tonnes were 2-3/4 times the previous week's marketing year low, but six pct less than the four week average. Major increases for West Germany, Belgium, Spain, Italy and Australia were partially offset by declines to unknown destinations. Soybean oil sales of 5,400 tonnes were the result of increases for Venezuela and reductions of 500 tonnes for unknown destinations. Combined sales activity in cotton of 75,200 running bales -- 44,700 bales for the current year and 30,500 bales for the 1987/88  bales -- were 56 pct below the prior week's good showing, the department said. Major purchasers for the current season were South Korea, Japan, Taiwan and Thailand, while South Korea and Indonesia were the major buyers for the 1987/88 season, which begins August 1."));
				assertArrayEquals(
						new String[] { "The", "Academy", "operated",
								"until", "it", "was", "destroyed", "by",
								"Lucius", "Cornelius", "Sulla", "in",
								"-00840101" },
						runTest(TokenFilterType.DATE, "The Academy operated until "
								+ "it was destroyed by Lucius "
								+ "Cornelius Sulla in 84 BC"));
				assertArrayEquals(
						new String[] { "For", "instance,", "the",
								"19480101", "ABL", "finalist", "Baltimore",
								"Bullets", "moved", "to", "the", "BAA",
								"and", "won", "that", "league's",
								"19480101", "title." },
						runTest(TokenFilterType.DATE, "For instance, the 1948 ABL "
								+ "finalist Baltimore Bullets "
								+ "moved to the BAA and won "
								+ "that league's 1948 title."));
				assertArrayEquals(
						new String[] { "It", "was", "now", "about",
								"10:15:00." },
						runTest(TokenFilterType.DATE, "It was now about 10:15 am."));
				assertArrayEquals(
						new String[] { "Godse", "approached", "Gandhi",
								"on", "19480130", "during", "the",
								"evening", "prayer", "at", "17:15:00." },
						runTest(TokenFilterType.DATE, "Godse approached Gandhi on "
								+ "January 30, 1948 during the "
								+ "evening prayer at 5:15PM."));
				assertArrayEquals(
						new String[] { "Pune", "is", "known", "to", "have",
								"existed", "as", "a", "town", "since",
								"08470101." },
						runTest(TokenFilterType.DATE, "Pune is known to have existed as a town since 847AD."));
				assertArrayEquals(
						new String[] { "19000411", "is", "the", "101st",
								"day", "of", "the", "year", "(102nd", "in",
								"leap", "years)", "in", "the", "Gregorian",
								"calendar." },
						runTest(TokenFilterType.DATE, "April 11 is the 101st day "
								+ "of the year (102nd in "
								+ "leap years) in the Gregorian "
								+ "calendar."));
				assertArrayEquals(
						new String[] { "Apple", "is", "one", "of", "the",
								"world's", "most", "valuable", "publicly",
								"traded", "companies", "in",
								"20110101-20120101." },
						runTest(TokenFilterType.DATE, "Apple is one of the "
								+ "world's most valuable publicly "
								+ "traded companies in 2011-12."));

			} catch (TokenizerException e) {
				fail("Exception thrown when not expected!");
			}
	}
}
