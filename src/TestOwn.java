import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestOwn {

	public void MyOwnParser() {

		BufferedReader br = null;
		BufferedReader br1 = null;
		Map<String, Integer> mapQ = new HashMap<String, Integer>();
		Map<String, String> userdata = new HashMap<String, String>();
		Map<String, Integer> mapA = new HashMap<String, Integer>();
		String line;
		try {
			br = new BufferedReader(new FileReader(new File(
					"/Users/uditi_000/Downloads/posts.xml")));
			br1 = new BufferedReader(new FileReader(new File(
					"/Users/uditi_000/Downloads/users.xml")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while ((line = br.readLine()) != null) {

				Pattern postId = Pattern
						.compile("PostTypeId=[\"\']?([\\d]+)[\"\']?");
				Pattern id = Pattern
						.compile("OwnerUserId=[\"\']?([\\d]+)[\"\']?");
				Matcher post = postId.matcher(line);
				Matcher matchId = id.matcher(line);

				if (matchId.find() && post.find()) {

					String postType = post.group(1);
					String userId = matchId.group(1);

					if (postType.equals("1")) {
						if (mapQ.containsKey(userId)) {
							mapQ.put(userId, mapQ.get(userId) + 1);
						} else {
							mapQ.put(userId, 1);
						}

					} else {
						if (postType.equals("2")) {
							if (mapA.containsKey(userId)) {
								mapA.put(userId, mapA.get(userId) + 1);
							} else {
								mapA.put(userId, 1);
							}
						}
					}
				}

			}
		} catch (Exception e) {
		}

		try {
			while ((line = br1.readLine()) != null) {
				Pattern userid = Pattern.compile("Id=[\"\']?([\\d]+)[\"\']?");
				Pattern name = Pattern.compile("(DisplayName=\".*?\")");
				Matcher id = userid.matcher(line);
				Matcher nm = name.matcher(line);

				if (id.find() && nm.find()) {

					String userId = id.group(1);
					String username = nm.group(1);
					userdata.put(userId, username);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		Map<String, String> finalAnswer = new HashMap<String, String>();
		Map<String, String> finalQuestions = new HashMap<String, String>();
		List<Entry<String, Integer>> greatestQ = TopTen.findGreatest(mapQ, 10);
		List<Entry<String, Integer>> greatestA = TopTen.findGreatest(mapA, 10);
		for (Entry<String, Integer> entry : greatestA) {
			for (Map.Entry<String, String> entry1 : userdata.entrySet()) {
				if (entry1.getKey().equals(entry.getKey())) {
					finalAnswer.put(entry.getKey(), entry1.getValue() + "    "
							+ "Count of Answers Posted= "
							+ entry.getValue().toString() + "\n");

				}
			}

		}
		for (Entry<String, Integer> entry : greatestQ) {
			for (Map.Entry<String, String> entry1 : userdata.entrySet()) {
				if (entry1.getKey().equals(entry.getKey())) {
					finalQuestions.put(entry.getKey(), entry1.getValue()
							+ "    " + "Count of Questions Posted= "
							+ entry.getValue().toString() + "\n");

				}
			}

		}

		printValues(finalQuestions);
		System.out
				.println("------------Top Users for questions posted complete---------\n");
		printValues(finalAnswer);
		System.out
				.println("------------Top Users for answers posted complete---------\n");
	}

	private static void printValues(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue();
			System.out.println("User Id = " + key + "::   " + "" + value);

		}

	}
}
