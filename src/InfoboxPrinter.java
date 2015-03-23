import java.util.HashSet;
import java.util.List;


public class InfoboxPrinter {
	private static int WIDTH = 100;
	
	public void print() {
		for (int i = 0; i < WIDTH; i++) {
			if (i == 0 || i == WIDTH-1)
				System.out.print(" ");
			else
				System.out.print("-");
		}
		System.out.println();
	}
	
	public void print82() {
		System.out.printf("|%17s", "");
		for (int i = 0; i < 82; i++) {
				System.out.print("-");
		}
		System.out.println();
	}
	
	public void print(String name, HashSet<String> validTypes) {
		StringBuilder sb = new StringBuilder();
		sb.append(name + "(");
		int i = 0;
		for (String type : validTypes) {
			if (i == 0)
				sb.append(type);
			else
				sb.append(", " + type);
			i++;
		}
		sb.append(")");
		String title = sb.toString();
		int remain = WIDTH-2-title.length();
		System.out.print("|");
		for (int j = 0;  j < remain/2; j++) {
			System.out.print(" ");
		}
		System.out.print(title);
		for (int j = 0;  j < remain-remain/2; j++) {
			System.out.print(" ");
		}
		System.out.println("|");
	}
	
	public void print(String name, String content) {
		if (!name.equals(""))
			name = name + ":";
		content = content.replaceAll("\n", " ");
		int line = content.length() / 81;
		if (content.length() % 81 > 0)
			line++;
		for(int i = 0; i < line; i++) {
			if(i == 0){
				System.out.printf("| %-16s%-81s|\n", name, content.substring(i*81, Math.min((i+1)*81, content.length())));
			}
			else{
				System.out.printf("| %-16s%-81s|\n", "", content.substring(i*81, Math.min((i+1)*81, content.length())));
			}
		}
	}
	
	public void print(String name, List<String> values) {
		if (!name.equals(""))
			name = name + ":";
		int sectionLen = 81 / values.size();
		System.out.printf("| %-16s", name);
		for (int i = 0; i < values.size(); i++) {
			int len = sectionLen-1;
			if (i == values.size() - 1)
				len = 81 - i * sectionLen - 1;
			String tmp = values.get(i);
			if (len < values.get(i).length()) {
				tmp = tmp.substring(0, len-3) + "...";
			}
			System.out.printf("|%-" + len + "s", tmp);
		}
		System.out.println("|");
	}
}
