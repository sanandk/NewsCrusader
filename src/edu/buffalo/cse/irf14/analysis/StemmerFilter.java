package edu.buffalo.cse.irf14.analysis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StemmerFilter extends TokenFilter {

	TokenStream t_stream;
	HashSet<String> consonant = new HashSet<String>(Arrays.asList("b", "c",
			"d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s",
			"t", "v", "w", "x", "y", "z"));
	HashSet<String> vowel = new HashSet<String>(Arrays.asList("a", "e", "i",
			"o", "u"));
	String consonantVowelRegex = "([aieou]+[b-df-hj-np-tv-z]+)";
	String consonantRegex = "([b-df-hj-np-tv-z]+)";
	String vowelRegex = "([aieou]+)";
	Pattern consonantVowelPattern = Pattern.compile(consonantVowelRegex);
	Pattern consonantPattern = Pattern.compile(consonantRegex);
	Pattern vowelPattern = Pattern.compile(vowelRegex);
	Matcher matcher;

	public StemmerFilter(TokenStream stream) {
		// TODO Auto-generated constructor stub
		super(stream);
		t_stream = stream;
	}

	@Override
	public boolean increment() throws TokenizerException {
		// Code added by Karthik-j on Sept 18,2014
		// Porter Alogorithm Reference
		// site:http://snowball.tartarus.org/algorithms/porter/stemmer.html
		Token current_token;

		Matcher consonantVowelMatcher;
		if (t_stream.hasNext()) {
			t_stream.next();
			current_token = t_stream.getCurrent();
			if (null == current_token)
				return false;
			String str = current_token.toString();
			String stem;
			int m = 0;
			if (str.matches("[a-zA-Z]+")) {

				// ===========Porter 1a=====================
				if (str.endsWith("s")) {// SSES -> SS, IES -> I, SS -> SS, S ->
					if (str.endsWith("sses"))
						str = str.substring(0, str.lastIndexOf("sses")) + "ss";
					else if (str.endsWith("ies"))
						str = str.substring(0, str.lastIndexOf("ies")) + "i";
					else if (str.endsWith("s") && !str.endsWith("ss"))
						str = str.substring(0, str.lastIndexOf("s"));
				}

				// ===========Porter 1b======================
				// consonantVowelMatcher= consonantVowelPattern.matcher(str);
				// while (consonantVowelMatcher.find()) {
				// // Get the matching string
				// System.out.println(consonantVowelMatcher.group());
				// }
				// String[] strSpit=str.split(cosonantVowelRegex);
				// System.out.println(strSpit.toString());

				else if (str.endsWith("eed")) { // (m>0) EED -> EE but test case
												// is
												// given as "EED"==>"E"
					stem = str.substring(0, str.lastIndexOf("eed"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("eed")) + "e";
					}
				} else if (str.endsWith("ed")) {// (*v*) ED ->
					stem = str.substring(0, str.lastIndexOf("ed"));
					if (containsVowel(stem))
						str = stem;
				} else if (str.endsWith("ing")) {// (*v*) ING ->
					stem = str.substring(0, str.lastIndexOf("ing"));
					if (containsVowel(stem))
						str = stem;
				}

				// ============Porter 1c======================
				else if (str.endsWith("y")) {// (*v*) Y -> I
					stem = str.substring(0, str.lastIndexOf("y"));
					if (containsVowel(stem))
						str = str.substring(0, str.lastIndexOf("y")) + "i";
				}

				// =============Porter 2=========================
				if (str.endsWith("ational")) {// (m>0) ATIONAL -> ATE
												// relational -> relate
					stem = str.substring(0, str.lastIndexOf("ational"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ational"))
								+ "ate";
					}
				} else if (str.endsWith("tional")) {// (m>0) TIONAL -> TION
													// conditional -> condition,
													// rational -> rational
					stem = str.substring(0, str.lastIndexOf("tional"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("tional")) + "t";// rulemodified
																				// to
																				// accomodate
																				// test
																				// cases
					}
				} else if (str.endsWith("enci")) {// (m>0) ENCI -> ENCE valenci
													// ->
													// valence
					stem = str.substring(0, str.lastIndexOf("enci"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("enci"))
								+ "ence";
					}
				} else if (str.endsWith("anci")) {// (m>0) ANCI -> ANCE
													// hesitanci ->
													// hesitance
					stem = str.substring(0, str.lastIndexOf("anci"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("anci"))
								+ "ance";
					}
				} else if (str.endsWith("izer")) {// (m>0) IZER -> IZE digitizer
													// ->
													// digitize
					stem = str.substring(0, str.lastIndexOf("izer"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("izer")) + "ize";
					}
				} else if (str.endsWith("abli")) {// (m>0) ABLI -> ABLE
													// conformabli
													// -> conformable
					stem = str.substring(0, str.lastIndexOf("abli"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("abli"))
								+ "able";
					}
				} else if (str.endsWith("alli")) {// (m>0) ALLI -> AL radicalli
													// ->
													// radical
					stem = str.substring(0, str.lastIndexOf("alli"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("alli")) + "al";
					}
				} else if (str.endsWith("entli")) {// (m>0) ENTLI -> ENT
													// differentli
													// -> different
					stem = str.substring(0, str.lastIndexOf("entli"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("entli"))
								+ "ent";
					}
				} else if (str.endsWith("eli")) {// (m>0) ELI -> E vileli ->
													// vile
					stem = str.substring(0, str.lastIndexOf("eli"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("eli")) + "e";
					}
				} else if (str.endsWith("ousli")) {// (m>0) OUSLI -> OUS
													// analogousli
													// -> analogous
					stem = str.substring(0, str.lastIndexOf("ousli"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ousli"))
								+ "ous";
					}
				} else if (str.endsWith("ization")) {// (m>0) IZATION -> IZE
														// vietnamization ->
														// vietnamize
					stem = str.substring(0, str.lastIndexOf("ization"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ization"))
								+ "ize";
					}
				} else if (str.endsWith("ation")) {// (m>0) ATION -> ATE
													// predication
													// -> predicate
					stem = str.substring(0, str.lastIndexOf("ation"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ation"))
								+ "ate";
					}
				} else if (str.endsWith("ator")) {// (m>0) ATOR -> ATE operator
													// ->
													// operate
					stem = str.substring(0, str.lastIndexOf("ator"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ator"));// alerted
																		// toaccomodate
																		// test
																		// cases
					}
				} else if (str.endsWith("alism")) {// (m>0) ALISM -> AL
													// feudalism ->
													// feudal
					stem = str.substring(0, str.lastIndexOf("alism"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("alism")) + "al";
					}
				} else if (str.endsWith("iveness")) {// (m>0) IVENESS -> IVE
														// decisiveness ->
														// decisive
					stem = str.substring(0, str.lastIndexOf("iveness"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("iveness"))
								+ "ive";
					}
				} else if (str.endsWith("fulness")) {// (m>0) FULNESS -> FUL
														// hopefulness ->
														// hopeful
					stem = str.substring(0, str.lastIndexOf("fulness"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("fulness"))
								+ "ful";
					}
				} else if (str.endsWith("ousness")) {// (m>0) OUSNESS -> OUS
														// callousness ->
														// callous
					stem = str.substring(0, str.lastIndexOf("ousness"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ousness"))
								+ "ous";
					}
				} else if (str.endsWith("aliti")) {// (m>0) ALITI -> AL
													// formaliti ->
													// formal
					stem = str.substring(0, str.lastIndexOf("aliti"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("aliti")) + "al";
					}
				} else if (str.endsWith("iviti")) {// (m>0) IVITI -> IVE
													// sensitiviti
													// -> sensitive
					stem = str.substring(0, str.lastIndexOf("iviti"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("iviti"))
								+ "ive";
					}
				} else if (str.endsWith("biliti")) {// (m>0) BILITI -> BLE
													// sensibiliti -> sensible
					stem = str.substring(0, str.lastIndexOf("biliti"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("biliti"))
								+ "ble";
					}
				}

				// ==================Porter 3========================
				else if (str.endsWith("icate")) {// (m>0) ICATE -> IC triplicate
													// ->
													// triplic
					stem = str.substring(0, str.lastIndexOf("icate"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("icate")) + "ic";
					}
				} else if (str.endsWith("ative")) {// (m>0) ATIVE -> formative
													// ->
													// form
					stem = str.substring(0, str.lastIndexOf("ative"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ative"));
					}
				} else if (str.endsWith("alize")) {// (m>0) ALIZE -> AL
													// formalize ->
													// formal
					stem = str.substring(0, str.lastIndexOf("alize"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("alize")) + "al";
					}
				} else if (str.endsWith("iciti")) {// (m>0) ICITI -> IC
													// electriciti
													// -> electric
					stem = str.substring(0, str.lastIndexOf("iciti"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("iciti")) + "ic";
					}
				} else if (str.endsWith("ical")) {// (m>0) ICAL -> IC electrical
													// ->
													// electric
					stem = str.substring(0, str.lastIndexOf("ical"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ical")) + "ic";
					}
				} else if (str.endsWith("ful")) {// (m>0) FUL -> hopeful -> hope
					stem = str.substring(0, str.lastIndexOf("ful"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ful"));
					}
				} else if (str.endsWith("ness")) {// (m>0) NESS -> goodness ->
													// good
					stem = str.substring(0, str.lastIndexOf("ness"));
					m = getVowelConsonantCount(stem);
					if (m > 0) {
						str = str.substring(0, str.lastIndexOf("ness"));
					}
				}

				// ==============Porter 4==============================
				else if (str.endsWith("al")) {// (m>1) AL -> revival -> reviv

					stem = str.substring(0, str.lastIndexOf("al"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("al"));
					}
				} else if (str.endsWith("ance")) {// (m>1) ANCE -> allowance ->
													// allow

					stem = str.substring(0, str.lastIndexOf("ance"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ance"));
					}
				} else if (str.endsWith("ence")) {// (m>1) ENCE -> inference ->
													// infer

					stem = str.substring(0, str.lastIndexOf("ence"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ence"));
					}
				} else if (str.endsWith("er")) {// (m>1) ER -> airliner ->
												// airlin

					stem = str.substring(0, str.lastIndexOf("er"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("er"));
					}
				} else if (str.endsWith("ic")) {// (m>1) IC -> gyroscopic ->
												// gyroscop

					stem = str.substring(0, str.lastIndexOf("ic"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ic"));
					}
				} else if (str.endsWith("able")) {// (m>1) ABLE -> adjustable ->
													// adjust

					stem = str.substring(0, str.lastIndexOf("able"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("able"));
					}
				} else if (str.endsWith("ible")) {// (m>1) IBLE -> defensible ->
													// defens

					stem = str.substring(0, str.lastIndexOf("ible"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ible"));
					}
				} else if (str.endsWith("ant")) {// (m>1) ANT -> irritant ->
													// irrit

					stem = str.substring(0, str.lastIndexOf("ant"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ant"));
					}
				} else if (str.endsWith("ement")) {// (m>1) EMENT -> replacement
													// ->
													// replac

					stem = str.substring(0, str.lastIndexOf("ement"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ement"));
					}
				} else if (str.endsWith("ment")) {// (m>1) MENT -> adjustment ->
													// adjust

					stem = str.substring(0, str.lastIndexOf("ment"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ment"));
					}
				} else if (str.endsWith("ent")) {// (m>1) ENT -> dependent ->
													// depend

					stem = str.substring(0, str.lastIndexOf("ent"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ent"));
					}
				} else if (str.endsWith("")) {// (m>1 and (*S or *T)) ION ->
												// adoption -> adopt

					stem = str.substring(0, str.lastIndexOf(""));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf(""));
					}
				} else if (str.endsWith("ou")) {// (m>1) OU -> homologou ->
												// homolog

					stem = str.substring(0, str.lastIndexOf("ou"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ou"));
					}
				} else if (str.endsWith("ism")) {// (m>1) ISM -> communism ->
													// commun

					stem = str.substring(0, str.lastIndexOf("ism"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ism"));
					}
				} else if (str.endsWith("ate")) {// (m>1) ATE -> activate ->
													// activ

					stem = str.substring(0, str.lastIndexOf("ate"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ate"));
					}
				} else if (str.endsWith("iti")) {// (m>1) ITI -> angulariti ->
													// angular

					stem = str.substring(0, str.lastIndexOf("iti"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("iti"));
					}
				} else if (str.endsWith("ous")) {// (m>1) OUS -> homologous ->
													// homolog

					stem = str.substring(0, str.lastIndexOf("ous"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ous"));
					}
				} else if (str.endsWith("ive")) {// (m>1) IVE -> effective ->
													// effect

					stem = str.substring(0, str.lastIndexOf("ive"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ive"));
					}
				} else if (str.endsWith("ize")) {// (m>1) IZE -> bowdlerize ->
													// bowdler

					stem = str.substring(0, str.lastIndexOf("ize"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("ize"));
					}
				}

				// ===================Porter
				// 5a=========================================

				else if (str.endsWith("e")) {// (m>1) E -> probate -> probat,
												// rate
												// -> rate
					// (m=1 and not *o) E -> cease ->ceas
					stem = str.substring(0, str.lastIndexOf("e"));
					m = getVowelConsonantCount(stem);
					if (m > 1) {
						str = str.substring(0, str.lastIndexOf("e"));
					} else if (m == 1 & !stem.endsWith("o")) {
						str = str.substring(0, str.lastIndexOf("e"));
					}
				}
			}
			current_token.setTermText(str);
			t_stream.replace(current_token);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public TokenStream getStream() {
		// TODO Auto-generated method stub
		return t_stream;
	}

	public int getVowelConsonantCount(String str) {
		int splitIndex = 0, m = 0;

		for (String tempChar : str.split("(?!^)")) {
			if (vowel.contains(tempChar))
				break;
			else
				splitIndex++;
		}
		str = str.substring(splitIndex);
		matcher = consonantVowelPattern.matcher(str);
		while (matcher.find()) {
			m++;
		}
		return m;
	}

	public boolean containsVowel(String str) {
		matcher = vowelPattern.matcher(str);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

}
